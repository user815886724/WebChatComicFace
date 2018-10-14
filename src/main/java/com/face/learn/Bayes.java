package com.face.learn;

import com.face.utils.DecimalCalculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//贝叶斯主体
public class Bayes {

    /**
     * 将训练元组按类别划分
     * @param datas  训练元组
     * @return  Map<类别,属于该类别的训练元组>
     */
    public static Map<String,ArrayList<ArrayList<String>>> dataOfClass(ArrayList<ArrayList<String>> datas){
        Map<String,ArrayList<ArrayList<String>>> map = new HashMap<>();
        for(ArrayList<String> data : datas){
            //名字为样本数组的最后一个
            String categoryName = data.get(data.size() - 1);

            if(map.containsKey(categoryName)){
                map.get(categoryName).add(data);
            }else{
                ArrayList<ArrayList<String>> examples = new ArrayList<>();
                examples.add(data);
                map.put(categoryName,examples);
            }
        }
        return map;
    }


    public static String predictClass(ArrayList<ArrayList<String>> predictData,ArrayList<String> testData){
        Map<String,ArrayList<ArrayList<String>>> example = dataOfClass(predictData);
        //获得分类名称的集合
        Object categoryNames[] = example.keySet().toArray();
        double maxPredictRadio = 0.00;
        String maxPredictKey = "";
        for(Object categoryName : categoryNames){
            ArrayList<ArrayList<String>> categoryData = example.get(categoryName.toString());
            //表示当前类别的数据占所有数据的比例(取3位小数)
            double categoryOfRadio = DecimalCalculate.div(categoryData.size(),predictData.size(),3);
            for(int i = 0; i < testData.size();i++){
                double probability = predictProbability(categoryData,testData.get(i),i);
                categoryOfRadio = DecimalCalculate.mul(categoryOfRadio,probability);
            }
            if(categoryOfRadio > maxPredictRadio){
                maxPredictRadio = categoryOfRadio;
                maxPredictKey = categoryName.toString();
            }
        }
        return maxPredictKey;
    }


    /**
     * 计算指定属性列上指定值出现的概率(指定属性列数据表示数据中相同属性的数据)
     * @param predictData 属于某一类的训练元组
     * @param value 列值
     * @param index 属性列索引
     * @return 概率
     */
    private static double predictProbability(ArrayList<ArrayList<String>> predictData, String value, int index){
        double probability = 0.00;
        int count = 0;
        int total = predictData.size();
        for(ArrayList<String> data : predictData){
            if(data.get(index).equals(value)){
                count++;
            }
        }
        probability = DecimalCalculate.div(count,total,3);
        return probability;
    }
}
