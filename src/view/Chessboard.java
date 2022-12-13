package view;


import chessComponent.*;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent {

    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;
    public static int scoreOfRed = 0;
    public static int scoreOfBlack = 0;

    public static int redCan = 0; public static int redCha = 0; public static int redMan = 0; public static int redSol = 0;
    public static int redKin = 0; public static int redEle = 0; public static int redHor = 0;

    public static int blaCan = 0; public static int blaCha = 0; public static int blaMan = 0; public static int blaSol = 0;
    public static int blaKin = 0; public static int blaEle = 0; public static int blaHor = 0;

    private static final SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];
    private static ChessColor currentColor = ChessColor.RED;

    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);
        initAllChessOnBoard();
    }

    public static SquareComponent[][] getChessComponents() {
        return squareComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * 将SquareComponent 放置在 ChessBoard上。里面包含移除原有的component及放置新的component
     * 此处可以作为重开游戏的重要方法
     *
     * @param squareComponent 棋子
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
    }

    /**
     * 交换chess1 chess2的位置
     *
     * @param chess1 初始棋子
     * @param chess2 被吃棋子/被占格子
     */
    public void swapChessComponents(SquareComponent chess1, SquareComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            if (chess2.getChessColor() == ChessColor.RED) {
                if (chess2 instanceof CannonChessComponent || chess2 instanceof ChariotChessComponent || chess2 instanceof ElephantChessComponent || chess2 instanceof HorseChessComponent) {
                    scoreOfBlack += 5;
                    if (chess2 instanceof CannonChessComponent){blaCan++;}
                    if (chess2 instanceof ChariotChessComponent){blaCha++;}
                    if (chess2 instanceof ElephantChessComponent){blaEle++;}
                    if (chess2 instanceof HorseChessComponent){blaHor++;}
                } else if (chess2 instanceof MandarinChessComponent) {
                    scoreOfBlack += 10;
                    blaMan++;
                } else if (chess2 instanceof SoldierChessComponent) {
                    scoreOfBlack += 2;
                    blaSol++;
                } else if (chess2 instanceof KingChessComponent) {
                    scoreOfBlack += 30;
                    blaKin++;
                }
            } //计分规则，黑方加分
            else if (chess2.getChessColor() == ChessColor.BLACK) {
                if (chess2 instanceof CannonChessComponent || chess2 instanceof ChariotChessComponent || chess2 instanceof ElephantChessComponent || chess2 instanceof HorseChessComponent) {
                    scoreOfRed += 5;
                    if (chess2 instanceof CannonChessComponent){redCan++;}
                    if (chess2 instanceof ChariotChessComponent){redCha++;}
                    if (chess2 instanceof ElephantChessComponent){redEle++;}
                    if (chess2 instanceof HorseChessComponent){redHor++;}
                } else if (chess2 instanceof MandarinChessComponent) {
                    scoreOfRed += 10;
                    redMan++;
                } else if (chess2 instanceof SoldierChessComponent) {
                    scoreOfRed += 2;
                    redSol++;
                } else if (chess2 instanceof KingChessComponent) {
                    scoreOfRed += 30;
                    redKin++;
                }
            } //计分规则，红方加分
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        //只重新绘制chess1 chess2，其他不变z
        chess1.repaint();
        chess2.repaint();
        ChessGameFrame.getBlackScore().setText("BLACK:" + scoreOfBlack);
        ChessGameFrame.getRedScore().setText("RED:" + scoreOfRed);

        ChessGameFrame.num1(redKin).setText(Integer.toString(redKin));
        ChessGameFrame.num2(redCan).setText(Integer.toString(redCan));
        ChessGameFrame.num3(redCha).setText(Integer.toString(redCha));
        ChessGameFrame.num4(redHor).setText(Integer.toString(redHor));
        ChessGameFrame.num5(redMan).setText(Integer.toString(redMan));
        ChessGameFrame.num6(redSol).setText(Integer.toString(redSol));
        ChessGameFrame.num7(redEle).setText(Integer.toString(redEle));

        ChessGameFrame.num8(blaKin).setText(Integer.toString(blaKin));
        ChessGameFrame.num9(blaCan).setText(Integer.toString(blaCan));
        ChessGameFrame.num10(blaCha).setText(Integer.toString(blaCha));
        ChessGameFrame.num11(blaHor).setText(Integer.toString(blaHor));
        ChessGameFrame.num12(blaMan).setText(Integer.toString(blaMan));
        ChessGameFrame.num13(blaSol).setText(Integer.toString(blaSol));
        ChessGameFrame.num14(blaEle).setText(Integer.toString(blaEle));
        //判断输赢
        if (scoreOfBlack >= 60) {
            JOptionPane.showMessageDialog(this, "Black wins!");
            chess1.repaint();
        }
        if (scoreOfRed >= 60) {
            JOptionPane.showMessageDialog(this, "Red wins!");
            chess1.repaint();
        }
    }

    public void initAllChessOnBoard() {
        int[] blackChess = new int[16];
        int[] redChess = new int[16];
        for (int i = 1; i < 17; i++) {
            blackChess[i - 1] = i;
            redChess[i - 1] = i;
        }

        Random random = new Random();
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                SquareComponent squareComponent;
                while (true) {

                    boolean temp;
                    ChessColor color;
                    if (random.nextInt(2) == 0) { //随机颜色，0为红，1为黑
                        color = ChessColor.RED;
                        temp = true;
                    } else {
                        color = ChessColor.BLACK;
                        temp = false;
                    }

                    int a = random.nextInt(16);
                    //生成0-15之间的数，对应数组索引
                    // 兵5个、炮2个、车2个、马2个、相2个、仕2个、帅1个共16个棋子
                    if (a < 5 && temp && redChess[a] != 0) {  //红色兵
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        redChess[a] = 0;
                        break;
                    } else if (a < 5 && !temp && blackChess[a] != 0) {  //黑色兵
                        squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        blackChess[a] = 0;
                        break;
                    } else if (a >= 5 && a < 7 && temp && redChess[a] != 0) { //红色炮
                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        redChess[a] = 0;
                        break;
                    } else if (a >= 5 && a < 7 && !temp && blackChess[a] != 0) { //黑色炮
                        squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        blackChess[a] = 0;
                        break;
                    } else if (a >= 7 && a < 9 && temp && redChess[a] != 0) { //红色车
                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        redChess[a] = 0;
                        break;
                    } else if (a >= 7 && a < 9 && !temp && blackChess[a] != 0) { //黑色车
                        squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        blackChess[a] = 0;
                        break;
                    } else if (a >= 9 && a < 11 && temp && redChess[a] != 0) { //红色马
                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        redChess[a] = 0;
                        break;
                    } else if (a >= 9 && a < 11 && !temp && blackChess[a] != 0) { //黑色马
                        squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        blackChess[a] = 0;
                        break;
                    } else if (a >= 11 && a < 13 && temp && redChess[a] != 0) { //红色象
                        squareComponent = new ElephantChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        redChess[a] = 0;
                        break;
                    } else if (a >= 11 && a < 13 && !temp && blackChess[a] != 0) { //黑色象
                        squareComponent = new ElephantChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        blackChess[a] = 0;
                        break;
                    } else if (a >= 13 && a < 15 && temp && redChess[a] != 0) { //红色士
                        squareComponent = new MandarinChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        redChess[a] = 0;
                        break;
                    } else if (a >= 13 && a < 15 && !temp && blackChess[a] != 0) { //黑色士
                        squareComponent = new MandarinChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        blackChess[a] = 0;
                        break;
                    } else if (a == 15 && temp && redChess[a] != 0) { //红色将
                        squareComponent = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        redChess[a] = 0;
                        break;
                    } else if (a == 15 && !temp && blackChess[a] != 0) { //黑色将
                        squareComponent = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                        blackChess[a] = 0;
                        break;
                    }
                }
                squareComponent.setVisible(true);
                putChessOnBoard(squareComponent);
                scoreOfBlack = 0;
                scoreOfRed = 0;
                redKin=0;redEle=0;redCan=0;redCha=0;redHor=0;redMan=0;redSol=0;
                blaKin=0;blaEle=0;blaCan=0;blaCha=0;blaHor=0;blaMan=0;blaSol=0;
            }
        }
    }

    /**
     * 绘制棋盘格子
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     *
     * @param row 棋盘上的行
     * @param col 棋盘上的列
     * @return
     */
    public Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }

    /**
     * 通过GameController调用该方法
     *
     * @param chessData
     */
    public int loadGame(List<String> chessData) {
        if (validLoad(chessData) == 0) {
            for (int i = 0; i < 32; ) {
                for (int j = 0; j < 4; j++) {
                    String[] temp = chessData.get(i).split(" ");
                    SquareComponent squareComponent;
                    ChessColor color = ChessColor.NONE;
                    if (Objects.equals(temp[0], "Empty")) {
                        squareComponent = new EmptySlotComponent(new ChessboardPoint(i / 4, j), calculatePoint(i / 4, j), clickController, CHESS_SIZE);
                        putChessOnBoard(squareComponent);
                    } else {
                        if (Objects.equals(temp[2], "RED")) {
                            color = ChessColor.RED;
                        } else if (Objects.equals(temp[2], "BLACK")) {
                            color = ChessColor.BLACK;
                        }
                        if (Objects.equals(temp[0], "Cannon")) {
                            squareComponent = new CannonChessComponent(new ChessboardPoint(i / 4, j), calculatePoint(i / 4, j), color, clickController, CHESS_SIZE);
                        } else if (Objects.equals(temp[0], "Chariot")) {
                            squareComponent = new ChariotChessComponent(new ChessboardPoint(i / 4, j), calculatePoint(i / 4, j), color, clickController, CHESS_SIZE);
                        } else if (Objects.equals(temp[0], "Elephant")) {
                            squareComponent = new ElephantChessComponent(new ChessboardPoint(i / 4, j), calculatePoint(i / 4, j), color, clickController, CHESS_SIZE);
                        } else if (Objects.equals(temp[0], "Horse")) {
                            squareComponent = new HorseChessComponent(new ChessboardPoint(i / 4, j), calculatePoint(i / 4, j), color, clickController, CHESS_SIZE);
                        } else if (Objects.equals(temp[0], "King")) {
                            squareComponent = new KingChessComponent(new ChessboardPoint(i / 4, j), calculatePoint(i / 4, j), color, clickController, CHESS_SIZE);
                        } else if (Objects.equals(temp[0], "Mandarin")) {
                            squareComponent = new MandarinChessComponent(new ChessboardPoint(i / 4, j), calculatePoint(i / 4, j), color, clickController, CHESS_SIZE);
                        } else if (Objects.equals(temp[0], "Soldier")) {
                            squareComponent = new SoldierChessComponent(new ChessboardPoint(i / 4, j), calculatePoint(i / 4, j), color, clickController, CHESS_SIZE);
                        } else {
                            break;
                        }
                        if (Objects.equals(temp[1], "true")) {
                            squareComponent.setReversal(true);
                        } else if (Objects.equals(temp[1], "False")) {
                            squareComponent.setReversal(false);
                        }
                    }
                    squareComponent.setVisible(true);
                    putChessOnBoard(squareComponent);
                    i++;
                }
            }
            String[] temp = chessData.get(32).split(" ");
            scoreOfRed = Integer.parseInt(temp[1]);
            scoreOfBlack = Integer.parseInt(temp[2]);
            ChessGameFrame.getBlackScore().setText("BLACK:" + scoreOfBlack);
            ChessGameFrame.getRedScore().setText("RED:" + scoreOfRed);
            if (Objects.equals(temp[0], "RED")) {
                currentColor = ChessColor.RED;
            } else if (Objects.equals(temp[0], "BLACK")) {
                currentColor = ChessColor.BLACK;
            }
            ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", currentColor.getName()));
            return 0;
        }
        return validLoad(chessData);
    }

    public static int validLoad(List<String> chessData) {
        if (chessData.size() < 33) {
            return 102;
        }
        for (int i = 0; i < 32; i++) {
            String[] temp = chessData.get(i).split(" ");
            if (!Objects.equals(temp[0], "Cannon") && !Objects.equals(temp[0], "Chariot") && !Objects.equals(temp[0], "Elephant") && !Objects.equals(temp[0], "Empty") && !Objects.equals(temp[0], "Horse") && !Objects.equals(temp[0], "King") && !Objects.equals(temp[0], "Mandarin") && !Objects.equals(temp[0], "Soldier")) {
                return 103;
            }
            if (temp.length > 1) {
                if ((!Objects.equals(temp[2], "RED") && !Objects.equals(temp[2], "BLACK")) || (!Objects.equals(temp[1], "true") && !Objects.equals(temp[1], "false"))) {
                    return 103;
                }
            }
            if (!Objects.equals(chessData.get(32).split(" ")[0], "RED") && !Objects.equals(chessData.get(32).split(" ")[0], "BLACK")) {
                return 104;
            }
        }
        return 0;
    }

    public static void saveGame(int fileName) {
        List<String> chessBoardList = new ArrayList<>();
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[0].length; j++) {
                chessBoardList.add(squareComponents[i][j].toString());
            }
        }
        chessBoardList.add(currentColor + " " + scoreOfRed + " " + scoreOfBlack);
        if (fileName == 0) {
            // 创建一个File对象，表示要写入的文件
            File file = new File("Saves/Save1.txt");
            writeLoad(file, chessBoardList);
        } else if (fileName == 1) {
            File file = new File("Saves/Save2.txt");
            writeLoad(file, chessBoardList);
        } else if (fileName == 2) {
            File file = new File("Saves/Save3.txt");
            writeLoad(file, chessBoardList);
        }
    }

    public static void writeLoad(File file, List<String> chessBoardList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // 遍历List<String>中的每一个字符串
            for (String line : chessBoardList) {
                // 向文件中写入当前字符串
                writer.write(line);
                // 写入一个换行符，表示一行结束
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
