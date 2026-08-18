package com.shj.oop0726;

import javax.swing.*;
import java.awt.*;

public class MPlane {
    public int x,y,w,h;
    public int speedX,speedY;
    public Image image;
    public int size;
    public MPlane(int x,int y){
        this.x=x;
        this.y=y;
        w=100;
        h=100;
        size=100;
        image=new ImageIcon("image\\PaperPlane.png").getImage();
    }
    public void drawPlane(Graphics g){
        g.drawImage(image,x,y,w,h,null);
        move();
    }
    public void move(){
        x+=speedX;
        y+=speedY;
    }
}
