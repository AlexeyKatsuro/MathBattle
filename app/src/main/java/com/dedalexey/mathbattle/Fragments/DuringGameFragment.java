package com.dedalexey.mathbattle.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import com.dedalexey.mathbattle.model.Statistics;
import com.dedalexey.mathbattle.model.Answer;
import com.dedalexey.mathbattle.model.MathQuestionProvider;
import com.dedalexey.mathbattle.model.Question;
import com.dedalexey.mathbattle.model.QuestionListProvider;
import com.dedalexey.mathbattle.model.SessionInfo;

import java.util.List;

/**
 * Created by Alexey on 20.01.2018.
 */

public class DuringGameFragment extends GameFragment {

    private static final String TAG = DuringGameFragment.class.getSimpleName();
    private static final String SESSION_INFO = "session_info";
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
    private Question mQuestion;
    private boolean isFinished;

    public interface CallBacks {
        void onEndGame();
    }

    public static DuringGameFragment newInstance(SessionInfo sessionInfo) {

        DuringGameFragment fragment = new DuringGameFragment();
        fragment.setSessionInfo(sessionInfo);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProvider = new MathQuestionProvider();

        mQuestion = mProvider.provideQuestionNumber(mIndexQuestion);

        initCallBacksListener();
        mTimer = new MyCountDownTimer(mInitialTime,50);
        mTimer.setOnTickListener(new MyCountDownTimer.onTickListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isAdded()) {
                    mTimeTextView.setText(getString(R.string.time_format, millisUntilFinished / 1000, millisUntilFinished % 1000 / 10));
                    mProgressBar.setMax((int) mTimer.getTotalTime());
                    mProgressBar.setProgress((int) (mTimer.getTotalTime() - millisUntilFinished));
                }
            }
        });
        mTimer.setOnFinishListener(new MyCountDownTimer.onFinishListener() {
            @Override
            public void onFinish() {
                Log.d(TAG, "Time Out");
                onFinishGame();
            }
        });
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
        mProgressBar.setMax((int) mInitialTime);

        if(mAdapter!=null) {
            mAnswersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mAnswersRecyclerView.setAdapter(mAdapter);
        }


        updateUI();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"ON PAUSE");
        if(mTimer!=null){
            mTimer.cancel();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"ON STOP");
        
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"ON RESUME");
        if(mTimer!=null){
           mTimer.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"ON DESTROY");
        if(mTimer!=null){
            mTimer.cancel();
        }
        mCallBacks = null;
        mTimer = null;

    }

    private void updateUI() {


        if(isAdded()) {


            mQuestionTextView.setText(mQuestion.getQuestionText() + " = ?");
            mMistakesTextView.setText(getString(R.string.mistake_format, mMistakesValue, mMaxMistakesValue));

            if (mQuestionList != null) {
                mIndexTextView.setText(getString(R.string.round_format_list, mIndexQuestion , mQuestionList.size()));
            } else {
                mIndexTextView.setText(getString(R.string.round_format_single, mIndexQuestion));
            }

            if (mAdapter == null) {
                mAdapter = new AnswerAdapter(mQuestion.getAnswerList());
                mAnswersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mAnswersRecyclerView.setAdapter(mAdapter);

            } else {
                mAdapter.setAnswerList(mQuestion.getAnswerList());
                mAdapter.notifyDataSetChanged();
            }

            if(isFinished){
                finishUI();
            }
        }

    }

    public synchronized void onFinishGame() {
        Log.d(TAG, "onFinishGame");

        if(isFinished){
            return;
        }
        Log.d(TAG,"Finish: "+mSessionInfo.toString());
        isFinished= true;
        mTimer.cancel();
        mTimer.setFinished(true);
        finishUI();

        Handler handler = new Handler();
        vibrate(1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCallBacks.onEndGame();
            }
        },5000);

    }

    private void finishUI() {
        mSessionInfo.setTotalTime(mTimer.getTotalTime());
        mAnswersRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mTimeTextView.setVisibility(View.INVISIBLE);
        mQuestionTextView.setText(R.string.game_over);
    }

    private void initCallBacksListener() {
        Fragment fragment = getParentFragment();
        if ( fragment instanceof CallBacks) {
            mCallBacks = (CallBacks) fragment;
        } else {
            Log.i(TAG,"fragment NOT instanceof CallBacks");
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
            mAnswerButton.setBackground(getResources().getDrawable(R.drawable.button_shape));
        }

        @Override
        public void onClick(View view) {

            if(mAnswer.isTrue()) {
                onTrueAnswerClick();
            } else {
                onFalseAnswerClick();
            }
            //mQuestionTextView.setText(mQuestion.getQuestionText() + " = " + mQuestion.getTrueAnswer().toString());
            if(mMistakesValue>mMaxMistakesValue){
                Log.d(TAG, "Many mistakes");
                onFinishGame();
                return;
            }

            mQuestion = mProvider.provideQuestionNumber(mIndexQuestion);
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    updateUI();
                }
            });
        }

        private void onFalseAnswerClick() {
            mAnswerButton.setBackground(getResources().getDrawable(R.drawable.false_shape));
            mTimer.addTime(-3000);
            mMistakesValue++;
            mSessionInfo.increaseFalseAnswers();
            vibrate(250);
        }

        private void onTrueAnswerClick() {
            mTimer.addTime(3000);
            mProgressBar.setMax((int) mTimer.getTotalTime());
            mIndexQuestion++;
            mSessionInfo.increaseTrueAnswers();
            mAnswerButton.setBackground(getResources().getDrawable(R.drawable.true_shape));
        }
    }

    private void vibrate(long mill) {
       // ((Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(mill);
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
