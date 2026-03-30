package com.yagnesh.presence.data.repository

import com.yagnesh.presence.data.database.StudentDao
import com.yagnesh.presence.data.model.Student
import kotlinx.coroutines.flow.Flow

class StudentRepository(private val studentDao: StudentDao) {

    val allStudents: Flow<List<Student>> = studentDao.getAllStudents()

    suspend fun insert(student: Student) {
        studentDao.insert(student)
    }

    suspend fun delete(student: Student) {
        studentDao.delete(student)
    }

    suspend fun update(student: Student) {
        studentDao.update(student)
    }

}