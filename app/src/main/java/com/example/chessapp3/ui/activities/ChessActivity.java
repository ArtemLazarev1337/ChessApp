package com.example.chessapp3.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.chessapp3.R;
import com.example.chessapp3.dao.ChessEntity;
import com.example.chessapp3.model.FieldButton;
import com.example.chessapp3.model.MyColor;
import com.example.chessapp3.model.Util;
import com.example.chessapp3.ui.fragments.addTaskNextFragment;
import com.example.chessapp3.viewModel.MainViewModel;

import java.util.List;
import java.util.prefs.Preferences;

public class ChessActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ListView taskListView;
    private MainViewModel mainViewModel;

    private static FieldButton[][] fieldButtons;
    private static FieldButton[][] backFieldButtons;

    private static final int ROWS = 8;
    private static final int COLUMNS = 8;

    private static final int[] selectedField = {-1, -1};
    private int currentTask;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        // Установка цвета доски
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        MyColor.getDeskTheme(prefs.getString("deskTheme", null));


        TableLayout chessboardLayout = findViewById(R.id.chessLayout);
        chessboardLayout.setShrinkAllColumns(true);

        TableLayout backChessboardLayout = findViewById(R.id.chessLayoutBack);
        backChessboardLayout.setShrinkAllColumns(true);
        backChessboardLayout.setBackgroundColor(MyColor.BoardColorBackground);

        TextView isCompleteText = findViewById(R.id.taskStatus);

        // Отрисовка задника
        backFieldButtons = new FieldButton[ROWS][COLUMNS];

        createBoardLayout(backChessboardLayout);

        // Отрисовка кнопок
        View.OnClickListener fieldListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = ((FieldButton) v).getFieldX();
                int y = ((FieldButton) v).getFieldY();
                FieldButton backField = backFieldButtons[x][y];
                FieldButton field = fieldButtons[x][y];

                if (selectedField[0] == -1 && selectedField[1] == -1) {
                    if (field.figureExists()) {
                        backField.setBackgroundColor(MyColor.WrongFieldColor);
                        selectedField[0] = x;
                        selectedField[1] = y;
                    }
                }
                else if (selectedField[0] == x && selectedField[1] == y) {
                    selectedField[0] = -1;
                    selectedField[1] = -1;
                    backField.setBackgroundColor(MyColor.pickFieldColor(x, y));
                }
                else {
                    mainViewModel.getAllChessTasks().observe(ChessActivity.this, new Observer<List<ChessEntity>>() {
                        @Override
                        public void onChanged(List<ChessEntity> chessEntities) {
                            ChessEntity chessEntity = chessEntities.get(currentTask);

                            FieldButton previousField = fieldButtons[selectedField[0]][selectedField[1]];
                            FieldButton nowField = fieldButtons[x][y];


                            String solve = chessEntity.solve;
                            boolean isWhiteMove = chessEntity.move.equals("Ход белых");

                            if ((previousField.getFigure() + "" + FieldButton.numToNotation(nowField.getFieldY()) + "" + (8 - nowField.getFieldX())).equals(Util.firstMove(solve, isWhiteMove))) {
                                nowField.setFigure(previousField.getFigure());
                                previousField.removeFigure();

                                previousField.setBackgroundColor(MyColor.pickFieldColor(previousField.getFieldX(), previousField.getFieldY()));
                                backFieldButtons[nowField.getFieldX()][nowField.getFieldY()].setBackgroundColor(MyColor.CompleteFieldColor);

//                                for (String move: solve) {
//                                    String figure =  move.substring(0, 2);
//                                    String[] motion = move.substring(2, 7).split("-");
//
//                                    fieldButtons[8 - Integer.parseInt(motion[0].substring(1))][FieldButton.notationToNum(motion[0].charAt(0))].removeFigure();
//                                    fieldButtons[8 - Integer.parseInt(motion[1].substring(1))][FieldButton.notationToNum(motion[1].charAt(0))].setFigure(figure);
//                                }
////(previousField.getFigure() + "" + FieldButton.numToNotation(previousField.getFieldY()) + "" + (8 - previousField.getFieldX()) + "-" + FieldButton.numToNotation(nowField.getFieldY()) + "" + (8 - previousField.getFieldX())).equals(solve[0])
                                isCompleteText.setTextColor(MyColor.RightSolveColor);
                                isCompleteText.setText(solve);
                            } else {
                                isCompleteText.setTextColor(MyColor.WrongSolveColor);
                                isCompleteText.setText("Неправильно!");
                            }
                        }
                    });
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

            chessboardLayout.addView(tableRow, i);
        }

        // Установка позиции
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mainViewModel.getAllChessTasks().observe(this, new Observer<List<ChessEntity>>() {
            @Override
            public void onChanged(List<ChessEntity> chessEntities) {
                if (chessEntities.size() == 0) {
                    isCompleteText.setTextColor(MyColor.BLACK);
                    isCompleteText.setText("Список задач пуст!");
                }

                taskListView = findViewById(R.id.taskList);

                String[] tasks = new String[chessEntities.size()];
                for (int k = 0; k < tasks.length; k++) {
                    tasks[k] = chessEntities.get(k).title;
                }

                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.task_item, tasks);
                taskListView.setAdapter(adapter);

                taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        currentTask = position;
                        createBoardLayout(backChessboardLayout);
                        selectedField[0] = -1;
                        selectedField[1] = -1;
                        ((TextView) findViewById(R.id.taskStatus)).setText("");

                        ChessEntity chessEntity = chessEntities.get(position);

                        TextView taskInfo = findViewById(R.id.taskInfoText);
                        taskInfo.setText(chessEntity.title + ", " + chessEntity.move);

                        for (int i = 0; i < ROWS; i++) {
                            for (int j = 0; j < COLUMNS; j++) {
                                fieldButtons[i][j].removeFigure();
                            }
                        }

                        String[] placement = chessEntity.placement.split(" ");

                        for (String place: placement) {
                            String figure = place.substring(0, 2);

                            int abscissa = Integer.parseInt(place.substring(3));
                            int ordinate = FieldButton.notationToNum(place.charAt(2));

                            fieldButtons[8 - abscissa][ordinate].setFigure(figure);
                        }
                    }
                });
            }
        });
    }
}

