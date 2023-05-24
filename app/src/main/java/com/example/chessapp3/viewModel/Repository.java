package com.example.chessapp3.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import com.example.chessapp3.dao.ChessEntity;
import com.example.chessapp3.dao.DaoInt;
import com.example.chessapp3.dao.DataBaseManager;

import java.util.List;

public class Repository {
    private DataBaseManager dataBaseManager;
    private DaoInt dao;

    public Repository(Context context) {
        dataBaseManager = DataBaseManager.getInstance(context);
        dao = dataBaseManager.getDaoInt();
    }

    public LiveData<List<ChessEntity>> getAllChessTasks() {
        return dao.getAllChessTasks();
    }

    public LiveData<List<ChessEntity>> getAllTacticTasks() {
        return dao.getAllTacticTasks();
    }

    public LiveData<List<ChessEntity>> getAllStudyTasks() {
        return dao.getAllStudyTasks();
    }

    public void insertChessTasks(List<ChessEntity> data) {
        dao.insertChessTasks(data);
    }

    public void deleteAllChessTasks() {
        dao.deleteAllChessTasks();
    }

    public void deleteTaskByPlacement(String placement) {
        dao.deleteTaskByPlacement(placement);
    }
}
