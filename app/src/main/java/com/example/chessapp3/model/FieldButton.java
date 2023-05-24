package com.example.chessapp3.model;

import android.content.Context;

public class FieldButton extends androidx.appcompat.widget.AppCompatButton {
    private int fieldX;
    private int fieldY;
    private String figure = "";
    public static char[] notation = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    public FieldButton(Context context) {
        super(context);
    }

    public void setFigure(String figure) {
        if (figure.length() == 2) {
            String path = figure.substring(0, 2);

            String PACKAGE_NAME = getContext().getPackageName();
            int imgId = getResources().getIdentifier(PACKAGE_NAME + ":drawable/" + path , null, null);

            setBackgroundResource(imgId);
            this.figure = figure;
        }
    }

    public void removeFigure() {
        if (!figure.equals("")) {
            setBackgroundResource(0);
            this.figure = "";
        }
    }

    public String getFigure() {
        return figure;
    }

    public boolean figureExists() {
        return !figure.equals("");
    }

    public static int notationToNum(char n) {
        for (int i = 0; i < notation.length; i++) {
            if (notation[i] == n) return i;
        }
        return -1;
    }

    public static char numToNotation(int n) {
        return notation[n];
    }

    public int getFieldX() {
        return fieldX;
    }

    public void setFieldX(int fieldX) {
        this.fieldX = fieldX;
    }

    public int getFieldY() {
        return fieldY;
    }

    public void setFieldY(int fieldY) {
        this.fieldY = fieldY;
    }
}
