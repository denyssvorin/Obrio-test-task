package com.example.obrio_test_task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.example.obrio_test_task.presentation.navigation.NavGraph
import com.example.obrio_test_task.presentation.ui.common.LocalImageCache
import com.example.obrio_test_task.presentation.ui.theme.ObriotesttaskTheme
import com.example.obrio_test_task.presentation.utils.imagememorycache.ImageMemoryCache
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var imageCache: ImageMemoryCache

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(
                LocalImageCache provides imageCache
            ) {
                ObriotesttaskTheme {
                    NavGraph()
                }
            }
        }
    }
}
