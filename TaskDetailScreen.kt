package com.example.locallift

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(navController: NavHostController, taskId: String?) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPrefsHelper(context) }
    val tasks = sharedPrefs.getTasks() ?: listOf() // Provide default empty list if null

    val task = tasks.find { it.id == taskId } // Find the task using taskId

    if (task != null) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Task Details") }) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Title: ${task.title}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Description: ${task.description}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Status: ${if (task.isVolunteered) "Volunteered" else "Available"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Toggle volunteer status and update in Shared Preferences
                        task.isVolunteered = !task.isVolunteered
                        val updatedTasks = tasks.toMutableList().apply {
                            val index = indexOfFirst { it.id == task.id }
                            if (index != -1) this[index] = task
                        }
                        sharedPrefs.saveTasks(updatedTasks)
                        navController.navigate("home") // Navigate back to Home
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (task.isVolunteered) "Cancel Volunteering" else "Volunteer")
                }
            }
        }
    } else {
        // Handle case where task is null (not found)
        Scaffold(
            topBar = { TopAppBar(title = { Text("Task Not Found") }) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(text = "Task not found", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
