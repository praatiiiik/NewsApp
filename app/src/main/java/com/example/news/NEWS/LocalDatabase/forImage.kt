package com.example.news.NEWS.LocalDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imageTable")
data class forImage(@ColumnInfo var imageName : String?, @ColumnInfo var index :Int?=0) {
    @PrimaryKey(autoGenerate = false)
    var id = index
}