package com.yagnesh.presence.ai

import okhttp3.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.MultipartBody
import okhttp3.Callback
import okhttp3.Call
import okhttp3.Response
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaType
import java.io.File
import java.io.IOException

object FaceApi {

    const val API_KEY = "YOUR_API_KEY"
    const val API_SECRET = "YOUR_API_SECRET"

    private val client = OkHttpClient()

    // CREATE CLASS FACESET
    fun createFaceSet(className: String) {

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("api_key", API_KEY)
            .addFormDataPart("api_secret", API_SECRET)
            .addFormDataPart("display_name", className)
            .addFormDataPart("outer_id", className)
            .build()

        val request = Request.Builder()
            .url("https://api-us.faceplusplus.com/facepp/v3/faceset/create")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                val res = response.body?.string()

                println("FaceSet Created: $res")

            }

        })
    }


    // REGISTER STUDENT FACE
    fun registerFace(
        imagePath: String,
        className: String,
        studentName: String
    ) {

        val file = File(imagePath)

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("api_key", API_KEY)
            .addFormDataPart("api_secret", API_SECRET)
            .addFormDataPart(
                "image_file",
                file.name,
                file.asRequestBody("image/jpeg".toMediaType())
            )
            .build()

        val request = Request.Builder()
            .url("https://api-us.faceplusplus.com/facepp/v3/detect")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {

                val responseBody = response.body?.string()

                println("Face++ Detect Response: $responseBody")

            }

        })
    }

}