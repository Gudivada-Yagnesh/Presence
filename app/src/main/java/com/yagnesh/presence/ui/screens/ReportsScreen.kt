package com.yagnesh.presence.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yagnesh.presence.ui.viewmodel.StudentViewModel

@Composable
fun ReportsScreen(
    viewModel: StudentViewModel = viewModel()
) {

    val result = viewModel.attendanceResult

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Attendance Report",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (result == null) {

            Text("No attendance captured yet")

        } else {

            Text("Total Students: ${result.totalStudents}")
            Text("Present: ${result.presentStudents}")
            Text("Absent: ${result.absentStudents}")

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Present Students",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {

                items(result.presentStudentsList) { studentName ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            Text(studentName)

                        }

                    }

                }

            }

        }

    }

}