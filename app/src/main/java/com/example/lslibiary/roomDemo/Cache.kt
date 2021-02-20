package com.example.lslibiary.roomDemo

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.room.*

/**
 * author  : Liushuai
 * time    : 2021/2/19 16:57
 * desc    :
 */
@Entity(tableName = "table_cache")
class Cache {
    //主键 不自动指定值
    @PrimaryKey(autoGenerate = false)
    @NonNull
    var key:String=""

    var data:ByteArray?=null

    @ColumnInfo(name = "cacheId",defaultValue = "1")
    var cache_id:Long=0

    //忽略字段 不会映射到数据库表
    @Ignore
    var bitmap:Bitmap?=null

    @Embedded//把当前对象也映射成数据库表  要求对象也要符合表声明
    var user:User?=null

}

@Entity(tableName = "table_user")
class User {
    @PrimaryKey
    @NonNull
   var name:String=""
   var age=10
}
