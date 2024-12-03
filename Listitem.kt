package com.example.locallift

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.locallift.Task as Task1

@Composable
fun ListItem(task: Task1, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = task.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (task.isVolunteered) "Volunteered" else "Available",
                style = MaterialTheme.typography.labelSmall,
                color = if (task.isVolunteered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
        }
    }
}
