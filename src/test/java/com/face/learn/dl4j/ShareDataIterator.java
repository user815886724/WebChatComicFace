package com.face.learn.dl4j;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author huangwh
 * @date 2018/11/11
 * @time 9:24
 */
public class ShareDataIterator implements DataSetIterator {

    private static final int VECTOR_SIZE = 7;

    //批次训练组数
    private int batchNum;

    //每个训练组的长度（ShareData的个数）
    private int shareLength;

    //数据训练集
    private List<ShareData> dataList;

    //存放剩余组的index信息(每个训练组的开始index)
    private List<Integer> dataRecord;

    private double[] maxNum;


    public ShareDataIterator() {
        dataRecord = new ArrayList<>();
    }

    /**
     * 加载数据初始化
     * @param shareDataList
     * @param batchNum
     * @param shareLength
     * @return
     */
    public Boolean loadData(List<ShareData> shareDataList,int batchNum,int shareLength){
        this.batchNum = batchNum;
        this.shareLength = shareLength;

        //初始化最大值数组
        maxNum = new double[7];
        for(int i = 0;i < maxNum.length;i++){
            maxNum[i] = 0;
        }
        //赋值数组
        dataList = shareDataList;
        if(dataList.size() < 1){
            return false;
        }
        for (ShareData data : dataList){
            //强行赋值
            if(maxNum[0] < data.getOpenPrice()){
                maxNum[0] = data.getOpenPrice();
            }
            if(maxNum[1] < data.getClosePrice()){
                maxNum[1] = data.getClosePrice();
            }
            if(maxNum[2] < data.getRiseFallAmount()){
                maxNum[2] = data.getRiseFallAmount();
            }
            if(maxNum[3] < data.getLowest()){
                maxNum[3] = data.getLowest();
            }
            if(maxNum[4] < data.getHighest()){
                maxNum[4] = data.getHighest();
            }
            if(maxNum[5] < data.getVolume()){
                maxNum[5] = data.getVolume();
            }
            if(maxNum[6] < data.getTurnover()){
                maxNum[6] = data.getTurnover();
            }
        }
        resetDataRecord();
        return true;
    }


    private void resetDataRecord(){
        dataRecord.clear();
        //（训练集个数 / 训练组长度）= 训练组的训练总量
        int total = dataList.size() / shareLength + 1;
        //存入每个训练组的开始index
        // （比如第一组从0开始，第二组从shareLength开始，第三组从2*shareLength......）
        for(int i = 0; i < total; i++){
            dataRecord.add(i * shareLength);
        }
    }


    public double[] getMaxArr(){
        return this.maxNum;
    }

    /**
     * 获得接下来一次的训练数据集
     * @param num
     * @return
     */
    @Override
    public DataSet next(int num) {
        if( dataRecord.size() <= 0 ) {
            throw new NoSuchElementException();
        }
        int actualBatchSize = Math.min(num,dataRecord.size());
        int actualLength = Math.min(shareLength,dataList.size() - dataRecord.get(0) - 1);
        INDArray input = Nd4j.create(new int[]{actualBatchSize,VECTOR_SIZE,actualLength}, 'f');
        INDArray label = Nd4j.create(new int[]{actualBatchSize,1,actualLength}, 'f');
        ShareData nextData = null,curData = null;
        //获取每批次的训练数据和标签数据
        for(int i = 0;i < actualBatchSize;i++){
            int index = dataRecord.remove(0);
            int endIndex = Math.min(index + shareLength,dataList.size() - 1);
            curData = dataList.get(index);
            for(int j = index; j < endIndex; j++){
                //获取数据信息,预先保存下一个信息
                nextData = dataList.get(j+1);
                //构造训练向量
                int c = endIndex - j - 1;
                input.putScalar(new int[]{i,0,c}, curData.getOpenPrice() / maxNum[0]);
                input.putScalar(new int[]{i,1,c}, curData.getClosePrice() / maxNum[1]);
                input.putScalar(new int[]{i,2,c}, curData.getRiseFallAmount() / maxNum[2]);
                input.putScalar(new int[]{i,3,c}, curData.getLowest() / maxNum[3]);
                input.putScalar(new int[]{i,4,c}, curData.getHighest() / maxNum[4]);
                input.putScalar(new int[]{i,5,c}, curData.getVolume() / maxNum[5]);
                input.putScalar(new int[]{i,6,c}, curData.getTurnover() / maxNum[6]);

                //构造label向量
                label.putScalar(new int[]{i, 0, c}, nextData.getClosePrice() / maxNum[1]);
                curData = nextData;
            }
            if (dataRecord.size() <= 0){
                break;
            }
        }
        return new DataSet(input,label);
    }

    @Override
    public int totalExamples() {
        return (dataList.size()) / shareLength;
    }

    @Override
    public int inputColumns() {
        return dataList.size();
    }

    @Override
    public int totalOutcomes() {
        return 1;
    }

    @Override
    public boolean resetSupported() {
        return false;
    }

    @Override
    public boolean asyncSupported() {
        return false;
    }

    @Override
    public void reset() {
        resetDataRecord();
    }

    @Override
    public int batch() {
        return batchNum;
    }

    @Override
    public int cursor() {
        return totalExamples() - dataRecord.size();
    }

    @Override
    public int numExamples() {
        return totalExamples();
    }

    @Override
    public void setPreProcessor(DataSetPreProcessor dataSetPreProcessor) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public DataSetPreProcessor getPreProcessor() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public List<String> getLabels() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public DataSet next() {
        return next(batchNum);
    }


    @Override
    public boolean hasNext() {
        return dataRecord.size() > 0;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
