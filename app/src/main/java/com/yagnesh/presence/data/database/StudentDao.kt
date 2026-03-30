package com.yagnesh.presence.data.database

import androidx.room.*
import com.yagnesh.presence.data.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Update
    suspend fun update(student: Student)

    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Query("DELETE FROM students")
    suspend fun deleteAllStudents()

    // Get students of a specific class
    @Query("SELECT * FROM students WHERE className = :className")
    fun getStudentsByClass(className: String): Flow<List<Student>>

    // Get list of all classes
    @Query("SELECT DISTINCT className FROM students")
    fun getAllClasses(): Flow<List<String>>

}