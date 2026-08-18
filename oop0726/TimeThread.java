package com.shj.oop0726;

import java.util.ArrayList;

public class TimeThread implements Runnable{
    public long time=200;
    public MPlane mp;
    public ArrayList<Bullet> arrayList;
    public GameListener gameListener;
    public TimeThread(ArrayList<Bullet> arrayList,MPlane mp,GameListener gameListener){
        this.arrayList=arrayList;
        this.mp=mp;
        this.gameListener=gameListener;
    }
    public void run(){
        while(true){
            try{
                Thread.sleep(time);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            if(!gameListener.isPaused&&mp!=null) {
                Bullet bullet = new Bullet(mp.x+mp.size/2, mp.y);
                arrayList.add(bullet);
            }
        }
    }
}
