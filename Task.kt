package com.example.locallift

import java.util.UUID

data class Task(
    val title: String,
    val description: String,
    var isVolunteered: Boolean = false,
    val id: String = UUID.randomUUID().toString() // Unique identifier for each task
)