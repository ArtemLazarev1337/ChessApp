package com.example.chessapp3.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.chessapp3.R;
import com.example.chessapp3.dao.ChessEntity;
import com.example.chessapp3.model.FigureButton;
import com.example.chessapp3.model.Util;
import com.example.chessapp3.ui.activities.AddTaskActivity;
import com.example.chessapp3.ui.activities.ChessActivity;
import com.google.android.material.snackbar.Snackbar;

public class addTaskPreviousFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_task_previous, container, false);

        RelativeLayout nextButton = view.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.addTaskFragContainer, new addTaskNextFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        TableLayout figureGroup = view.findViewById(R.id.figureGroupLayout);
        figureGroup.setShrinkAllColumns(true);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FigureButton button = (FigureButton) v;
                AddTaskActivity.setPickedFigure(button.getFigure());
                // Snackbar.make(v, "Выбрано " + button.getFigure(), Snackbar.LENGTH_SHORT).show();
            }
        };

        String[] figures = {"wb", "wk", "wn", "wp", "wq", "wr", "bb", "bk", "bn", "bp", "bq", "br"};
        int index = 0;
        for (int i = 0; i < 2; i++) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < 6; j++) {
                FigureButton button = new FigureButton(getContext());

                String PACKAGE_NAME = getContext().getPackageName();
                int imgId = getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + figures[index], null, null);

                button.setBackgroundResource(imgId);
                button.setOnClickListener(listener);
                button.setFigure(figures[index]);

                index++;

                tableRow.addView(button, j);
            }

            figureGroup.addView(tableRow, i);
        }

        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.setGravity(Gravity.CENTER);
        FigureButton button = new FigureButton(getContext());
        button.setFigure("delete");

        String PACKAGE_NAME = getContext().getPackageName();
        int imgId = getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + "delete", null, null);

        button.setBackgroundResource(imgId);
        button.setOnClickListener(listener);

        tableRow.addView(button);

        figureGroup.addView(tableRow);

        return view;
    }
}