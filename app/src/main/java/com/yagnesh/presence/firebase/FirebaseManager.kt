package com.yagnesh.presence.firebase

import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class FirebaseManager {

    private val db = FirebaseFirestore.getInstance()

    fun markAttendance(
        studentName: String,
        rollNumber: String,
        branch: String
    ) {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val date = dateFormat.format(Date())
        val time = timeFormat.format(Date())

        val attendanceData = hashMapOf(
            "name" to studentName,
            "rollNumber" to rollNumber,
            "branch" to branch,
            "date" to date,
            "time" to time,
            "status" to "Present"
        )

        db.collection("attendance")
            .add(attendanceData)
            .addOnSuccessListener {

                println("Attendance stored successfully")

            }
            .addOnFailureListener {

                println("Attendance storage failed")

            }

    }

}