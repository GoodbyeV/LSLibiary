package com.example.jnexecutorlibrary

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**
 * author  : Liushuai
 * time    : 2021/1/31 15:42
 * desc    :线程池组件:支持任务优先级、线程池暂停、恢复、关闭。支持一步任务结果回调
 */
object JnExecutor {
    private val TAG: String?="JnExecutor"
    private var jnExecutor: ThreadPoolExecutor
    private var lock:ReentrantLock = ReentrantLock()
    private var pauseCondition:Condition
    private var isPaused:Boolean=false
    private val mainHandler=Handler(Looper.getMainLooper())

    init {
        //参数初始化
        pauseCondition= lock.newCondition()
        val cpuCount=Runtime.getRuntime().availableProcessors()
        val corePoolSize=cpuCount+1
        val maxPoolSize=cpuCount*2+1
         //按优先级高地排列 执行时先执行高优先级 传入的元素要实现Comparable
        val blockingDeque:PriorityBlockingQueue<out Runnable> =PriorityBlockingQueue()
        val keepAlive=30L
        val unit=TimeUnit.SECONDS
        val flag=AtomicLong()
        val threadFactory=ThreadFactory{
            val thread=Thread(it)
            thread.name="JNExecutor"+flag.getAndIncrement()
            return@ThreadFactory thread
        }
        jnExecutor=object :ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAlive,unit,blockingDeque as BlockingQueue<Runnable>,threadFactory){
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (isPaused) {
                    lock.lock()
                    try {
                        //任务run方法执行前通过条件对象阻塞当前线程
                        pauseCondition.await()
                    }finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                //监控线程池耗时任务 线程创建数量 正在运行数量
                Log.i(TAG,"任务完成优先级为："+(r as PriorityRunnable).priority)

            }
        }
    }

    @JvmOverloads
    fun execute(@IntRange(from =0,to=10)priority:Int=0,runnable: Runnable) {
        jnExecutor.execute(PriorityRunnable(priority,runnable))
    }


    @Synchronized
    fun pause(){
        isPaused=true
        Log.i(TAG,"executor is paused")
    }

    @Synchronized
    fun resume(){
        isPaused=false
        lock.lock()
        try {
            //唤醒所有阻塞的线程
            pauseCondition.signalAll()
        }finally {
            lock.unlock()
        }
        Log.i(TAG,"executor is resume")
    }

    //需要主线程结果回调、执行前的回调
    abstract class Callable<T> :Runnable{
        override fun run() {
            mainHandler.post {
                onPrepare()
            }
            //先执行任务
            val t=onBackground()
            //执行完成主线程回调
            mainHandler.post {
                onCompleted(t)
            }
        }
        open fun onPrepare(){
            //执行前提示性工作
        }
        //线程执行的内容工作
        abstract fun onBackground():T
        //完成后的回调
        abstract fun onCompleted(t:T)
    }

    class PriorityRunnable(val priority: Int,val runnable: Runnable):Runnable,Comparable<PriorityRunnable>{
        override fun run() {
            runnable.run()
        }

        override fun compareTo(other: PriorityRunnable): Int {
            return if(this.priority<other.priority) 1 else if(this.priority>other.priority) -1 else 0
        }
    }
}
