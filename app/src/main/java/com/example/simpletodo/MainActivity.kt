package com.example.simpletodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.simpletodo.model.container.ViewModelFactory
import com.example.simpletodo.ui.edit.EditViewModel
import com.example.simpletodo.ui.entry.EntryViewModel
import com.example.simpletodo.ui.home.HomeViewModel
import com.example.simpletodo.ui.main.MainScreen
import com.example.simpletodo.ui.main.MainViewModel
import com.example.simpletodo.ui.theme.SimpleToDoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel = MainViewModel()
            
            val homeViewModel by viewModels<HomeViewModel> { ViewModelFactory.Factory }
            val entryViewModel by viewModels<EntryViewModel> { ViewModelFactory.Factory }
            val editViewModel by viewModels<EditViewModel> { ViewModelFactory.Factory }

            val navController = rememberNavController()

            SimpleToDoTheme(dynamicColor = false) {
                MainScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    navController = navController,
                    mainViewModel = mainViewModel,
                    homeViewModel = homeViewModel,
                    entryViewModel = entryViewModel,
                    editViewModel = editViewModel
                )
            }
        }
    }
}