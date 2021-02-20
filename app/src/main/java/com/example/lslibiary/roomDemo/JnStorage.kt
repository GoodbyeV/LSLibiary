package com.example.lslibiary.roomDemo

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception

/**
 * author  : Liushuai
 * time    : 2021/2/19 20:45
 * desc    :
 */
object JnStorage {
    fun <T> saveCache(key:String,body:T){
        val cache=Cache()
        cache.key=key
        cache.data= toByteArray(body)
        CacheDatabase.get().cacheDao.saveCache(cache)
    }


    fun <T> getCache(key: String):T?{
        val cache=CacheDatabase.get().cacheDao.getCache(key)
        var t:T?=null
        if (cache?.data != null) {
            t=toObject(cache.data) as? T
        }
        return t
    }

    fun deleteCache(key:String){
        val cache=Cache()
        cache.key=key
        CacheDatabase.get().cacheDao.deleteCache(cache)
    }


    //将对象转化为二进制流  比json效率要高
    private fun <T> toByteArray(body:T):ByteArray?{
        var baos: ByteArrayOutputStream?=null
        var oos:ObjectOutputStream?=null
        try {
            baos= ByteArrayOutputStream()
            oos= ObjectOutputStream(baos)
            oos.writeObject(body)
            oos.flush()
            return baos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            baos?.close()
            oos?.close()
        }
        return ByteArray(0)
    }

    //将二进制转化为对象
    private fun  toObject(data:ByteArray?):Any?{
        var baos: ByteArrayInputStream?=null
        var ois:ObjectInputStream?=null
        try {
            baos= ByteArrayInputStream(data)
            ois= ObjectInputStream(baos)
            return ois.readObject()
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            baos?.close()
            ois?.close()
        }
        return ByteArray(0)
    }

}