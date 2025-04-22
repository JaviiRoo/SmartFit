package com.rodriguez.smartfitv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.rodriguez.smartfitv2.navigation.AppNavigation
import androidx.compose.runtime.Composable



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MaterialTheme {
                AppNavigation(navController = navController)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        AppNavigation(navController = navController)
    }
}

