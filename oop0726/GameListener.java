package com.shj.oop0726;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;

public class GameListener extends MouseAdapter implements KeyListener {
    public Graphics g;
    public MPlane mp;
    public ArrayList<Bullet> arrayList=new ArrayList<>();
    public boolean isPaused=false;
    public GameThread gameThread;
    public TimeThread timeThread;
    public TopFileV2 topFileV2;
    public GameListener(Graphics g){
        this.g=g;
        this.mp=new MPlane(350,600);
        gameThread=new GameThread(g,mp,arrayList);
        gameThread.start();
        String topPath="D:\\IdeaProjects\\summer\\src\\com\\shj\\oop0726\\Top1.txt";
        topFileV2=new TopFileV2(topPath);
    }
    public void mouseMoved(MouseEvent e){
        int x=e.getX();
        int y=e.getY();
        if(x>180&&y>600){
            System.out.println("重新开始");
        }
    }
    public void keyPressed(KeyEvent e){
        int key=e.getKeyCode();
        System.out.println("key="+key);
        switch (key){
            case KeyEvent.VK_A:
                if(mp!=null)mp.speedX=-15;
                break;
            case KeyEvent.VK_D:
                if(mp!=null)mp.speedX=15;
                break;
            case KeyEvent.VK_J:
                   if(mp!=null){
                       Bullet bullet=new Bullet(mp.x+mp.size/2,mp.y);
                       arrayList.add(bullet);
                   }
                    break;
            case KeyEvent.VK_Q:
                if(mp!=null) {
                    timeThread = new TimeThread(arrayList, mp, this);
                    new Thread(timeThread).start();
                }
                break;
            case KeyEvent.VK_R:
                isPaused=!isPaused;
                System.out.println("游戏"+(isPaused?"暂停":"继续"));
                break;
            case KeyEvent.VK_SPACE:
                mp=new MPlane(350,600);
                gameThread.setMP(mp);
                gameThread.isStart(false);
                gameThread.resetScore();
                System.out.println("游戏开始");
                break;
            case KeyEvent.VK_ENTER:
                if(gameThread.gameOver){
                    endGameAndSaveScore();
                }else{
                    System.out.println("游戏进行中,无法保存!");
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println("key=" + key);
        switch (key) {
            case KeyEvent.VK_A:
                if(mp!=null)mp.speedX = 0;
                break;
            case KeyEvent.VK_D:
                if(mp!=null)mp.speedX = 0;
                break;
        }
    }
    public void keyTyped(KeyEvent e){

    }
    public void endGameAndSaveScore(){
        int score= gameThread.score;
        System.out.println("游戏结束,得分："+score);
        if(topFileV2.isTop10(score)){
            Scanner scanner=new Scanner(System.in);
            System.out.println("进入排行榜!请输入你的昵称:");
            String name=scanner.next();
            topFileV2.addRecord(name,score);
            System.out.println("保存成功!");
            topFileV2.displayRank();
        }else{
            System.out.println("未进入排行榜!");
        }
        gameThread.isStart(true);
        mp=null;
    }
}
