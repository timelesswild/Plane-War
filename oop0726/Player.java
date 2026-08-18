package com.shj.oop0726;

import javax.sound.sampled.*;
import java.io.File;

public class Player {
    public void play(String path){
        File file=new File(path);
        Clip clip=null;
        try{
            AudioInputStream inputStream=AudioSystem.getAudioInputStream(file);
            clip=AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
            FloatControl control=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            float max=control.getMaximum();
            float min=control.getMinimum();
            System.out.println("max="+max+"min="+min);
            float value=control.getValue();
            Thread.sleep(3000);
            control.setValue(value-5);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){

    }
}
