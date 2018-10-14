package com.face.learn.bias.classification;

import com.face.learn.Bayes;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;

public class TestBayes {
    public static void main(String[] args)throws IOException{
        ArrayList<ArrayList<String>> predictDatas = readData("C:\\Users\\PabloWayne\\Desktop\\predict.txt");
        ArrayList<ArrayList<String>> testData = readData("C:\\Users\\PabloWayne\\Desktop\\test.txt");
        for(ArrayList<String> test : testData){
            System.out.println(Bayes.predictClass(predictDatas,test));
        }
    }

    private static ArrayList<String> readTest(String path)throws IOException{
        ArrayList<String> result = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tmpLine = reader.readLine();
        for(String tmp : tmpLine.split(" ")){
            if( tmp != " "){
                result.add(tmp);
            }
        }
        return result;
    }

    private static ArrayList<ArrayList<String>> readData(String path)throws IOException{
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tmpLine = "";
        while ((tmpLine = reader.readLine()) != null){
            ArrayList<String> tmpList = new ArrayList<>();
            for(String tmp : tmpLine.split(" ")){
                if(StringUtils.isNotBlank(tmp)){
                    tmpList.add(tmp);
                }
            }
            if(tmpList.size() > 0){
                results.add(tmpList);
            }
        }
        reader.close();
        return results;
    }
}
