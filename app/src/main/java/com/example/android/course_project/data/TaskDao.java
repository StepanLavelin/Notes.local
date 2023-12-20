package com.example.android.course_project.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.course_project.model.Task;

import java.util.List;

// интерфейс с которым будет работать БД

@Dao
public interface TaskDao {

    // создать задачу
    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Update
    void update(Task task);

    //выбрать все задачи Query-запрос, SELECT- выбрать, *-выбрать всё, FROM-откуда, из колонки task
    @Query("SELECT * FROM 'task'")
    List<Task> getAll();

    @Query("SELECT * FROM 'task'")
    LiveData<List<Task>> getAllLive();

    //получаем определенную запись по её id (WHERE - где по полю id такое же как мы передали в функцию)
    @Query("SELECT * FROM 'task' WHERE id IN (:id)")
    Task getTaskBuId(int id);


}
