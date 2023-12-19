package org.example.decompose_navigation

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import navigation.DefaultRootScreenComponent

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent {
            DefaultRootScreenComponent(componentContext = it)
        }

        setContent {
            App(component = root)
        }
    }
}

/*
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}*/
