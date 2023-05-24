package com.example.chessapp3.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.chessapp3.R;
import com.example.chessapp3.model.FieldButton;
import com.example.chessapp3.model.MyColor;
import com.example.chessapp3.ui.fragments.addTaskNextFragment;
import com.example.chessapp3.ui.fragments.addTaskPreviousFragment;
import com.example.chessapp3.viewModel.MainViewModel;

public class AddTaskActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private ListView taskListView;
    private MainViewModel mainViewModel;

    private static FieldButton[][] fieldButtons;
    private static FieldButton[][] backFieldButtons;

    private static final int ROWS = 8;
    private static final int COLUMNS = 8;

    private static String pickedFigure = "";

    private void createBoardLayout(TableLayout tableLayout) {
        tableLayout.removeAllViews();
        for (int i = 0; i < ROWS; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < COLUMNS; j++) {
                FieldButton field = new FieldButton(this);

                field.setBackgroundColor(MyColor.pickFieldColor(i, j));
                field.setClickable(false);

                backFieldButtons[i][j] = field;

                tableRow.addView(field, j);
            }

            tableLayout.addView(tableRow, i);
        }
    }

    public static void setPickedFigure(String pickedFigure) {
        AddTaskActivity.pickedFigure = pickedFigure;
    }

    public static FieldButton[][] getFieldButtons() {
        return fieldButtons;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Установка цвета доски
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        MyColor.getDeskTheme(prefs.getString("deskTheme", null));


        TableLayout addChessboardLayout = findViewById(R.id.addChessLayout);
        addChessboardLayout.setShrinkAllColumns(true);

        TableLayout addBackChessboardLayout = findViewById(R.id.addChessLayoutBack);
        addBackChessboardLayout.setShrinkAllColumns(true);
        addBackChessboardLayout.setBackgroundColor(MyColor.BoardColorBackground);

        // Отрисовка задника
        backFieldButtons = new FieldButton[ROWS][COLUMNS];

        createBoardLayout(addBackChessboardLayout);

        // Отрисовка кнопок
        View.OnClickListener fieldListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FieldButton field = (FieldButton) v;
                if (pickedFigure.equals("delete")) {
                    field.removeFigure();
                }
                else if (!pickedFigure.equals("")) {
                    field.setFigure(pickedFigure);
                }
            }
        };

        fieldButtons = new FieldButton[ROWS][COLUMNS];

        for (int i = 0; i < ROWS; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < COLUMNS; j++) {
                FieldButton field = new FieldButton(this);

                field.setBackgroundColor(MyColor.pickFieldColor(i, j));

                field.setFieldX(i);
                field.setFieldY(j);
                field.setOnClickListener(fieldListener);

                fieldButtons[i][j] = field;

                tableRow.addView(field, j);
            }

            addChessboardLayout.addView(tableRow, i);
        }
    }
}