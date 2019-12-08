package edu.neu.csye6200.ui;

import javax.swing.*;
import java.awt.*;

/*
 * This class will the JPanel that will contain the hexagonal grid
 */
public class HexagonalCanvas extends JPanel{

    public void paint(Graphics g)
    {
        setBackground(Color.BLACK);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2);
        for (int i=0;i<CAApp.BSIZE;i++) {
            for (int j=0;j<CAApp.BSIZE;j++) {
                CAAppHelper.drawHex(i,j,g2);
            }
        }
        for (int i=0;i<CAApp.BSIZE;i++) {
            for (int j=0;j<CAApp.BSIZE;j++) {
                CAAppHelper.fillHex(i,j,CAApp.cells[i][j].getState(),g2);
            }
        }
    }
}
