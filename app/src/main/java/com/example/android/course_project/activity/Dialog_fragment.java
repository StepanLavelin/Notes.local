package com.example.android.course_project.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.course_project.data.TaskAdapter;
import com.example.android.course_project.data.TaskDao;
import com.example.android.course_project.model.Task;

import java.util.List;

public class Dialog_fragment extends DialogFragment {

    private final List<Task> list;
    private final TaskDao taskDao;
    private final RecyclerView.ViewHolder viewHolder;
    TaskAdapter taskAdapter;
    LiveData<List<Task>> liveData;

    public Dialog_fragment(RecyclerView.ViewHolder viewHolder, List<Task> list, TaskDao taskDao) {
        this.list = list;
        this.taskDao = taskDao;
        this.viewHolder = viewHolder;
        liveData = taskDao.getAllLive();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "Вы действительно хотите удалить задачу?";
        String message = "";
        String button1String = "Нет";
        String button2String = "Да";


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);  // заголовок
        builder.setMessage(message); // сообщение
        builder.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                добавить обновить список что бы показать что ничего не изменилось
                Task item = list.get(viewHolder.getAdapterPosition());
                taskDao.update(item);
            }
        });
        builder.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Task item = list.get(viewHolder.getAdapterPosition());
                taskDao.delete(item);
            }
        });
        builder.setCancelable(false);

        return builder.create();
    }


}
