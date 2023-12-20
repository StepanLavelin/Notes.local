package com.example.android.course_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.example.android.course_project.App;
import com.example.android.course_project.R;
import com.example.android.course_project.data.AppData;
import com.example.android.course_project.data.TaskDao;
import com.example.android.course_project.model.Task;

public class MainActivityNewTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new_task);

        Intent intent = getIntent();


        //вызываем интерфейс для работы с БД
        TaskDao taskDao = App.getInstance().getDataBase().taskDao();

        EditText name_task = findViewById(R.id.name_task);
        EditText name_textTask = findViewById(R.id.name_textTask);
        Button saveTask = findViewById(R.id.saveTask);
        CheckBox case_Switch = (CheckBox) findViewById(R.id.switchCaseEdit);

        if (intent.getStringExtra("action").equals("edit")) {
            Task item = taskDao.getTaskBuId(intent.getIntExtra("id", -1));
            name_task.setText(item.textName);
            name_textTask.setText(item.text);
            case_Switch.setChecked(item.state != 0);

        }

        // кнопка которая должна сохранить задачу в исходном или отредактированном виде
        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                if (intent.getStringExtra("action").equals("edit")) {
                    task = taskDao.getTaskBuId(intent.getIntExtra("id", -1));
                }
                task.textName = String.valueOf(name_task.getText());
                task.text = String.valueOf(name_textTask.getText());
                if (case_Switch.isChecked()) task.state = 1;
                else task.state = 0;
                if (intent.getStringExtra("action").equals("edit")) {
                    taskDao.update(task);
                } else taskDao.insert(task);
                setResult(RESULT_OK);
                finish();
            }
        });

    }
}