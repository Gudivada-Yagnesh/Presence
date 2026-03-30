package com.yagnesh.presence.storage

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object ImageStorage {

    fun saveStudentImage(
        context: Context,
        className: String,
        studentName: String,
        imageUri: Uri
    ): String {

        val classFolder =
            File(context.filesDir, className)

        if (!classFolder.exists()) {
            classFolder.mkdirs()
        }

        val studentFile =
            File(classFolder, "$studentName.jpg")

        val inputStream =
            context.contentResolver.openInputStream(imageUri)

        val outputStream =
            FileOutputStream(studentFile)

        inputStream?.copyTo(outputStream)

        inputStream?.close()
        outputStream.close()

        return studentFile.absolutePath
    }
}