package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;
import view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * 这个类是一个抽象类，主要表示8*4棋盘上每个格子的棋子情况。
 * 有两个子类：
 * 1. EmptySlotComponent: 空棋子
 * 2. ChessComponent: 表示非空棋子
 */
public abstract class SquareComponent extends JComponent {

    private static final Color squareColor = new Color(250, 220, 190);
    protected static int spacingLength;
    protected static final Font CHESS_FONT = new Font("华文中宋", Font.BOLD, 36);

    /**
     * chessboardPoint: 表示8*4棋盘中，当前棋子在棋格对应的位置，如(0, 0), (1, 0)等等
     * chessColor: 表示这个棋子的颜色，有红色，黑色，无色三种
     * isReversal: 表示是否翻转
     * selected: 表示这个棋子是否被选中
     */
    private ChessboardPoint chessboardPoint;
    protected final ChessColor chessColor;
    protected boolean isReversal;
    private boolean selected;

    /**
     * handle click event
     */
    private final ClickController clickController;

    protected SquareComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(size, size);
        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
        this.clickController = clickController;
        this.isReversal = false;
    }

    public boolean isReversal() {
        return isReversal;
    }

    public void setReversal(boolean reversal) {
        isReversal = reversal;
    }

    public static void setSpacingLength(int spacingLength) {
        SquareComponent.spacingLength = spacingLength;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @param another 主要用于和另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(EmptySlotComponent)做交换
     */
    public void swapLocation(SquareComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.printf("Click [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            clickController.onClick(this);
        }
    }

    /**
     * @param chessboard  棋盘
     * @param destination 目标位置，如(0, 0), (0, 1)等等
     * @return this棋子对象的移动规则和当前位置(chessboardPoint)能否到达目标位置
     * <br>
     * 这个方法主要是检查移动的合法性，如果合法就返回true，反之是false。
     */

    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination, SquareComponent initialChess) {
        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];
        int startX = initialChess.getChessboardPoint().getX();
        int startY = initialChess.getChessboardPoint().getY();
        if (startX - destination.getX() > 1 || startX - destination.getX() < -1) return false;
        if (startY - destination.getY() > 1 || startY - destination.getY() < -1) return false;
        if ((startX - destination.getX() == 1 || startX - destination.getX() == -1)
                && (startY - destination.getY() == 1 || startY - destination.getY() == -1)) return false;
        //应当位于上下左右的格子，否则直接返回false
        if (destinationChess.isReversal && destinationChess instanceof ChessComponent) {
            if (((ChessComponent) destinationChess).level == 1 && ((ChessComponent) initialChess).level == 7) {
                return false;
                // 将/帅不可以吃兵
            }
            if (((ChessComponent) destinationChess).level <= ((ChessComponent) initialChess).level) {
                return true;
                // 只能吃等级比自己小的或相等的。将7>士6>象5>车4>马3>炮2>兵1
            }
            if (((ChessComponent) destinationChess).level == 7 && ((ChessComponent) initialChess).level == 1) {
                return true;
                // 兵可以吃将/帅
            }
        }
        //如果level符合，则返回true
        if (destinationChess instanceof EmptySlotComponent) return true;
        //如果位置上没有棋子，返回true
        return false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        System.out.printf("repaint chess [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
        g.setColor(squareColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
    }

    @Override
    public String toString(){
        return "";
    }
}
