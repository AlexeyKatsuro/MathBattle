package com.dedalexey.mathbattle.model;


import java.util.List;

public interface QuestionListProvider {
    List<Question> provideQuestionList();
    List<Question> provideQuestionList(int limit);
    Question provideQuestionNumber(int index);
}
