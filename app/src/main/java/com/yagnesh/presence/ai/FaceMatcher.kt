package com.yagnesh.presence.ai

import com.yagnesh.presence.data.model.Student

object FaceMatcher {

    fun matchFaces(
        detectedFaces: Int,
        students: List<Student>
    ): List<Student> {

        val presentStudents = mutableListOf<Student>()

        for (i in 0 until detectedFaces) {

            if (i < students.size) {
                presentStudents.add(students[i])
            }

        }

        return presentStudents
    }

}