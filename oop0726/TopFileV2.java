package com.shj.oop0726;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TopFileV2 {
   public String filePath;
   public List<String[]> rankList;
   public static final int MAX_SIZE=10;
   public TopFileV2(String filePath){
       this.filePath=filePath;
       this.rankList=new ArrayList<>();
       loadRank();
   }
   public void loadRank(){
       File file=new File(filePath);
       if(!file.exists()||file.length()==0){
           rankList.clear();
           return;
       }
       try(DataInputStream dis=new DataInputStream(new FileInputStream(file))){
           int count=dis.readInt();
           rankList.clear();
           for(int i=0;i<count;i++){
               int nameLen=dis.readInt();
               byte[] nameBytes=new byte[nameLen];
               dis.readFully(nameBytes);
               String name=new String(nameBytes,"UTF-8");
               int score=dis.readInt();
               rankList.add(new String[]{name,String.valueOf(score)});
           }
       }catch(EOFException e){
           rankList.clear();
       }catch(Exception e){
           System.out.println("加载失败:"+e.getMessage());
           rankList.clear();
       }
   }
   public void saveRank(){
       try(DataOutputStream dos=new DataOutputStream(new FileOutputStream(filePath))){
           dos.writeInt(rankList.size());
           for(String[] record:rankList){
               byte[] nameBytes=record[0].getBytes("UTF-8");
               dos.writeInt(nameBytes.length);
               dos.write(nameBytes);
               dos.writeInt(Integer.parseInt(record[1]));
           }
           dos.flush();
       }
       catch(Exception e){
           System.out.println("保存失败:"+e.getMessage());
       }
   }
   public void addRecord(String name,int score){
       rankList.add(new String[]{name,String.valueOf(score)});
       rankList.sort(((a, b) -> Integer.parseInt(b[1])-Integer.parseInt(a[1])));
       if(rankList.size()>MAX_SIZE){
           rankList=new ArrayList<>(rankList.subList(0,MAX_SIZE));
       }
       saveRank();
   }
   public List<String[]> getRankList(){
       return new ArrayList<>(rankList);
   }
   public List<String[]> getTopN(int n){
       List<String[]> result=new ArrayList<>();
       for(int i=0;i<Math.min(n,rankList.size());i++){
           result.add(rankList.get(i));
       }
       return result;
   }
   public void displayRank(){
       System.out.println();
       System.out.println("       排行榜       ");
       System.out.printf("%-6s %-16s %-10s%n","名次","玩家","得分");
       for(int i=0;i<rankList.size();i++){
           String[] record=rankList.get(i);
           System.out.printf("%-6d %-16s %-10s%n",i+1,record[0],record[1]);
       }
       if(rankList.isEmpty()){
           System.out.println("     暂无记录");
       }
       System.out.println();
   }
   public int getTopScore(){
       if(rankList.isEmpty())return 0;
       return Integer.parseInt(rankList.get(0)[1]);
   }
   public void clearRank(){
       rankList.clear();
       saveRank();
       System.out.println("排行榜已清空");
   }
   public boolean isTop10(int score){
       if(rankList.size()<MAX_SIZE)return true;
       return score>Integer.parseInt(rankList.get(rankList.size()-1)[1]);
   }
   public int getRank(String name){
       for(int i=0;i<rankList.size();i++){
           if(rankList.get(i)[0].equals(name)){
               return i+1;
           }
       }
       return -1;
   }
   public int size(){
       return rankList.size();
   }
   public static void main(String[] args){
       String path="D:\\IdeaProjects\\summer\\src\\com\\shj\\oop0726\\Top1.txt";
       TopFileV2 topFileV2=new TopFileV2(path);
       topFileV2.addRecord("张三", 1000);
       topFileV2.addRecord("李四", 800);
       topFileV2.addRecord("王五", 1500);
       topFileV2.addRecord("赵六", 500);
       topFileV2.addRecord("孙七", 1200);
       topFileV2.displayRank();
       System.out.println("前三名:");
       List<String[]> top3=topFileV2.getTopN(3);
       for(int i=0;i<top3.size();i++){
           System.out.println("  "+(i+1)+". "+top3.get(i)[0]+" "+top3.get(i)[1]);
       }
       System.out.println("分数1100是否能上榜:"+topFileV2.isTop10(1100));
       System.out.println("最高分:"+topFileV2.getTopScore());
       System.out.println("张三的排名:第"+topFileV2.getRank("张三")+"名");
   }
}
