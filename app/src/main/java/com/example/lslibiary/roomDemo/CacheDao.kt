package com.example.lslibiary.roomDemo

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * author  : Liushuai
 * time    : 2021/2/19 17:04
 * desc    : 类似于retrofit的接口定义方法
 */
@Dao  //Dao 数据访问对象  定义数据操作 增删改查
interface CacheDao{
    @Query("select * from table_cache where `key`=:keyword limit 1")
    fun getCache(keyword:String):Cache


    //通过结合livedata 以观察者的方式获取数据库数据 可以避免Npe
    //可以监听数据库表中的数据变化 一旦发生了 操作room会自动监听并发送给UI层更新
    @Query("select * from table_cache ")
    fun queryAll():LiveData<List<Cache>>


    @Delete(entity = Cache::class)
    fun deleteCache(cache:Cache)

    //onConflict插入冲突策略 覆盖
    @Insert(entity = Cache::class,onConflict = OnConflictStrategy.REPLACE)
    fun saveCache(cache:Cache)

    @Update()
    fun  update(cache: Cache)
}