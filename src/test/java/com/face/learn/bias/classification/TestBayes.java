package com.face.learn.bias.classification;

import java.io.*;
import java.util.ArrayList;

public class TestBayes {
    public static void main(String[] args)throws IOException{
        ArrayList<ArrayList<String>> predictDatas = readData("C:\\Users\\PabloWayne\\Desktop\\text.txt");
        ArrayList<String> testData = readTest("C:\\Users\\PabloWayne\\Desktop\\test.txt");
        System.out.println(Bayes.predictClass(predictDatas,testData));
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
                if( tmp != " "){
                    tmpList.add(tmp);
                }
            }
            results.add(tmpList);
        }
        reader.close();
        return results;
    }
}
