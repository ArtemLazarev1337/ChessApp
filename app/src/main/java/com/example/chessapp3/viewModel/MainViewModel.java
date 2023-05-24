package com.example.chessapp3.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.chessapp3.dao.ChessEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    Repository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<ChessEntity>> getAllChessTasks() {
        return repository.getAllChessTasks();
    }

    public LiveData<List<ChessEntity>> getAllTacticTasks() {
        return repository.getAllTacticTasks();
    }

    public LiveData<List<ChessEntity>> getAllStudyTasks() {
        return repository.getAllStudyTasks();
    }

    public void insertChessTasks(List<ChessEntity> data) {
        repository.insertChessTasks(data);
    }

    public void deleteAllChessTasks() {
        repository.deleteAllChessTasks();
    }

    public void deleteTaskByPlacement(String placement) {
        repository.deleteTaskByPlacement(placement);
    }
}
