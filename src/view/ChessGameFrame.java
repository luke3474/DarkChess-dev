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
        statusLabel.setLocation(WIDTH * 3 / 5, HEIGHT / 10);
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
        });
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 120);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 270);
        button.setSize(180, 60);
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
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 360);
        button.setSize(180, 60);
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
        redScore.setLocation(WIDTH * 3 / 5, HEIGHT / 5 - 25);
        redScore.setSize(200, 60);
        redScore.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(redScore);

        blackScore = new JLabel("BLACK:" + scoreOfBlack);
        blackScore.setLocation(WIDTH * 3 / 5, HEIGHT / 5);
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
        button.setLocation(WIDTH * 3 / 5, HEIGHT / 10 + 210);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

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
