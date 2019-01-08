package com.face.commom;

import com.face.thread.StreamThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author huangwh
 * @date 2019/1/8
 * @time 10:55
 */
public class ProcessUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static Boolean execute(String command)throws IOException,InterruptedException{
        Runtime runtime = Runtime.getRuntime();
        Process process = executeCommand(runtime,command);
        InputStream errorStream = process.getErrorStream();
        StreamThread errorThread = new StreamThread(errorStream);
        InputStream inputStream = process.getInputStream();
        StreamThread inputThread = new StreamThread(inputStream);

        errorThread.start();
        inputThread.start();
        //执行堵塞等待结果返回
        process.waitFor();
        return process.exitValue() == 0;
    }

    public static Boolean execute(String logType,String taskName,String command)throws IOException,InterruptedException{
        Runtime runtime = Runtime.getRuntime();
        Process process = executeCommand(runtime,command);
        InputStream errorStream = process.getErrorStream();
        StreamThread errorThread = new StreamThread(logType,errorStream,taskName,ConfigCommon.ERROR_CODE);
        InputStream inputStream = process.getInputStream();
        StreamThread inputThread = new StreamThread(logType,inputStream,taskName,ConfigCommon.NORMAL_CODE);

        errorThread.start();
        inputThread.start();
        //执行堵塞等待结果返回
        process.waitFor();
        return process.exitValue() == 0;
    }


    //处理命令并执行，返回Process
    private static Process executeCommand(Runtime runtime,String command) throws IOException{
        Process process;
        //这里包括对多行命令行处理,多行命令行
        if(command.split("\n").length > 1){
            process= runtime.exec("sh");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            for(String cmd : command.split("\n")) {
                os.writeBytes(cmd.replace("\r", "")+"\n");
            }
            os.flush();
            os.close();
        }else{
            process = runtime.exec(command);
        }
        return process;
    }
}
