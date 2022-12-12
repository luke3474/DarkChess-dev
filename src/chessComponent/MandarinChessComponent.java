package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示黑红士
 */
public class MandarinChessComponent extends ChessComponent {
    public MandarinChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size, 6);
        if (this.getChessColor() == ChessColor.RED) {
            name = "仕";
        } else {
            name = "士";
        }
    }

    @Override
    public String toString() {
        return "Mandarin " + isReversal + " " + getChessColor();
    }

}
