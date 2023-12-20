package com.example.android.course_project.data;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.course_project.R;
import com.example.android.course_project.model.Task;

import java.util.List;

//  для выполнение функционала адаптера наследуемся, в общем виде можно скопировать для создания адаптера
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    // лист объектов который будем выводить
    private final List<Task> list;
    // грубо говоря наполнитель
    private final LayoutInflater inflater;
    private final OnTaskClick onTaskClick;

    // при создании адаптера передадим в list ресайклер вью и наполнитель (инфлатер)
    public TaskAdapter(Context context, List<Task> list, OnTaskClick onTaskClick) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.onTaskClick = onTaskClick;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.content_main, parent, false);
        return new ViewHolder(view);
    }

    // наполняем список объектами
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task item = list.get(position);
        // для вывода в списке
        holder.textName.setText((item.textName));
        //урок 2 21:45 holder.caseSwitch.setChecked(item.state) урок 3 18:36 переделываем как есть
        holder.caseSwitch.setChecked(item.state != 0);
        holder.itemView.setOnClickListener(v -> {
            onTaskClick.OnClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //     хранилище конкретного объекта, т.е. в данном примере текст и свитч
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // подключаем текст заметки (текст вью) и переключатель свитч, для этого
        //  добавляем финальные константы и вызываем их в вью холдер(в рамках сделанного мы запрашиваем в файле разметки item_case)

        final CheckBox caseSwitch;
        final TextView textName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            caseSwitch = itemView.findViewById(R.id.caseSwitch);
            textName = itemView.findViewById(R.id.textName);

        }
    }

    // создаем интерфейс для редактирования задачи
    public interface OnTaskClick {
        void OnClick(Task item);
    }
}

