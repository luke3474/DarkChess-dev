package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;

import static view.Chessboard.scoreOfBlack;
import static view.Chessboard.scoreOfRed;


/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    private static JLabel statusLabel;
    public static JLabel redScore;
    public static JLabel blackScore;

    public static boolean isCheat = false;

    public static JLabel redCan = new JLabel(); public static JLabel redCha = new JLabel();; public static JLabel redMan = new JLabel();; public static JLabel redSol = new JLabel();;
    public static JLabel redKin = new JLabel();; public static JLabel redEle = new JLabel();; public static JLabel redHor = new JLabel();;

    public static JLabel blaCan = new JLabel();; public static JLabel blaCha = new JLabel();; public static JLabel blaMan = new JLabel();; public static JLabel blaSol = new JLabel();;
    public static JLabel blaKin = new JLabel();; public static JLabel blaEle = new JLabel();; public static JLabel blaHor = new JLabel();;

    public ChessGameFrame(int width, int height) { //直接调用该方法进行初始化游戏
        setTitle("Dark Chess for CS109"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addLabel();
        addRestartButton();
        addLoadButton();
        addScoreLabel();
        addCheatButton();
        addSaveButton();

        addLabelOfChess("将",WIDTH * 2 / 5 + 25,HEIGHT / 5 - 25);
        addLabelOfChess("士",WIDTH * 2 / 5 + 25,HEIGHT / 5 + 35);
        addLabelOfChess("像",WIDTH * 2 / 5 + 25,HEIGHT / 5 + 95);
        addLabelOfChess("車",WIDTH * 2 / 5 + 25,HEIGHT / 5 + 155);
        addLabelOfChess("马",WIDTH * 2 / 5 + 25,HEIGHT / 5 + 215);
        addLabelOfChess("炮",WIDTH * 2 / 5 + 25,HEIGHT / 5 + 275);
        addLabelOfChess("卒",WIDTH * 2 / 5 + 25,HEIGHT / 5 + 335);

        addLabelOfChess2(redKin.getText(),WIDTH * 2 / 5 + 135,HEIGHT / 5 - 25);
        addLabelOfChess2(redMan.getText(),WIDTH * 2 / 5 + 135,HEIGHT / 5 + 35);
        addLabelOfChess2(redEle.getText(),WIDTH * 2 / 5 + 135,HEIGHT / 5 + 95);
        addLabelOfChess2(redCha.getText(),WIDTH * 2 / 5 + 135,HEIGHT / 5 + 155);
        addLabelOfChess2(redHor.getText(),WIDTH * 2 / 5 + 135,HEIGHT / 5 + 215);
        addLabelOfChess2(redCan.getText(),WIDTH * 2 / 5 + 135,HEIGHT / 5 + 275);
        addLabelOfChess2(redSol.getText(),WIDTH * 2 / 5 + 135,HEIGHT / 5 + 335);

        addLabelOfChess2(blaKin.getText(),WIDTH * 2 / 5 + 240,HEIGHT / 5 - 25);
        addLabelOfChess2(blaMan.getText(),WIDTH * 2 / 5 + 240,HEIGHT / 5 + 35);
        addLabelOfChess2(blaEle.getText(),WIDTH * 2 / 5 + 240,HEIGHT / 5 + 95);
        addLabelOfChess2(blaCha.getText(),WIDTH * 2 / 5 + 240,HEIGHT / 5 + 155);
        addLabelOfChess2(blaHor.getText(),WIDTH * 2 / 5 + 240,HEIGHT / 5 + 215);
        addLabelOfChess2(blaCan.getText(),WIDTH * 2 / 5 + 240,HEIGHT / 5 + 275);
        addLabelOfChess2(blaSol.getText(),WIDTH * 2 / 5 + 240,HEIGHT / 5 + 335);
    }


    /**
     * 在游戏窗体中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
    }

    /**
     * 在游戏窗体中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("RED's TURN");
        statusLabel.setLocation(WIDTH * 4 / 5, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    public static JLabel getStatusLabel() {
        return statusLabel;
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart Game");
        button.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "Restart successfully!");
            GameController.restartGame();
            ChessGameFrame.getBlackScore().setText("BLACK:" + scoreOfBlack);
            ChessGameFrame.getRedScore().setText("RED:" + scoreOfRed);
            ChessGameFrame.num1(Chessboard.redKin).setText(Integer.toString(Chessboard.redKin));
            ChessGameFrame.num2(Chessboard.redCan).setText(Integer.toString(Chessboard.redCan));
            ChessGameFrame.num3(Chessboard.redCha).setText(Integer.toString(Chessboard.redCha));
            ChessGameFrame.num4(Chessboard.redHor).setText(Integer.toString(Chessboard.redHor));
            ChessGameFrame.num5(Chessboard.redMan).setText(Integer.toString(Chessboard.redMan));
            ChessGameFrame.num6(Chessboard.redSol).setText(Integer.toString(Chessboard.redSol));
            ChessGameFrame.num7(Chessboard.redEle).setText(Integer.toString(Chessboard.redEle));

            ChessGameFrame.num8(Chessboard.blaKin).setText(Integer.toString(Chessboard.blaKin));
            ChessGameFrame.num9(Chessboard.blaCan).setText(Integer.toString(Chessboard.blaCan));
            ChessGameFrame.num10(Chessboard.blaCha).setText(Integer.toString(Chessboard.blaCha));
            ChessGameFrame.num11(Chessboard.blaHor).setText(Integer.toString(Chessboard.blaHor));
            ChessGameFrame.num12(Chessboard.blaMan).setText(Integer.toString(Chessboard.blaMan));
            ChessGameFrame.num13(Chessboard.blaSol).setText(Integer.toString(Chessboard.blaSol));
            ChessGameFrame.num14(Chessboard.blaEle).setText(Integer.toString(Chessboard.blaEle));
        });
        button.setLocation(WIDTH * 4 / 5, HEIGHT / 10 + 120);
        button.setSize(130, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH * 4 / 5, HEIGHT / 10 + 270);
        button.setSize(130, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click load");
            Object[] options = {"Save1", "Save2", "Save3"};
            int choice = JOptionPane.showOptionDialog(null, "Please select a save file:",
                    "Save Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            String path = null;
            if (choice == 0) {
                path = "Saves/Save1.txt";
            } else if (choice == 1) {
                path = "Saves/Save2.txt";
            } else if (choice == 2) {
                path = "Saves/Save3.txt";
            }
            int temp = gameController.loadGameFromFile(path);
            repaint();
            if(temp == 0){
                JOptionPane.showMessageDialog(null, "Load Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else{
                JOptionPane.showMessageDialog(null, String.format("Error Code: %d",temp), "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //点击button触发的事件
    }

    public void addCheatButton() {
        JCheckBox button = new JCheckBox("Cheat Mode");
        button.setLocation(WIDTH * 4 / 5, HEIGHT / 10 + 360);
        button.setSize(130, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click cheat!");
            isCheat = !isCheat();
            if (isCheat) {
                gameController.cheatMode(Chessboard.getChessComponents());
            } else {
                gameController.returnCheatMode();
            }
        });
    }

    public void addScoreLabel() {
        redScore = new JLabel("RED:" + scoreOfRed);
        redScore.setLocation(WIDTH * 4 / 5, HEIGHT / 5 - 25);
        redScore.setSize(200, 60);
        redScore.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(redScore);

        blackScore = new JLabel("BLACK:" + scoreOfBlack);
        blackScore.setLocation(WIDTH * 4 / 5, HEIGHT / 5);
        blackScore.setSize(200, 60);
        blackScore.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(blackScore);
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.addActionListener((e) -> {
            System.out.println("Click save!");
            Object[] options = {"Save1", "Save2", "Save3"};
            int choice = JOptionPane.showOptionDialog(null, "Please select a save file:",
                    "Save Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);
            Chessboard.saveGame(choice);
        });
        button.setLocation(WIDTH * 4 / 5, HEIGHT / 10 + 200);
        button.setSize(130, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

    }
    public void addLabelOfChess(String name,int width,int height){
        JLabel label = new JLabel(name);
        label.setLocation(width,height);
        label.setSize(200,60);
        label.setFont(new Font("宋体",Font.BOLD,30));
        add(label);
    }
    public void addLabelOfChess2(String name,int width,int height){
        JLabel label = new JLabel(name);
        label.setLocation(width,height);
        label.setSize(200,60);
        label.setFont(new Font("宋体",Font.BOLD,30));
        add(label);
    }

    public static JLabel num1(int num) {
        return redKin;
    }
    public static JLabel num2(int num) {
        return redCan;
    }
    public static JLabel num3(int num) {
        return redCha;
    }
    public static JLabel num4(int num) {
        return redHor;
    }
    public static JLabel num5(int num) {
        return redMan;
    }
    public static JLabel num6(int num) {
        return redSol;
    }
    public static JLabel num7(int num) {
        return redEle;
    }

    public static JLabel num8(int num) {
        return blaKin;
    }
    public static JLabel num9(int num) {
        return blaCan;
    }
    public static JLabel num10(int num) {
        return blaCha;
    }
    public static JLabel num11(int num) {
        return blaHor;
    }
    public static JLabel num12(int num) {
        return blaMan;
    }
    public static JLabel num13(int num) {
        return blaSol;
    }
    public static JLabel num14(int num) {
        return blaEle;
    }
    public static JLabel getRedScore() {
        return redScore;
    }

    public static JLabel getBlackScore() {
        return blackScore;
    }

    public boolean isCheat() {
        return isCheat;
    }
}
