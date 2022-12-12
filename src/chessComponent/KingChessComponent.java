package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示黑红帅
 */

public class KingChessComponent extends ChessComponent {
    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size, 7);
        if (this.getChessColor() == ChessColor.RED) {
            name = "帅";
        } else {
            name = "将";
        }
    }

    @Override
    public String toString() {
        return "King " + isReversal + " " + getChessColor();
    }

}
