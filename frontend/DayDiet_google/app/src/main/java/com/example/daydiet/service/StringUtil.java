package com.example.daydiet.service;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
    public  List<String> getString(String str){
        String[] strArray = str.split("步骤"); // 使用空格作为间隔符;
        for(int i=1;i<strArray.length;i++){
            for(int j=0;;j++){
                if(strArray[i].charAt(j)==':'){
                    strArray[i] = strArray[i].substring(j+1);
                    break;
                }
            }
        }
        List<String> res = new ArrayList<>();
        for(int i=1;i<strArray.length;i++){
            res.add(i-1,strArray[i]);
        }
        return res;
    }
    public List<String> split(String str){
        String[] strArray = str.split("、"); // 使用空格作为间隔符;
        List<String> res = new ArrayList<>();
        if(strArray.length == 1 && strArray[0].equals("")){
            return res;
        }
        for(int i=0;i<strArray.length;i++){
            res.add(i,strArray[i]);
        }
        return res;
    }

    public String append(List<String> str){
        if(str.size() < 1) return "";
        String res = str.get(0);
        for(int i=1;i<str.size();i++){
            res = res + "、" + str.get(i);
        }
        return res;
    }

    public  String getStep(String str,int num){
        String[] strArray = str.split("步骤"); // 使用空格作为间隔符;
        for(int i=1;i<strArray.length;i++){
            for(int j=0;;j++){
                if(strArray[i].charAt(j)==':'){
                    strArray[i] = strArray[i].substring(j+1);
                    break;
                }
            }
        }
        List<String> res = new ArrayList<>();
        for(int i=1;i<strArray.length;i++){
            res.add(i-1,strArray[i]);
        }
        return res.get(num);
    }


    public  int getNum(String str){
        String[] strArray = str.split("步骤"); // 使用空格作为间隔符;
        for(int i=1;i<strArray.length;i++){
            for(int j=0;;j++){
                if(strArray[i].charAt(j)==':'){
                    strArray[i] = strArray[i].substring(j+1);
                    break;
                }
            }
        }
        List<String> res = new ArrayList<>();
        for(int i=1;i<strArray.length;i++){
            res.add(i-1,strArray[i]);
        }
        return res.size();
    }

    public List<String> split2(String str){
        String[] strArray = str.split(";"); // 使用空格作为间隔符;
        List<String> res = new ArrayList<>();
        for(int i=0;i<strArray.length;i++){
            res.add(i,strArray[i]);
        }
        return res;
    }

}
