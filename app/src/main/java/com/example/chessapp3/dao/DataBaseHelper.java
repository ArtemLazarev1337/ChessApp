package com.example.chessapp3.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChessEntity.class}, version = 1)
public abstract class DataBaseHelper extends RoomDatabase {
    public static final String DATABASE_NAME = "db25";

    public abstract DaoInt daoInt();
}
