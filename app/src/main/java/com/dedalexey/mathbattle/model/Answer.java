package com.dedalexey.mathbattle.model;

/**
 * Created by Alexey on 20.01.2018.
 */

public class Answer {
    private boolean isTrue;
    private String mAnswerText;

    public Answer(String answerText, boolean isTrue) {
        this.isTrue = isTrue;
        this.mAnswerText = answerText;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    @Override
    public String toString() {
        return getAnswerText();
    }

    public String getAnswerText() {
        if(mAnswerText!=null) {
            return mAnswerText;
        } else {
            return String.valueOf(isTrue);
        }
    }

    public void setAnswerText(String answerText) {
        this.mAnswerText = answerText;
    }
}
