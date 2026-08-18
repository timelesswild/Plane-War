package com.shj.oop0726;


import java.awt.*;

public class Bullet {
    public int x,y,size,speedX,speedY;
    public Bullet(int x,int y){
        this.x=x;
        this.y=y;
        this.size=10;
        this.speedX=0;
        this.speedY=-10;
    }
    public void drawBullet(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillOval(x,y,size,size);
        g.setColor(Color.RED);
        g.drawOval(x,y,size,size);
        move();
    }
    public void move(){
        x+=speedX;
        y+=speedY;
    }
}
