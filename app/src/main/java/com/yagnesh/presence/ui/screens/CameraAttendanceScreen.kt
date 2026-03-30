package com.yagnesh.presence.ui.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.yagnesh.presence.data.model.AttendanceResult
import com.yagnesh.presence.ui.viewmodel.StudentViewModel
import java.util.concurrent.Executors

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraAttendanceScreen(
    navBackStackEntry: NavBackStackEntry,
    viewModel: StudentViewModel = viewModel()
) {

    val selectedClass =
        navBackStackEntry.arguments?.getString("className") ?: ""

    val students by viewModel.students.collectAsState(initial = emptyList())

    val classStudents =
        students.filter { it.className == selectedClass }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var showCamera by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraExecutor = remember {
        Executors.newSingleThreadExecutor()
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            imageUri = uri
        }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        if (!showCamera && imageUri == null) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Text(
                    text = "Class: $selectedClass",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { showCamera = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Capture Photo")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { galleryLauncher.launch("image/*") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Upload Photo")
                }

            }

        }

        if (showCamera) {

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->

                    val previewView = PreviewView(ctx)

                    val cameraProviderFuture =
                        ProcessCameraProvider.getInstance(ctx)

                    cameraProviderFuture.addListener({

                        val cameraProvider = cameraProviderFuture.get()

                        val preview = Preview.Builder()
                            .build()
                            .also {
                                it.setSurfaceProvider(
                                    previewView.surfaceProvider
                                )
                            }

                        val imageAnalyzer =
                            ImageAnalysis.Builder()
                                .setTargetResolution(Size(1280, 720))
                                .setBackpressureStrategy(
                                    ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
                                )
                                .build()

                        val detector = FaceDetection.getClient()

                        imageAnalyzer.setAnalyzer(cameraExecutor) { imageProxy ->

                            val mediaImage = imageProxy.image

                            if (mediaImage != null) {

                                val image =
                                    InputImage.fromMediaImage(
                                        mediaImage,
                                        imageProxy.imageInfo.rotationDegrees
                                    )

                                detector.process(image)
                                    .addOnSuccessListener { faces ->

                                        if (faces.isNotEmpty()) {

                                            val presentStudents =
                                                com.yagnesh.presence.ai.FaceMatcher.matchFaces(
                                                    faces.size,
                                                    classStudents
                                                )

                                            val absentStudents =
                                                classStudents.filter {
                                                    !presentStudents.contains(it)
                                                }

                                            val result =
                                                AttendanceResult(
                                                    totalStudents = classStudents.size,
                                                    presentStudents = presentStudents.size,
                                                    absentStudents = absentStudents.size,
                                                    presentStudentsList = presentStudents.map { it.name }
                                                )

                                            viewModel.attendanceResult = result

                                        }

                                    }
                                    .addOnCompleteListener {
                                        imageProxy.close()
                                    }

                            } else {

                                imageProxy.close()

                            }

                        }

                        val cameraSelector =
                            CameraSelector.DEFAULT_FRONT_CAMERA

                        cameraProvider.unbindAll()

                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalyzer
                        )

                    }, ContextCompat.getMainExecutor(ctx))

                    previewView

                }
            )

        }

        imageUri?.let { uri ->

            val detector = FaceDetection.getClient()

            LaunchedEffect(uri) {

                val image =
                    InputImage.fromFilePath(context, uri)

                detector.process(image)
                    .addOnSuccessListener { faces ->

                        val presentStudents =
                            com.yagnesh.presence.ai.FaceMatcher.matchFaces(
                                faces.size,
                                classStudents
                            )

                        val absentStudents =
                            classStudents.filter {
                                !presentStudents.contains(it)
                            }

                        val result =
                            AttendanceResult(
                                totalStudents = classStudents.size,
                                presentStudents = presentStudents.size,
                                absentStudents = absentStudents.size,
                                presentStudentsList = presentStudents.map { it.name }
                            )

                        viewModel.attendanceResult = result

                    }

            }

        }

    }

}