package com.traffic.test;  
  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
public class TestPraserJsonp {  
    public static void main(String[] args) {  
        String jsonStr = "/**/jsonp3&&jsonp3({q:\"招\",p:false,s:[\"招商银行\",\"招商银行信用卡\",\"招商银行信用卡电话\",\"招聘网\",\"招聘\",\"招聘网站大全58同城\",\"招财宝\",\"招财宝安全吗\",\"招行信用卡中心电话\",\"招商轮船\"],pre:[\"0\",\"1\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"]});";  
        
        
    }  
    
    public static ArrayList<String> praseJson(String jsonStr){
    	jsonStr = jsonStr.split("s:\\[")[1];
        String s[] = jsonStr.split("],")[0].replace("\"", "").split(",");
        String pre[] = jsonStr.split("pre:\\[")[1].replace("\"", "").replace("]});", "").split(",");
        ArrayList<String> preWord = new ArrayList<>();
        for (int i = 0; i < pre.length; i++) {
			if(pre[i].equals("1"))
				preWord.add(s[i]);
		}
        return preWord;
    }
}  