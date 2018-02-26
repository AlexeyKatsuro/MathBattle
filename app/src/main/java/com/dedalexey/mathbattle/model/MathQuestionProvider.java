package com.dedalexey.mathbattle.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 19.09.2017.
 */

public class MathQuestionProvider implements QuestionListProvider{

    private final static String TAG = "MathQuestionProvider";

    private final int numbtoIncrease = 2;
    private List<Question> mQuestionList = new ArrayList<>();;

    private static final int[] mSummands = new int[]{1,2,3};
    private static final String[] mOperations = new String[]{"+","-"};


    public MathQuestionProvider() {
    }


    private Question createQuestion(int index){
        StringBuilder stringBuilder = new StringBuilder();
        List<Answer> answerList = getAnswerList();

        int sum = 0;
        int randSummand = (int) (Math.random()*mSummands.length);
        int randOperations;

        while (!(sum >=1 && sum<=3)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(mSummands[randSummand]);
            sum = mSummands[randSummand];
            for (int i = 0; i <= index/numbtoIncrease; i++) {

                randOperations = (int) (Math.random() * mOperations.length);

                stringBuilder.append(mOperations[randOperations]);

                randSummand = (int) (Math.random() * mSummands.length);

                stringBuilder.append(mSummands[randSummand]);
                sum =calulate(sum,mSummands[randSummand],mOperations[randOperations]);
            }
        }
        answerList.get(sum-1).setTrue(true);
        stringBuilder.append(" = ?");

        return new Question(stringBuilder.toString(), answerList);
    }

    private  List<Answer> getAnswerList(){
        List<Answer> answerList = new ArrayList<>();
        answerList.add(new Answer("1",false));
        answerList.add(new Answer("2",false));
        answerList.add(new Answer("3",false));
        return answerList;
    }
    private int calulate(int a, int b, String operation) {
        if (operation.equals(mOperations[0])) {
            return a + b;
        } else if (operation.equals(mOperations[1])) {
            return a - b;
        }

        return 0;
    }

    @Override
    public List<Question> provideQuestionList(int limit) {
        for (int i=0;i<limit;i++){
            mQuestionList.add(createQuestion(i));
            Log.i(TAG,mQuestionList.get(i).toString());
        }
        return mQuestionList;
    }

    @Override
    public List<Question> provideQuestionList() {
        return null;
    }

    @Override
    public Question provideQuestionNumber(int index) {
        return createQuestion(index);
    }
}