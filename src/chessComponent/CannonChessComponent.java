package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示黑红炮
 */

public class CannonChessComponent extends ChessComponent {


    public CannonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size, 2);
        if (this.getChessColor() == ChessColor.RED) {
            name = "砲";
        } else {
            name = "炮";
        }
    }

    @Override
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination, SquareComponent initialChess) {
        int destX = destination.getX();
        int destY = destination.getY();
        int startX = initialChess.getChessboardPoint().getX();
        int startY = initialChess.getChessboardPoint().getY();
        if (chessboard[destX][destY].isReversal && chessboard[destX][destY].chessColor == initialChess.chessColor) {
            //如果已经翻过来且颜色相等，则不能打
            return false;
        }
        if (destX == startX) {
            int counter = 0;
            for (int i = startY; i < chessboard[startX].length; i++) {  //向右
                if (!(chessboard[startX][i] instanceof EmptySlotComponent)) {
                    counter++;
                }
                if (destX == chessboard[startX][i].getChessboardPoint().getX()
                        && destY == chessboard[startX][i].getChessboardPoint().getY()) {
                    return counter == 3;
                }
            }
            counter = 0;
            for (int i = startY; i >= 0; i--) { //向左
                if (!(chessboard[startX][i] instanceof EmptySlotComponent)) {
                    counter++;
                }
                if (destX == chessboard[startX][i].getChessboardPoint().getX()
                        && destY == chessboard[startX][i].getChessboardPoint().getY()) {
                    return counter == 3;
                }
            }
        } else if (destY == startY) {
            int counter = 0;
            for (int i = startX; i < chessboard.length; i++) { //向下
                if (!(chessboard[i][startY] instanceof EmptySlotComponent)) {
                    counter++;
                }
                if (destX == chessboard[i][startY].getChessboardPoint().getX()
                        && destY == chessboard[i][startY].getChessboardPoint().getY()) {
                    return counter == 3;
                }
            }
            counter = 0;
            for (int i = startX; i >= 0; i--) {  //向上
                if (!(chessboard[i][startY] instanceof EmptySlotComponent)) {
                    counter++;
                }
                if (destX == chessboard[i][startY].getChessboardPoint().getX()
                        && destY == chessboard[i][startY].getChessboardPoint().getY()) {
                    return counter == 3;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Cannon " + isReversal + " " + getChessColor();
    }

}
