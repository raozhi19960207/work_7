package com.example.aspect;

import java.util.concurrent.TimeUnit;
/**
 * Created by 康伟 on 2017/5/19.
 */

public class StopWatch {
    private long startTime;
    private long endTime;
    private long elapsedTime;
    public static int Accuracy = 1; // 1：毫秒   2：微秒
    public StopWatch() {
        //empty
    }

    private void reset() {
        startTime = 0;
        endTime = 0;
        elapsedTime = 0;
    }

    public void start() {
        reset();
        startTime = System.nanoTime();
    }

    public void stop() {
        if (startTime != 0) {
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        } else {
            reset();
        }
    }
    public double getTotalTime(int type){
        if (type == 1){
            return getTotalTimeMillis();
        }else{
            return getTotalTimeMicros();
        }
    }
    public double getTotalTimeMicros() {
        return  (elapsedTime != 0) ? (endTime - startTime) /1000.0 : 0;
    }
    public double getTotalTimeMillis(){
        return (elapsedTime != 0) ? TimeUnit.NANOSECONDS.toMicros(endTime - startTime) /1000.0: 0;
    }
}
