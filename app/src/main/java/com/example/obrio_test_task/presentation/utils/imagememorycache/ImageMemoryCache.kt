package com.example.obrio_test_task.presentation.utils.imagememorycache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class ImageMemoryCache(private val maxSize: Int = 20) {

    private val cache = LinkedHashMap<String, Bitmap>(maxSize, 0.75f, true)

    suspend fun loadImage(url: String): Bitmap? {
        synchronized(cache) {
            cache[url]?.let { return it }
        }

        val bitmap = downloadImage(url)

        bitmap?.let {
            synchronized(cache) {
                if (cache.size >= maxSize) {
                    val firstKey = cache.keys.first()
                    cache.remove(firstKey)
                }
                cache[url] = it
            }
        }

        return bitmap
    }

    private suspend fun downloadImage(url: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val stream = URL(url).openStream()
                BitmapFactory.decodeStream(stream)
            } catch (e: Exception) {
                e.printStackTrace()
                if (e is CancellationException) throw e
                null
            }
        }
    }

    fun clear() {
        synchronized(cache) { cache.clear() }
    }
}
