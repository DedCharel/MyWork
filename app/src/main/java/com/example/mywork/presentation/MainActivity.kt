package com.example.mywork.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mywork.presentation.screen.works.WorksScreen
import com.example.mywork.presentation.ui.theme.MyWorkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyWorkTheme {
                WorksScreen(
                    onAddWorkClick = {}
                )
            }
        }
    }
}
