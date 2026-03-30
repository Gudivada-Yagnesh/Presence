package com.yagnesh.presence.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val rollNumber: String,

    val className: String,

    val imagePath: String,

    val isPresent: Boolean = false,

    val totalClasses: Int = 0,

    val presentClasses: Int = 0

)