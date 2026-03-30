package com.yagnesh.presence.ai

import android.graphics.Bitmap
import kotlin.math.sqrt

class FaceRecognitionManager {

    fun compareFaces(face1: FloatArray, face2: FloatArray): Float {

        var sum = 0f

        for (i in face1.indices) {

            val diff = face1[i] - face2[i]

            sum += diff * diff

        }

        return sqrt(sum)

    }

    fun isSamePerson(distance: Float): Boolean {

        val threshold = 1.0f

        return distance < threshold

    }

    fun generateFakeEmbedding(bitmap: Bitmap): FloatArray {

        val embedding = FloatArray(128)

        for (i in embedding.indices) {

            embedding[i] = (bitmap.width + bitmap.height + i).toFloat() / 1000f

        }

        return embedding

    }

}