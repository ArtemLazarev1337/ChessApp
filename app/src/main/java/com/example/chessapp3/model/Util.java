package com.example.chessapp3.model;

public class Util {

    public static String translateNotation(String[] moves, boolean whiteMove) {
        StringBuilder result = new StringBuilder();

        int i = 2;
        for (String move: moves) {
            if (!whiteMove && i == 2) {
                result.append("1... ");
                i++;
            }
            if (i % 2 == 0) {
                result.append(i / 2).append(". ");
            }
            String notation = move.substring(2, 4);
            char figure = move.charAt(1);
            switch (figure) {
                case 'k':
                    result.append("Кр").append(notation);
                    break;
                case 'q':
                    result.append("Ф").append(notation);
                    break;
                case 'r':
                    result.append("Л").append(notation);
                    break;
                case 'b':
                    result.append("С").append(notation);
                    break;
                case 'n':
                    result.append("К").append(notation);
                    break;
                case 'p':
                    result.append(notation);
                    break;
            }
            result.append(" ");
            i++;
        }

        return result.toString().trim();
    }

    public static String translateMoves(String moves, boolean isWhiteMove) {
        StringBuilder result = new StringBuilder();

        int i = 0;
        if (!isWhiteMove) i++;

        for (String move: moves.split(" ")) {
            char color = i % 2 == 0 ? 'w' : 'b';

            if (move.length() == 4) {
                result.append(color).append('k').append(move.substring(2, 4));
                i++;
                continue;
            } else if (move.length() == 2) {
                result.append(color).append('p').append(move.substring(0, 2));
                i++;
                continue;
            }

            char figure = Character.toLowerCase(move.charAt(0));
            String notation = move.substring(1, 3);

            switch (figure) {
                case 'ф':
                    result.append(color).append('q').append(notation);
                    break;
                case 'л':
                    result.append(color).append('r').append(notation);
                    break;
                case 'с':
                    result.append(color).append('b').append(notation);
                    break;
                case 'к':
                    result.append(color).append('n').append(notation);
                    break;
            }
            result.append(" ");
            i++;
        }

        return result.toString().trim();
    }

    public static String firstMove(String moves, boolean isWhiteMove) {
        return translateMoves(moves, isWhiteMove).split(" ")[0];
    }
}
