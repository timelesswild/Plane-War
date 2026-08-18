package com.shj.oop0726;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    public void intUI() {
        JFrame jf = new JFrame("游戏界面");
        jf.setSize(800, 800);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);
        JPanel gamePanel=new JPanel();
        gamePanel.setBackground(Color.WHITE);
        jf.add(gamePanel,BorderLayout.CENTER);
        jf.setVisible(true);
        Graphics g=gamePanel.getGraphics();
        GameListener listener=new GameListener(g);
        gamePanel.addKeyListener(listener);
        gamePanel.addMouseMotionListener(listener);
        gamePanel.requestFocus();
    }
    public static void main(String[] args){
        GameUI ui=new GameUI();
        ui.intUI();
    }
}
