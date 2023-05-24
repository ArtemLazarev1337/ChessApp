package com.example.chessapp3.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DataBaseManager {
    private DataBaseHelper db;
    private static DataBaseManager instance;

    private DataBaseManager(Context context) {
        db = Room.databaseBuilder(context,
                DataBaseHelper.class,
                DataBaseHelper.DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        initData(context);
                    }
                });
            }
        }).build();
    }

    public static DataBaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseManager(context.getApplicationContext());
        }
        return instance;
    }

    public DaoInt getDaoInt() {
        return db.daoInt();
    }

    public void initData(Context context) {
//        List<ChessEntity> taskList = new ArrayList<>();
//        ChessEntity chessTask = new ChessEntity();
//
//        chessTask.category = "tactic";
//        chessTask.title = "Мат в 2 хода";
//        chessTask.placement = "wke1 wqe6 wbe8 wnd5 wnf5 bke4 bpe5";
//        chessTask.move = "white";
//        chessTask.solve = "wqa6 bkd5 wqc6";
//
//        taskList.add(chessTask);
//
//        chessTask = new ChessEntity();
//
//        chessTask.category = "tactic";
//        chessTask.title = "Выигрыш ферзя";
//        chessTask.placement = "wkh4 wng5 wph6 bkf4 bpf5 bpa2";
//        chessTask.move = "white";
//        chessTask.solve = "wne6 bke4 wnd4 bkd4 wph7";
//
//        taskList.add(chessTask);
//
//        chessTask = new ChessEntity();
//
//        chessTask.category = "tactic";
//        chessTask.title = "Н. Бельчиков, 1971 год";
//        chessTask.placement = "wkc2 wqc6 wne5 wra2 bkb4 bnc5 bna4";
//        chessTask.move = "white";
//        chessTask.solve = "wnd7 bnd7 bra4";
//
//        taskList.add(chessTask);
//
//        chessTask = new ChessEntity();
//
//        chessTask.category = "tactic";
//        chessTask.title = "Прибыл - Орнштейн, Таллин, 1977";
//        chessTask.placement = "wkd6 wqd8 wnc5 wpb4 wpe5 bka7 bqf7";
//        chessTask.move = "black";
//        chessTask.solve = "bqd5 wkc7 bqc6";
//
//        taskList.add(chessTask);
//
//
//        DataBaseManager.getInstance(context).getDaoInt().insertChessTasks(taskList);
    }
}
