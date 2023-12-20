package com.example.android.course_project.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // текст задачи
    @ColumnInfo(name = "text")
    public String text;

    //название задачи
    @ColumnInfo(name = "textName")
    public String textName;

    // выполнена ли задача
    @ColumnInfo(name = "state")
    public int state;

    // конструктор алт+инсерт
    public Task(String textName, String text, int state) {
        this.textName = textName;
        this.text = text;
        this.state = state;
    }

    public Task(int id, String text, String textName, int state) {
        this.id = id;
        this.text = text;
        this.textName = textName;
        this.state = state;
    }

    public Task() {
    }
}
