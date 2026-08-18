package com.shj.oop0726;

import javax.swing.*;
import java.awt.*;

public class Boom {
    public ImageIcon[] imageArr;
    public int index;
    public int x,y;
    public int width,height;
    public boolean finished;
    public Boom(int x,int y){
        this.x=x;
        this.y=y;
        this.index=0;
        this.width=80;
        this.height=80;
        this.finished=false;
        imageArr=new ImageIcon[3];
        for(int i=1;i<=3;i++){
            imageArr[i-1]=new ImageIcon("image\\爆炸"+i+".png");
        }
    }
    public void drawBoom(Graphics g){
        if(index>=imageArr.length){
            finished=true;
            return;
        }
        g.drawImage(imageArr[index].getImage(),x,y,width,height,null);
        index++;
    }
}
