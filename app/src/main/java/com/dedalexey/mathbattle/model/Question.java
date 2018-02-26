package com.dedalexey.mathbattle.model;

import java.util.List;

public class Question {
    private String mQuestionText;
    private List<Answer> mAnswerList;


    public Question(String questionText, List<Answer> answerList) {
        mQuestionText = questionText;
        mAnswerList = answerList;
    }

    public String getQuestionText() {
        return mQuestionText;
    }

    public void setQuestionText(String questionText) {
        mQuestionText = questionText;
    }

    public List<Answer> getAnswerList() {
        return mAnswerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        mAnswerList = answerList;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(getQuestionText()+ "\n");
        for(Answer answer: mAnswerList){
            result.append(answer + " ");
        }
        result.append("\n");
        return result.toString();
    }
}
