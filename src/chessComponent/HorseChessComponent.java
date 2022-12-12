package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示黑红马
 */

public class HorseChessComponent extends ChessComponent {
    public HorseChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size, 3);
        name = "馬";
    }

    @Override
    public String toString() {
        return "Horse " + isReversal + " " + getChessColor();
    }
}
