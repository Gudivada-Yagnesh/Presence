package com.yagnesh.presence.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.yagnesh.presence.ui.viewmodel.StudentViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: StudentViewModel = viewModel()
) {

    val students by viewModel.students.collectAsState(initial = emptyList())

    val classList =
        students.map { it.className }.distinct()

    var selectedClass by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Smart Attendance System",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Select Class",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedTextField(
                value = selectedClass,
                onValueChange = {},
                readOnly = true,
                label = { Text("Class") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {

                    IconButton(
                        onClick = { expanded = true }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null
                        )

                    }

                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {

                if (classList.isEmpty()) {

                    DropdownMenuItem(
                        text = { Text("No classes available") },
                        onClick = {}
                    )

                } else {

                    classList.forEach { className ->

                        DropdownMenuItem(
                            text = { Text(className) },
                            onClick = {

                                selectedClass = className

                                expanded = false

                            }
                        )

                    }

                }

            }

        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {

                if (selectedClass.isNotBlank()) {

                    navController.navigate("camera/$selectedClass")

                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Start Face Attendance")

        }

    }

}