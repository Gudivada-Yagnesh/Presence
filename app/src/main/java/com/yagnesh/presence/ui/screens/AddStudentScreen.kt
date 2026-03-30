package com.yagnesh.presence.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.yagnesh.presence.data.model.Student

@Composable
fun AddStudentScreen(
    onSaveStudent: (Student) -> Unit
) {

    var name by remember { mutableStateOf("") }
    var rollNumber by remember { mutableStateOf("") }
    var className by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
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
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Student Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = rollNumber,
            onValueChange = { rollNumber = it },
            label = { Text("Roll Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = className,
            onValueChange = { className = it },
            label = { Text("className") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                imagePicker.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Student Photo")
        }

        Spacer(modifier = Modifier.height(10.dp))

        imageUri?.let {

            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Student Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val student = Student(
                    name = name,
                    rollNumber = rollNumber,
                    className = className,
                    imagePath = imageUri?.toString() ?: ""
                )

                onSaveStudent(student)

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Student")
        }
    }
}