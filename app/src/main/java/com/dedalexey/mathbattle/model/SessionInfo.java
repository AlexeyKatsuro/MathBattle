package com.dedalexey.mathbattle.model;

import java.util.Date;
import java.util.UUID;

public class SessionInfo {

    private UUID mId;
    private String mPlayerName;
    private long mTotalTime;
    private int mTrueAnswers;
    private Date mDate;
    private int mFalseAnswers;

    public SessionInfo() {
        this(UUID.randomUUID());
    }

    public SessionInfo(UUID id) {
        mId = id;
        mDate = new Date();
        mPlayerName = "Unnamed";
    }


    public String getPlayerName() {
        return mPlayerName;
    }

    public void setPlayerName(String playerName) {
        mPlayerName = playerName;
    }


    public long getTotalTime() {
        return mTotalTime;
    }

    public void setTotalTime(long totalTime) {
        mTotalTime = totalTime;
    }

    public int getTrueAnswers() {
        return mTrueAnswers;
    }

    public void setTrueAnswers(int trueAnswers) {
        mTrueAnswers = trueAnswers;
    }

    public int getFalseAnswers() {
        return mFalseAnswers;
    }

    public void setFalseAnswers(int falseAnswers) {
        mFalseAnswers = falseAnswers;
    }

    public void increaseTrueAnswers(){
        mTrueAnswers++;
    }
    public void increaseFalseAnswers(){
        mFalseAnswers++;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public UUID getId() {
        return mId;
    }

    @Override
    public String toString() {
        return "SessionInfo{" +
                "mId=" + mId +
                ", mPlayerName='" + mPlayerName + '\'' +
                ", mTrueAnswers=" + mTrueAnswers +
                ", mFalseAnswers=" + mFalseAnswers +
                ", mTotalTime=" + mTotalTime +
                ", mDate=" + mDate +
                '}';
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof SessionInfo){
            SessionInfo sessionInfo = (SessionInfo) obj;
            if(mId.toString().equals(sessionInfo.getId().toString())){
                return true;
            } else {
                return false;
            }
        } else {
            return super.equals(obj);
        }
    }
}
