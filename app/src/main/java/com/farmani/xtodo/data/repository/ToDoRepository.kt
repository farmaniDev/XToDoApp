package com.farmani.xtodo.data.repository

import androidx.lifecycle.LiveData
import com.farmani.xtodo.data.ToDoDao
import com.farmani.xtodo.data.models.ToDoData

class ToDoRepository(private val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }
}