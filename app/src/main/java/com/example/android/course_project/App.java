package com.example.android.course_project;


import android.app.Application;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.course_project.data.AppData;

// класс для работы с базой данных (реализация генерации БД в отдельном классе)
public class App extends Application {
    public static App instance;
    private AppData dataBase;
    private final String nameDB = "dataBase";


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataBase = Room.databaseBuilder(
                        this,
                        AppData.class,
                        nameDB
                )
                .allowMainThreadQueries()
                // УДАЛИТЬ ПОСЛЕ ОКОНЧАНИЯ РАЗРАБОТКИ ПРИЛОЖЕНИЯ!!! (.ФАЛЛБЭК)
//                .fallbackToDestructiveMigration()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppData getDataBase() {
        return dataBase;
    }
}
