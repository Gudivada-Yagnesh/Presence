package com.yagnesh.presence.data.model

data class AttendanceResult(

    val totalStudents: Int,

    val presentStudents: Int,

    val absentStudents: Int,

    val presentStudentsList: List<String>

)