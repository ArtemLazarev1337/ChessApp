package com.example.chessapp3.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DaoInt {
    @Query("SELECT * FROM 'chess'")
    LiveData<List<ChessEntity>> getAllChessTasks();

    @Query("SELECT * FROM 'chess' WHERE category = 'tactic'")
    LiveData<List<ChessEntity>> getAllTacticTasks();

    @Query("SELECT * FROM 'chess' WHERE category = 'study'")
    LiveData<List<ChessEntity>> getAllStudyTasks();

    @Insert
    void insertChessTasks(List<ChessEntity> data);

    @Query("DELETE FROM chess")
    void deleteAllChessTasks();

    @Query("DELETE FROM chess WHERE placement = :placement")
    void deleteTaskByPlacement(String placement);
}
