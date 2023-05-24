package com.example.chessapp3.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.chessapp3.R;
import com.example.chessapp3.dao.ChessEntity;
import com.example.chessapp3.model.Constants;
import com.example.chessapp3.model.FieldButton;
import com.example.chessapp3.ui.activities.AddTaskActivity;
import com.example.chessapp3.viewModel.MainViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class addTaskNextFragment extends Fragment {

    ArrayAdapter<String> categoriesAdapter, moveAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_task_next, container, false);

        RelativeLayout previousButton = view.findViewById(R.id.previousButton);
        RelativeLayout completeButton = view.findViewById(R.id.completeButton);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.addTaskFragContainer, new addTaskPreviousFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        EditText textTitle = view.findViewById(R.id.textTitle);
        AutoCompleteTextView dropdownCategories = view.findViewById(R.id.dropdownCategories);
        AutoCompleteTextView dropdownMove = view.findViewById(R.id.dropdownMove);
        EditText textSolve = view.findViewById(R.id.textSolve);

        categoriesAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, Constants.CATEGORIES);
        dropdownCategories.setAdapter(categoriesAdapter);

        moveAdapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_item, Constants.MOVE);
        dropdownMove.setAdapter(moveAdapter);

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textTitle.getText().toString().equals("") && !textSolve.getText().toString().equals("") && !dropdownCategories.getText().toString().equals("") && !dropdownMove.getText().toString().equals("")) {
                    MainViewModel mainViewModel = new ViewModelProvider(addTaskNextFragment.this).get(MainViewModel.class);

                    List<ChessEntity> taskList = new ArrayList<>();
                    ChessEntity chessTask = new ChessEntity();

                    String category = dropdownCategories.getText().toString();

                    String title = textTitle.getText().toString();

                    StringBuilder placement = new StringBuilder();
                    FieldButton[][] fieldButtons = AddTaskActivity.getFieldButtons();
                    for (int i = 0; i < fieldButtons.length; i++) {
                        for (int j = 0; j < fieldButtons[i].length; j++) {
                            FieldButton field = fieldButtons[i][j];
                            if (field.figureExists()) {
                                placement.append(field.getFigure())
                                        .append(FieldButton.numToNotation(field.getFieldY()))
                                        .append(8 - field.getFieldX())
                                        .append(" ");
                            }
                        }
                    }

                    String move = dropdownMove.getText().toString();

                    String solve = textSolve.getText().toString();

                    chessTask.category = category;
                    chessTask.title = title;
                    chessTask.placement = placement.toString().trim();
                    chessTask.move = move;
                    chessTask.solve = solve;

                    taskList.add(chessTask);

                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            mainViewModel.insertChessTasks(taskList);
                        }
                    });

                    Toast.makeText(getContext(), "Задача добавлена!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}