package com.dedalexey.mathbattle;


import android.os.CountDownTimer;
import android.util.Log;

public class MyCountDownTimer {

    private static final String TAG = MyCountDownTimer.class.getSimpleName();
    private boolean isWasStopped;
    private boolean isRunning;
    private boolean isFinished;

    private CountDownTimer mTimer;
    private onTickListener mOnTickListener;
    private onFinishListener mOnFinishListener;

    private long mTotalTime;
    private long mUntilFinishedTime;
    private long mDownInterval;
    public interface onTickListener{
        void onTick(long millisUntilFinished);
    }
    public interface onFinishListener{
        void onFinish();
    }

    public MyCountDownTimer(long totalTime, long downInterval) {
        mTotalTime = totalTime;
        mUntilFinishedTime=mTotalTime;
        mDownInterval = downInterval;
        mTimer = newCountDownTimer(mTotalTime,mDownInterval);
    }
    public void cancel(){
        if(isRunning) {
            mTimer.cancel();
            isRunning = false;
            isWasStopped = true;
        }
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public void resume(){
        if(!isFinished) {
            Log.d(TAG,"Resume");
            mTimer = newCountDownTimer(mUntilFinishedTime, mDownInterval);
           start();
        }
    }
    public void start(){
        mTimer.cancel();
        mTimer.start();
        isRunning = true;
        isFinished=false;

    }


    public boolean isRunning() {
        return isRunning;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isWasStopped() {
        return isWasStopped;
    }

    private CountDownTimer newCountDownTimer(final long totalTime, final long downInterval) {
       return new CountDownTimer(totalTime, downInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                mUntilFinishedTime-=downInterval;
                if(mOnFinishListener!=null) {
                    mOnTickListener.onTick(mUntilFinishedTime);
                }
            }

            @Override
            public void onFinish() {
                isRunning=false;
                isFinished=true;
                if(mOnFinishListener!=null) {
                    mOnFinishListener.onFinish();
                }
            }

        };
    }
    public void addTime(long millis){
        mTimer.cancel();
        if(millis>=0)
        mTotalTime+=millis;
        mUntilFinishedTime+=millis;
        resume();
    }

    public long getMillisUntilFinishedTime() {
        return mUntilFinishedTime;
    }

    public long getTotalTime() {
        return mTotalTime;
    }

    public void setOnTickListener(onTickListener onTickListener) {
        mOnTickListener = onTickListener;
    }

    public void setOnFinishListener(onFinishListener onFinishListener) {
        mOnFinishListener = onFinishListener;
    }
}
