package com.example.obrio_test_task.presentation.ui.common

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.obrio_test_task.presentation.utils.imagememorycache.ImageMemoryCache

val LocalImageCache = staticCompositionLocalOf<ImageMemoryCache> {
    error("No ImageMemoryCache provided")
}