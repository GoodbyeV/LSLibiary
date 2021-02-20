package com.example.lslibiary.roomDemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.lslibiary.MyApplication

/**
 * author  : Liushuai
 * time    : 2021/2/19 17:16
 * desc    :
 */

@Database(entities = [Cache::class,User::class],version = 1,exportSchema = true)
abstract class CacheDatabase:RoomDatabase() {
    abstract val cacheDao:CacheDao

    companion object{
        private var database:CacheDatabase?=null
        val callBack= object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }
        }

        val migration1_2= object : Migration(1,2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table table_cache add column cache_time LONG")
            }
        }
        fun get():CacheDatabase {
            if (database == null) {
                //创建内存数据库 存储的数据只会停留在内存中 进程结束后数据随之丢失
//                database=Room.inMemoryDatabaseBuilder(context,CacheDatabase::class.java).build();
                database=Room.databaseBuilder(MyApplication.instance,CacheDatabase::class.java,"roomDemo")
                        //允许在主线程操作数据库
//                        .allowMainThreadQueries()
                        .addCallback(callBack)
                        //指定数据库查询的线程池 不指定有默认的
                        .setQueryExecutor{
                        }
                        //有默认 可以利用它实现数据库加密 自行创建supportsquliteopenhelper
//                        .openHelperFactory()
                        //做数据库升级 表的字段发生改变都有执行
//                        .addMigrations(migration1_2)
                        .build()

            }
            return database!!
        }
    }
}