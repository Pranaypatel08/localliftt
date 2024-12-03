package com.example.locallift

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPrefsHelper(context) }
    val tasks = sharedPrefs.getTasks() ?: listOf() // Provide a default empty list if null

    Scaffold(
        topBar = { TopAppBar(title = { Text("Home") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_task") }) {
                Text("+")
            }
        }
    ) { innerPadding ->
        if (tasks.isEmpty()) { // Check if the list is empty
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(text = "No tasks available")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(tasks) { task ->
                    ListItem(task = task) {
                        navController.navigate("taskDetail/${task.title}") // Pass task title or ID
                    }
                }
            }
        }
    }
}
