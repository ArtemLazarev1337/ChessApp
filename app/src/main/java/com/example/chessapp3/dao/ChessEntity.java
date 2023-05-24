package com.example.chessapp3.dao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "chess", indices = {@Index(value = {"placement"}, unique = true)})
public class ChessEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "category")
    @NonNull
    public String category;

    @ColumnInfo(name = "title")
    @NonNull
    public String title;

    @ColumnInfo(name = "move")
    @NonNull
    public String move = "";

    @ColumnInfo(name = "placement")
    @NonNull
    public String placement = "";

    @ColumnInfo(name = "solve")
    @NonNull
    public String solve = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
