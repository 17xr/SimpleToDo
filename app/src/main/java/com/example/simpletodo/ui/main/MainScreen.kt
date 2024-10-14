package com.example.simpletodo.ui.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.simpletodo.model.container.AppScreen
import com.example.simpletodo.model.container.toTask
import com.example.simpletodo.ui.edit.EditScreen
import com.example.simpletodo.ui.edit.EditScreenTopBar
import com.example.simpletodo.ui.edit.EditViewModel
import com.example.simpletodo.ui.entry.EntryScreen
import com.example.simpletodo.ui.entry.EntryScreenTopBar
import com.example.simpletodo.ui.entry.EntryViewModel
import com.example.simpletodo.ui.home.HomeScreen
import com.example.simpletodo.ui.home.HomeScreenFAB
import com.example.simpletodo.ui.home.HomeScreenTopAppBar
import com.example.simpletodo.ui.home.HomeViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel,
    entryViewModel: EntryViewModel,
    editViewModel: EditViewModel
) {
    val mainUiState = mainViewModel.appUiState.collectAsState()
    val homeUiState = homeViewModel.homeUiState.collectAsState()
    val entryUiState = entryViewModel.entryUiState.collectAsState()
    val editUiState = editViewModel.editUiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            when (mainUiState.value.appScreen) {
                AppScreen.Home -> HomeScreenTopAppBar()
                AppScreen.Entry -> EntryScreenTopBar(
                    onBackClicked = {
                        navController.navigateUp()
                    }
                )

                AppScreen.Edit -> EditScreenTopBar(
                    onBackClicked = {
                        navController.navigateUp()
                    }
                )
            }
        },
        floatingActionButton = {
            if (mainUiState.value.appScreen == AppScreen.Home) {
                HomeScreenFAB(
                    onAddClicked = {
                        entryViewModel.clearUiState()
                        navController.navigate(route = AppScreen.Entry.name)
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            modifier = Modifier
                .padding(paddingValues = innerPadding),
            navController = navController,
            startDestination = AppScreen.Home.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                )
            }
        ) {
            composable(route = AppScreen.Home.name) {
                mainViewModel.switchScreen(appScreen = AppScreen.Home)

                HomeScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    homeUiState = homeUiState.value,
                    onTaskUpdate = homeViewModel::updateTask,
                    onTaskClicked = {
                        editViewModel.updateTaskDetails(taskDetails = it)
                        navController.navigate(route = AppScreen.Edit.name)
                    }
                )
            }

            composable(route = AppScreen.Entry.name) {
                mainViewModel.switchScreen(appScreen = AppScreen.Entry)

                EntryScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    entryUiState = entryUiState.value,
                    onValueChange = entryViewModel::updateTaskDetails,
                    onSaveClicked = {
                        entryViewModel.insertTask(taskDetails = it)
                        navController.navigateUp()
                    }
                )
            }

            composable(route = AppScreen.Edit.name) {
                mainViewModel.switchScreen(appScreen = AppScreen.Edit)

                EditScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    editUiState = editUiState.value,
                    onValueChange = editViewModel::updateTaskDetails,
                    onSaveClicked = {
                        editViewModel.updateTask(task = it.toTask())
                        navController.navigateUp()
                    },
                    onDeleteClicked = {
                        editViewModel.deleteTask(task = it)
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}