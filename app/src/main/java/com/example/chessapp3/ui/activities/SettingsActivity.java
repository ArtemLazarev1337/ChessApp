package com.example.chessapp3.ui.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chessapp3.R;
import com.example.chessapp3.dao.ChessEntity;
import com.example.chessapp3.model.Constants;
import com.example.chessapp3.ui.fragments.addTaskNextFragment;
import com.example.chessapp3.viewModel.MainViewModel;

import java.util.List;
import java.util.concurrent.Executors;

public class SettingsActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private ArrayAdapter<String> adapter;

    private SharedPreferences prefs;
    private RelativeLayout deskThemeButton;
    private AutoCompleteTextView dropdownDeskTheme;

    private ListView listDeleteTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        deskThemeButton = findViewById(R.id.buttonDeskTheme);
        dropdownDeskTheme = findViewById(R.id.dropdownDeskTheme);

        adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, Constants.THEMES);
        dropdownDeskTheme.setAdapter(adapter);

        deskThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("deskTheme", dropdownDeskTheme.getText().toString());
                editor.apply();
            }
        });

        mainViewModel = new ViewModelProvider(SettingsActivity.this).get(MainViewModel.class);
        mainViewModel.getAllChessTasks().observe(SettingsActivity.this, new Observer<List<ChessEntity>>() {
            @Override
            public void onChanged(List<ChessEntity> chessEntities) {
                if (chessEntities.size() == 0) {
                    findViewById(R.id.textDeleteTask).setVisibility(View.GONE);
                }
                String[] titles = new String[chessEntities.size()];
                for (int i = 0; i < chessEntities.size(); i++) {
                    titles[i] = chessEntities.get(i).title;
                }

                listDeleteTask = findViewById(R.id.listDeleteTask);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SettingsActivity.this, R.layout.task_item, titles);
                listDeleteTask.setAdapter(arrayAdapter);

                listDeleteTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                        builder.setMessage("Вы уверены что хотите удалить задачу " + chessEntities.get(position).title + "?")
                                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mainViewModel.deleteTaskByPlacement(chessEntities.get(position).placement);
                                                    }
                                                });

                                                Toast.makeText(getApplicationContext(), "Задача была удалена!", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setTitle("Удаление");
                        alertDialog.show();
                    }
                });
            }
        });
    }
}