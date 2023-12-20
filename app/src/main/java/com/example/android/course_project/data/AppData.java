package com.example.android.course_project.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.android.course_project.model.Task;

// интерфейс для работы с БД
@Database(entities = {Task.class}, version = 3)
public abstract class AppData extends RoomDatabase {
    public abstract TaskDao taskDao();

}
