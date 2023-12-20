package com.example.android.course_project.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.course_project.App;
import com.example.android.course_project.R;
import com.example.android.course_project.data.TaskAdapter;
import com.example.android.course_project.data.TaskDao;
import com.example.android.course_project.model.Task;

import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView listCase;
    // создаем список того, что мы хотим хранить List <имя класса(возможно объекта)> название объекта list, конструктор данного объекта - Case
    List<Task> list;
    TaskDao taskDao;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCase = (RecyclerView) findViewById(R.id.listCases);

        taskDao = App.getInstance().getDataBase().taskDao();

        list = taskDao.getAll();
        //правило как рецикл вью выводить элекменты(без этого чистый экран)
        listCase.setLayoutManager(new LinearLayoutManager(this));
        // создаем экземпляр адаптера и вызываем его
        taskAdapter = new TaskAdapter(this, list, new TaskAdapter.OnTaskClick() {
            @Override
            public void OnClick(Task item) {
                Intent intent = new Intent(MainActivity.this, MainActivityNewTask.class);
                intent.putExtra("action", "edit");
                intent.putExtra("id", item.id);
                startActivityForResult(intent, 1);
            }
        });
        listCase.setAdapter(taskAdapter);

        LiveData<List<Task>> liveData = taskDao.getAllLive();

        liveData.observe(this, new Observer<List<Task>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                taskAdapter.notifyDataSetChanged();
            }
        });


        Button addButton = (Button) findViewById(R.id.addbutton);

        // добавляем наблюдателя
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivityNewTask.class);
                intent.putExtra("action", "add");
                startActivityForResult(intent, 1);
            }
        });

        // удаление задачи по свайпу
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // обработка события свайп
                FragmentManager manager = getSupportFragmentManager();
                Dialog_fragment dialog_fragment = new Dialog_fragment(viewHolder, list, taskDao);
                System.out.println(list);
                dialog_fragment.show(manager, "myDialog");
            }
        }).attachToRecyclerView(listCase);

        // сортировка по кнопкам
        Button sortUpButton = (Button) findViewById(R.id.button);
        Button sortDawnButton = (Button) findViewById(R.id.button2);

        sortUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.sort(new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return Integer.compare(o1.state, o2.state);
                    }
                });
                taskAdapter.notifyDataSetChanged();
            }
        });

        sortDawnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.sort(new Comparator<Task>() {
                    @Override
                    public int compare(Task o1, Task o2) {
                        return Integer.compare(o2.state, o1.state);
                    }
                });
                taskAdapter.notifyDataSetChanged();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}


