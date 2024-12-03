package com.example.locallift

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class SharedPrefsHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LocalLiftPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUserProfile(userProfile: UserProfile) {
        val userJson = gson.toJson(userProfile)
        sharedPreferences.edit().putString("user_profile", userJson).apply()
    }

    fun getUserProfile(): UserProfile? {
        val userJson = sharedPreferences.getString("user_profile", null)
        return userJson?.let { gson.fromJson(it, UserProfile::class.java) }
    }

    fun isValidCredentials(username: String, password: String): Boolean {
        val storedUser = getUserProfile()
        return storedUser != null &&
                storedUser.username == username &&
                storedUser.password == password
    }

    fun getTasks(): List<Task>? {
        val tasksJson = sharedPreferences.getString("tasks", null)
        return if (tasksJson != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            gson.fromJson(tasksJson, type)
        } else {
            emptyList() // Return an empty list instead of null
        }
    }

    fun saveTasks(tasks: List<Task>) {
        val tasksJson = gson.toJson(tasks)
        sharedPreferences.edit().putString("tasks", tasksJson).apply()
    }
}

