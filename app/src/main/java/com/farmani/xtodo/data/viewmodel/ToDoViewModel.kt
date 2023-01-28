package com.farmani.xtodo.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.farmani.xtodo.data.ToDoDatabase
import com.farmani.xtodo.data.models.ToDoData
import com.farmani.xtodo.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {
    private val toDoDao = ToDoDatabase.getDatabase(application).toDoDao()
    private val repository: ToDoRepository = ToDoRepository(toDoDao)

    private val getAllData: LiveData<List<ToDoData>> = repository.getAllData

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

}