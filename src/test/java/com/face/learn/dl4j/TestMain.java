package com.face.learn.dl4j;

import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangwh
 * @date 2018/11/11
 * @time 16:34
 */
public class TestMain {

    // 这个随机数生成器用一个随机种子来确保训练时使用的初始权重维持一致。
    private static final int rngSeed = 12345;
    private static final int IN_NUM = 7;
    private static final int OUT_NUM = 1;
    private static final int Epochs = 100;

    private static final int lstmLayer1Size = 50;
    private static final int lstmLayer2Size = 100;

    public static MultiLayerNetwork getNetModel(int nIn, int nOut){
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                //随机梯度下降
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                //迭代：指的是一个学习步骤，亦即模型权重的一次更新
                .iterations(1)
                /**
                 * 学习速率即每次迭代时对于权重的调整幅度，亦称步幅。
                 * 学习速率越高，神经网络“翻越”整个误差曲面的速度就越快，但也更容易错过误差极小点。
                 * 学习速率较低时，网络更有可能找到极小值，但速度会变得非常慢，因为每次权重调整的幅度都比较小。
                 */
                .learningRate(0.01)
                .rmsDecay(0.5)
                //该参数将一组随机生成的权重确定为初始权重。
                .seed(rngSeed)
                //正则化（regularization）是用来防止过拟合的一种方法。
                .regularization(true).l2(0.001)
                .weightInit(WeightInit.XAVIER)
                //权重的更新器（updater）
                //动量（momentum）是另一项决定优化算法向最优值收敛的速度的因素。动量影响权重调整的方向
                .updater(Updater.RMSPROP)/*.momentum(0.9)*/
                //函数可指定网络中层的数量；它会将您的配置复制n次，建立分层的网络结构。
                .list()
                .layer(0, new GravesLSTM.Builder().nIn(nIn).nOut(lstmLayer1Size)
                        .activation("tanh").build())
                .layer(1, new GravesLSTM.Builder().nIn(lstmLayer1Size).nOut(lstmLayer2Size)
                        .activation("tanh").build())
                .layer(2, new RnnOutputLayer.Builder(LossFunctions.LossFunction.MSE).activation("identity")
                        .nIn(lstmLayer2Size).nOut(nOut).build())
                .pretrain(false).backprop(true)
                .build();

        MultiLayerNetwork net = new MultiLayerNetwork(conf);
        net.init();
        net.setListeners(new ScoreIterationListener(1));

        return net;
    }



    public static void train(MultiLayerNetwork net,ShareDataIterator iterator)throws IOException{
        //迭代训练
        for(int i = 0;i < Epochs;i++) {
            DataSet dataSet = null;
            while (iterator.hasNext()) {
                dataSet = iterator.next();
                net.fit(dataSet);
            }
            iterator.reset();
            System.out.println();
            System.out.println("=================>完成第"+i+"次完整训练");
            INDArray initArray = getInitArray(iterator);

            System.out.println("预测结果：");
            for(int j = 0;j < 20;j++) {
                INDArray output = net.rnnTimeStep(initArray);
                System.out.print(output.getDouble(0) * iterator.getMaxArr()[1]+" ");
            }

            System.out.println();
            net.rnnClearPreviousState();
        }
        //保存模型
//        ModelSerializer.writeModel(net, "model.bin", true);
    }

    private static INDArray getInitArray(ShareDataIterator iterator){
        double[] maxNums = iterator.getMaxArr();
        INDArray initArray = Nd4j.zeros(1, 7, 1);
        initArray.putScalar(new int[]{0,0,0}, 6.23/maxNums[0]);
        initArray.putScalar(new int[]{0,1,0}, 6.18/maxNums[1]);
        initArray.putScalar(new int[]{0,2,0}, -0.09/maxNums[2]);
        initArray.putScalar(new int[]{0,3,0}, 6.13/maxNums[3]);
        initArray.putScalar(new int[]{0,4,0}, 6.26/maxNums[4]);
        initArray.putScalar(new int[]{0,5,0}, 658126/maxNums[5]);
        initArray.putScalar(new int[]{0,6,0}, 40706.18/maxNums[6]);
        return initArray;
    }


    public static void main(String[] args) throws IOException{
        int batchSize = 1;
        int exampleLength = 30;
        ShareDataIterator iterator = new ShareDataIterator();

        iterator.loadData(getData(),batchSize,exampleLength);

        MultiLayerNetwork net = getNetModel(IN_NUM,OUT_NUM);
        train(net, iterator);
    }



    private static List<ShareData> getData(){

        List<ShareData> shareList = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver");//导入驱动（jar包）  
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/face","root","950216");//建立连接  
        }catch(SQLException e){
            e.printStackTrace();
        }

        String sql = "SELECT * FROM share WHERE SHARE_CODE = '600016' AND TIME > '2016-11-01' AND  TIME < '2017-11-01' ORDER BY TIME ASC ";

        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql)  ;
            while (rs.next()){
                ShareData share = new ShareData();
                Double openPrice = rs.getDouble("OPEN_PRICE");
                Double closePrice = rs.getDouble("CLOSE_PRICE");
                Double riseFallAmount = rs.getDouble("RISE_FALL_AMOUNT");
                Double lowest = rs.getDouble("LOWEST");
                Double highest = rs.getDouble("HIGHEST");
                Double volume = rs.getDouble("VOLUME");
                Double turnover = rs.getDouble("TURNOVER");

                share.setClosePrice(closePrice);
                share.setOpenPrice(openPrice);
                share.setRiseFallAmount(riseFallAmount);
                share.setLowest(lowest);
                share.setHighest(highest);
                share.setVolume(volume);
                share.setTurnover(turnover);

                shareList.add(share);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (conn != null){
                try {
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return shareList;
        }
    }

}
