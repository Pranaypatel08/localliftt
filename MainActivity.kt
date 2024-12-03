package com.example.locallift

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.locallift.ui.theme.LocalLiftTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalLiftTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavigationHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        composable("registration") { RegistrationScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("add_task") { AddTaskScreen(navController) }
        composable(
            route = "taskDetail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val sharedPrefs = SharedPrefsHelper(navController.context)
            val task = sharedPrefs.getTasks()?.find { it.id == taskId }
            if (task != null) {
                TaskDetailScreen(navController = navController, task = task)
            }
        }
        composable("filterAndSort") { FilterAndSortScreen(navController) }
    }
}

fun TaskDetailScreen(navController: NavHostController, task: Task) {
    TODO("Not yet implemented")
}

fun NavHostController.navigateToTaskDetail(taskId: String) {
    this.navigate("taskDetail/$taskId") // Navigate with the taskId as an argument
}
