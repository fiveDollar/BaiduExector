package com.traffic.test;  
  
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
public class TestPraserJsonp {  
    public static void main(String[] args) {  
        String jsonStr = "/**/jsonp3&&jsonp3({q:\"��\",p:false,s:[\"��������\",\"�����������ÿ�\",\"�����������ÿ��绰\",\"��Ƹ��\",\"��Ƹ\",\"��Ƹ��վ��ȫ58ͬ��\",\"�вƱ�\",\"�вƱ���ȫ��\",\"�������ÿ����ĵ绰\",\"�����ִ�\"],pre:[\"0\",\"1\",\"1\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\",\"0\"]});";  
        
        
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