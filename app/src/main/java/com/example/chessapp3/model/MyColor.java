package com.example.chessapp3.model;

import android.graphics.Color;

public class MyColor extends Color {
    static public int BoardColor1 = rgb(222, 184, 135);
    static public int BoardColor2 = rgb(139, 69, 19);
    static public int BoardColorBackground = rgb(159, 103, 44);
    static public int CompleteFieldColor = rgb(113, 169, 44);
    static public int WrongFieldColor = rgb(206, 32, 41);
    static public int RightSolveColor = rgb(107, 225, 55);
    static public int WrongSolveColor = rgb(225, 6, 0);

    static public int pickFieldColor(int x, int y) {
        if (x % 2 == 0) {
            if (y % 2 == 0) return BoardColor1;
            else return BoardColor2;
        } else {
            if (y % 2 == 0) return BoardColor2;
            else return BoardColor1;
        }
    }

    public static void getDeskTheme(String key) {
        if (key != null) {
            switch (key) {
                case "Классика":
                    BoardColor1 = rgb(222, 184, 135);
                    BoardColor2 = rgb(139, 69, 19);
                    break;
                case "Черно-белое":
                    BoardColor1 = rgb(255, 255, 255);
                    BoardColor2 = rgb(128, 128, 128);
                    break;
                case "Зелено-бежевое":
                    BoardColor1 = rgb(236, 231, 199);
                    BoardColor2 = rgb(119, 149, 87);
                    break;
                case "Аквамарин":
                    BoardColor1 = rgb(135, 206, 250);
                    BoardColor2 = rgb(70, 130, 180);
                    break;
            }
        } else {
            BoardColor1 = rgb(222, 184, 135);
            BoardColor2 = rgb(139, 69, 19);
        }
    }
}
