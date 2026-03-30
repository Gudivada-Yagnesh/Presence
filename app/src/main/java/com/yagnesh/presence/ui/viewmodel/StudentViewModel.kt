package com.yagnesh.presence.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yagnesh.presence.data.database.PresenceDatabase
import com.yagnesh.presence.data.model.AttendanceResult
import com.yagnesh.presence.data.model.Student
import com.yagnesh.presence.data.repository.StudentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository

    val students: Flow<List<Student>>

    var currentStudents: List<Student> = emptyList()

    var attendanceResult: AttendanceResult? = null

    init {

        val dao =
            PresenceDatabase
                .getDatabase(application)
                .studentDao()

        repository = StudentRepository(dao)

        students = repository.allStudents

        viewModelScope.launch {

            students.collect {

                currentStudents = it

            }

        }

    }

    fun insert(student: Student) {

        viewModelScope.launch {

            repository.insert(student)

        }

    }

    fun delete(student: Student) {

        viewModelScope.launch {

            repository.delete(student)

        }

    }

}