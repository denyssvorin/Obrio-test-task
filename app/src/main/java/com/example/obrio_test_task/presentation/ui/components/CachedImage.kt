package com.example.obrio_test_task.presentation.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import com.example.obrio_test_task.presentation.ui.common.LocalImageCache

@Composable
fun CachedImage(
    url: String,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null
) {
    val imageCache = LocalImageCache.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(url) {
        bitmap = imageCache.loadImage(url)
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = null,
            modifier = modifier
        )
    } else {
        if (placeholder != null) {
            Image(
                painter = placeholder,
                contentDescription = null,
                modifier = modifier
            )
        } else {
            Box(
                modifier = modifier.background(Color.LightGray)
            )
        }
    }
}
