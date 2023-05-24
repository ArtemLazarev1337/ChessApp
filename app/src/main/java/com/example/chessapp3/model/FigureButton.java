package com.example.chessapp3.model;

import android.content.Context;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

public class FigureButton extends AppCompatButton {
    private String figure;

    public FigureButton(Context context) {
        super(context);
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }
}
