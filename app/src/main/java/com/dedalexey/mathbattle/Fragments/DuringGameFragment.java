package com.dedalexey.mathbattle.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dedalexey.mathbattle.MyCountDownTimer;
import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.Answer;
import com.dedalexey.mathbattle.model.MathQuestionProvider;
import com.dedalexey.mathbattle.model.Question;
import com.dedalexey.mathbattle.model.QuestionListProvider;

import java.util.List;

/**
 * Created by Alexey on 20.01.2018.
 */

public class DuringGameFragment extends Fragment {

    private static final String TAG = DuringGameFragment.class.getSimpleName();
    private List<Question> mQuestionList;
    private QuestionListProvider mProvider;
    private int mIndexQuestion=0;
    private int mMistakesValue=0;
    private int mMaxMistakesValue = 3;

    private ProgressBar mProgressBar;
    private TextView mQuestionTextView;
    private TextView mIndexTextView;
    private TextView mTimeTextView;
    private TextView mMistakesTextView;
    private RecyclerView mAnswersRecyclerView;
    private AnswerAdapter mAdapter;
    private CallBacks mCallBacks;

    private MyCountDownTimer mTimer;
    private long mInitialTime = 15000;

    public interface CallBacks {
        void onEndGame();
    }

    public static DuringGameFragment newInstance() {

        Bundle args = new Bundle();

        DuringGameFragment fragment = new DuringGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProvider = new MathQuestionProvider();


        initCallBacksListener();
        mTimer = new MyCountDownTimer(mInitialTime,50);
        mTimer.setOnTickListener(new MyCountDownTimer.onTickListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeTextView.setText(getString(R.string.time_format,millisUntilFinished/1000,millisUntilFinished%1000/10));
                mProgressBar.setProgress((int) (mTimer.getTotalTime()-millisUntilFinished));
            }
        });
        mTimer.setOnFinishListener(new MyCountDownTimer.onFinishListener() {
            @Override
            public void onFinish() {
                onFinishGame();
            }
        });
    }

    public void onFinishGame() {
        mTimer.cancel();
        mCallBacks.onEndGame();
    }

    private void initCallBacksListener() {
        Fragment fragment = getParentFragment();
        if ( fragment instanceof CallBacks) {
            mCallBacks = (CallBacks) fragment;
        } else {
            Log.i(TAG,"fragment NOT instanceof CallBacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmant_during_game,container,false);

        mProgressBar = view.findViewById(R.id.progressBar);
        mQuestionTextView = view.findViewById(R.id.question_text_view);
        mIndexTextView = view.findViewById(R.id.rounds_text_view);
        mMistakesTextView = view.findViewById(R.id.mistakes_text_view);
        mAnswersRecyclerView = view.findViewById(R.id.answers_recycler_view);
        mTimeTextView = view.findViewById(R.id.time_text_view);
        mAnswersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mProgressBar.setMax((int) mInitialTime);


        updateUI();
        mTimer.start();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mTimer!=null){
            mTimer.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mTimer!=null){
            mTimer.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCallBacks = null;
        mTimer = null;
    }
    private void updateUI() {


        Question question = mProvider.provideQuestionNumber(mIndexQuestion);

        mQuestionTextView.setText(question.getQuestionText());
        mMistakesTextView.setText(getString(R.string.mistake_format,mMistakesValue,mMaxMistakesValue));

        if(mQuestionList!=null) {
            mIndexTextView.setText(getString(R.string.round_format_list, mIndexQuestion + 1, mQuestionList.size()));
        } else {
            mIndexTextView.setText(getString(R.string.round_format_single, mIndexQuestion + 1));
        }

        if(mAdapter == null) {
            mAdapter = new AnswerAdapter(question.getAnswerList());
            mAnswersRecyclerView.setAdapter(mAdapter);

        } else {
            mAdapter.setAnswerList(question.getAnswerList());
            mAdapter.notifyDataSetChanged();
        }

    }

    private class AnswerHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Answer mAnswer;
        TextView mAnswerTextView;
        Button mAnswerButton;

        public AnswerHolder(View itemView) {
            super(itemView);
            mAnswerButton = itemView.findViewById(R.id.answer_button);
            mAnswerTextView = itemView.findViewById(R.id.answer_text_view) ;
            mAnswerButton.setOnClickListener(this);
        }
        public void bindAnswer(Answer answer){
            mAnswer = answer;
            mAnswerTextView.setText(mAnswer.toString());
            if(answer.isTrue()) {
                mAnswerButton.setBackgroundResource(R.drawable.button_true);
            } else {
                mAnswerButton.setBackgroundResource(R.drawable.button_false);
            }
        }

        @Override
        public void onClick(View view) {
//            if(mTimer!=null) {
//                mTimer.cancel();
//            }


            if(mAnswer.isTrue()) {
                mTimer.addTime(3000);
                mProgressBar.setMax((int) mTimer.getTotalTime());
            } else {
                mTimer.addTime(-2000);
                mMistakesValue++;
                ((Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(250);
            }

            if(mMistakesValue>mMaxMistakesValue){
                onFinishGame();
                return;
            } else {
                mIndexQuestion++;
            }

            updateUI();

            //mTimer.start();
        }
    }

    private class AnswerAdapter extends RecyclerView.Adapter<AnswerHolder>{

        List<Answer> mAnswerList;

        public AnswerAdapter(List<Answer> answerList) {
            mAnswerList = answerList;
        }

        public List<Answer> getAnswerList() {
            return mAnswerList;
        }

        public void setAnswerList(List<Answer> answerList) {
            mAnswerList = answerList;
        }

        @Override
        public AnswerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.answer_list_item,parent,false);
            return new AnswerHolder(view);
        }

        @Override
        public void onBindViewHolder(AnswerHolder holder, int position) {
            Answer answer = mAnswerList.get(position);
            holder.bindAnswer(answer);
        }

        @Override
        public int getItemCount() {
            if(mAnswerList == null) return 0;

            return mAnswerList.size();
        }
    }
}
