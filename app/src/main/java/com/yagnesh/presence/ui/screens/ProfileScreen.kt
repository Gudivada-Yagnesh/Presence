package com.yagnesh.presence.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yagnesh.presence.ui.viewmodel.StudentViewModel

@Composable
fun ProfileScreen(
    viewModel: StudentViewModel = viewModel()
) {

    val students by viewModel.students.collectAsState(initial = emptyList())

    val totalStudents = students.size
    val totalClasses = students.map { it.className }.distinct().size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Teacher Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text("Teacher Name: Yagnesh")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Department: Computer Science")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Subject: Physical Education")

            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Faculty Statistics",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text("Total Students: $totalStudents")

                Spacer(modifier = Modifier.height(8.dp))

                Text("Total Classes: $totalClasses")

            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Smart Attendance System v1.0",
            style = MaterialTheme.typography.bodyMedium
        )

    }

}