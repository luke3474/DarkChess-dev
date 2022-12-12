package controller;

import chessComponent.ChessComponent;
import chessComponent.SquareComponent;
import model.ChessColor;
import view.ChessGameFrame;
import view.Chessboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类主要完成由窗体上组件触发的动作。
 * 例如点击button等
 * ChessGameFrame中组件调用本类的对象，在本类中的方法里完成逻辑运算，将运算的结果传递至chessboard中绘制
 */


public class GameController {
    private static Chessboard chessboard;
    public ArrayList<SquareComponent> chessNotReversal;

    public GameController(Chessboard chessboard) {
        GameController.chessboard = chessboard;
    }

    public int loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            return chessboard.loadGame(chessData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 201;
    }

    public static void restartGame(){
        chessboard.setCurrentColor(ChessColor.RED);
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
        chessboard.initAllChessOnBoard();
        chessboard.repaint();
    }
    
    public void cheatMode(SquareComponent[][] chessboard){
    chessNotReversal = new ArrayList<>();
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[0].length; j++) {
                if(chessboard[i][j] instanceof ChessComponent){ //如果是棋子，才有isReversal
                    if(!chessboard[i][j].isReversal()){
                        chessNotReversal.add(chessboard[i][j]);
                        chessboard[i][j].setReversal(true);
                        chessboard[i][j].repaint();
                    }
                }
            }
        }
    }

    public void returnCheatMode(){
        for (int i = 0; i < chessNotReversal.size(); i++) {
            chessNotReversal.get(i).setReversal(false);
            chessNotReversal.get(i).repaint();
        }
    }
}
