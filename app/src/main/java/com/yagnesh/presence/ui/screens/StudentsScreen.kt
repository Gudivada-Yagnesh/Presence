package com.yagnesh.presence.ui.screens

import com.yagnesh.presence.ai.FaceApi
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.yagnesh.presence.data.model.Student
import com.yagnesh.presence.storage.ImageStorage
import com.yagnesh.presence.ui.viewmodel.StudentViewModel

@Composable
fun StudentsScreen(
    viewModel: StudentViewModel = viewModel()
) {

    val context = LocalContext.current

    val students by viewModel.students.collectAsState(initial = emptyList())

    var name by remember { mutableStateOf("") }
    var roll by remember { mutableStateOf("") }
    var className by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            imageUri = uri
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Add Student",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Student Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = roll,
            onValueChange = { roll = it },
            label = { Text("Roll Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = className,
            onValueChange = { className = it },
            label = { Text("Class Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { imagePicker.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Student Photo")
        }

        imageUri?.let {

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {

                if (
                    name.isNotBlank() &&
                    roll.isNotBlank() &&
                    className.isNotBlank() &&
                    imageUri != null
                ) {

                    val path = imageUri.toString()

                    viewModel.insert(
                        Student(
                            name = name,
                            rollNumber = roll,
                            className = className,
                            imagePath = path
                        )
                    )

                    FaceApi.createFaceSet(className)

                    FaceApi.registerFace(
                        path,
                        className,
                        name
                    )

                    name = ""
                    roll = ""
                    className = ""
                    imageUri = null
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Student")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Students List",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn {

            items(students) { student ->

                StudentItem(
                    student = student,
                    onDelete = { viewModel.delete(student) }
                )

            }

        }

    }

}

@Composable
fun StudentItem(
    student: Student,
    onDelete: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(text = student.name)

            Text(text = "Roll: ${student.rollNumber}")

            Text(text = "Class: ${student.className}")

            Spacer(modifier = Modifier.height(6.dp))

            Image(
                painter = rememberAsyncImagePainter(student.imagePath),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onDelete
            ) {
                Text("Delete")
            }

        }

    }

}