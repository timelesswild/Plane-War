package com.shj.oop0726;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class GameThread extends Thread {
    public Graphics g;
    public MPlane mp;
    public ArrayList<Bullet> arrayList;
    public ArrayList<MPlane> enemyList;
    public ArrayList<Boom> boomList;
    public Image back;
    public ImageIcon imageIcon;
    public Graphics buffG;
    public boolean start = true;
    public int imageX = 0, imageY = 0;
    public ImageIcon startImage;
    public int score = 0;
    public int enemySpawnCounter=0;
    public boolean gameOver=false;

    public GameThread(Graphics g, MPlane mp, ArrayList<Bullet> arrayList) {
        this.g = g;
        this.mp = mp;
        this.arrayList = arrayList;
        this.enemyList = new ArrayList<>();
        this.boomList = new ArrayList<>();
        imageIcon = new ImageIcon("image\\背景1.jpg");
        back = imageIcon.getImage();
        startImage = new ImageIcon("image\\开始.png");
    }

    public void setMP(MPlane mp) {
        this.mp = mp;
    }

    public void isStart(boolean start) {
        this.start = start;
    }

    public void resetScore() {
        this.score = 0;
        this.gameOver = false;
        this.enemySpawnCounter = 0;
        this.enemyList.clear();
        this.boomList.clear();
        this.arrayList.clear();
    }

    public void run() {
        BufferedImage bufferedImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        buffG = bufferedImage.getGraphics();
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            if (start) {
                gameStart();
            } else {
                gameRun();
            }
            g.drawImage(bufferedImage, 0, 0, null);
        }
    }

    public void gameStart() {
        buffG.setColor(Color.BLACK);
        buffG.fillRect(0, 0, 800, 800);
        if (startImage != null && startImage.getImage() != null) {
            buffG.drawImage(startImage.getImage(), 200, 250, 400, 200, null);
        }
        buffG.setColor(Color.WHITE);
        buffG.setFont(new Font("宋体", Font.BOLD, 36));
        buffG.drawString("飞机大战", 280, 180);
        buffG.setFont(new Font("宋体", Font.PLAIN, 18));
        buffG.drawString("空格键开始|A/D移动|J射击|Q自动射击|R暂停|ENTER保存", 90, 500);
    }

    public void gameRun() {
        if (gameOver) {
            gameOverScreen();
            return;
        }
        if (back != null) {
            buffG.drawImage(back, imageX, imageY, null);
            buffG.drawImage(back, imageX, imageY - imageIcon.getIconHeight(), null);
            if (imageY - imageIcon.getIconHeight() >= 0) {
                imageY = 0;

            }
            imageY += 3;
        } else {
            buffG.setColor(Color.DARK_GRAY);
            buffG.fillRect(0, 0, 800, 800);
        }
        enemySpawnCounter++;
        if (enemySpawnCounter >= 30) {
            enemySpawnCounter = 0;
            int ex = (int) (Math.random() * 700);
            MPlane enemy = new MPlane(ex, -100);
            enemy.speedY = 3 + (int) (Math.random() * 3);
            enemyList.add(enemy);
        }
        for (int i = enemyList.size() - 1; i >= 0; i--) {
            MPlane enemy = enemyList.get(i);
            enemy.y += enemy.speedY;
            enemy.drawPlane(buffG);
            if (enemy.y > 820) {
                enemyList.remove(i);
            }
        }
        if (mp != null) {
            mp.drawPlane(buffG);
        }
        for (int i = arrayList.size() - 1; i >= 0; i--) {
            Bullet bullet = arrayList.get(i);
            bullet.drawBullet(buffG);
            if (bullet.y < -20 || bullet.y > 820) {
                arrayList.remove(i);
                continue;
            }
            for (int j = enemyList.size() - 1; j >= 0; j--) {
                MPlane enemy = enemyList.get(j);
                if (hit(bullet, enemy)) {
                    boomList.add(new Boom(enemy.x, enemy.y));
                    enemyList.remove(j);
                    arrayList.remove(i);
                    score += 100;
                    break;
                }
            }
        }
        if (mp != null) {
            for (int j = enemyList.size() - 1; j >= 0; j--) {
                MPlane enemy = enemyList.get(j);
                if (hitPlane(enemy, mp)) {
                    boomList.add(new Boom(mp.x, mp.y));
                    enemyList.remove(j);
                    gameOver = true;
                    break;
                }
            }
        }
        for (int i = boomList.size() - 1; i >= 0; i--) {
            Boom boom = boomList.get(i);
            boom.drawBoom(buffG);
            if (boom.finished) {
                boomList.remove(i);
            }
        }
        drawScore();
    }
    public boolean hit(Bullet b,MPlane p){
        int bx=b.x;
        int by=b.y;
        int bs=b.size;
        int px=p.x;
        int py=p.y;
        int ps=p.size;
        return bx+bs>=px&&bx<=px+ps&&by+bs>=py&&by<=py+ps;
    }
    public boolean hitPlane(MPlane a,MPlane b){
        return a.x+a.size>=b.x&&a.x<=b.x+b.size&&a.y+a.size>=b.y&&a.y<=b.y+b.size;
    }
    public void gameOverScreen(){
        buffG.setColor(Color.BLACK);
        buffG.fillRect(0,0,800,800);
        for(int i=boomList.size()-1;i>=0;i--){
            Boom boom=boomList.get(i);
            boom.drawBoom(buffG);
            if(boom.finished){
                boomList.remove(i);
            }
        }
        buffG.setColor(Color.RED);
        buffG.setFont(new Font("宋体", Font.BOLD, 60));
        buffG.drawString("游戏结束",200,300);
        buffG.setColor(Color.WHITE);
        buffG.setFont(new Font("宋体",Font.BOLD,36));
        buffG.drawString("本局得分:"+score,280,400);
        buffG.setFont(new Font("宋体",Font.PLAIN,20));
        buffG.drawString("按ENTER保存成绩|按空格键重新开始",180,500);
    }
    public void drawScore(){
        buffG.setColor(Color.WHITE);
        buffG.setFont(new Font("宋体",Font.BOLD,30));
        buffG.drawString("SCORE:"+score,20,40);
    }
}