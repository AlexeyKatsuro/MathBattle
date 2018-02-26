package com.dedalexey.mathbattle;


import android.os.CountDownTimer;

public class MyCountDownTimer {

    private boolean mIsWasStoped;
    private boolean mIsRunning;

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
        mTimer.cancel();
        mIsRunning=false;
        mIsWasStoped=true;
    }
    public void resume(){
        if(mIsWasStoped) {
            mTimer = newCountDownTimer(mUntilFinishedTime, mDownInterval);
           start();
        }
    }
    public void start(){
        mTimer.start();
        mIsRunning = true;

    }


    public boolean isRunning() {
        return mIsRunning;
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
        mTimer = newCountDownTimer(mUntilFinishedTime,mDownInterval);
        mTimer.start();
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
