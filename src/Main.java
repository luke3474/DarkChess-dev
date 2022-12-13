import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(960, 720);
            mainFrame.setVisible(true);
        });

    }
}
