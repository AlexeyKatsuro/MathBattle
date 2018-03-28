package com.dedalexey.mathbattle.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dedalexey.mathbattle.R;
import com.dedalexey.mathbattle.model.SessionInfo;
import com.dedalexey.mathbattle.model.Statistics;

import java.util.List;


public class StatisticsFragment extends Fragment {

    private static final String TAG = StatisticsFragment.class.getSimpleName() ;
    RecyclerView mStatisticsRecyclerView;
    TextView mNoResultTextView;
    SessionInfoAdapter mAdapter;
    List<SessionInfo> mSessionInfoList;

    public static StatisticsFragment newInstance() {

        Bundle args = new Bundle();

        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics,container,false);
        mStatisticsRecyclerView = view.findViewById(R.id.fragment_recycler_view);
        mStatisticsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNoResultTextView = view.findViewById(R.id.no_results_text_view);
        updateUI();

        return view;
    }

    public void updateUI() {
        mSessionInfoList = Statistics.get(getActivity()).getSessionInfoList();

        if (mAdapter == null) {
            mAdapter = new SessionInfoAdapter(mSessionInfoList);
            mStatisticsRecyclerView.setAdapter(mAdapter);

        } else {
            mAdapter.setSessionInfoList(mSessionInfoList);
            mAdapter.notifyDataSetChanged();
        }

        if(isListEmpty(mAdapter.getSessionInfoList().size())){
            return;
        }
    }
    public void scrollToPositionOf(SessionInfo sessionInfo){
        int position = 0;
        for(SessionInfo sesInf: mSessionInfoList){
            if(sessionInfo.equals(sesInf)){
                position = mSessionInfoList.indexOf(sesInf);
                break;
            }
        }
        mStatisticsRecyclerView.scrollToPosition(position);
        mAdapter.setPositionForSelect(position);
    }

    private boolean isListEmpty(int size) {
        if(mSessionInfoList.size()>0){
            mNoResultTextView.setVisibility(View.GONE);
            return false;
        } else {
            mNoResultTextView.setVisibility(View.VISIBLE);
            return true;
        }
    }

    private class SessionInfoHolder extends RecyclerView.ViewHolder{
        SessionInfo mSessionInfo;

        TextView mPlayerName;
        TextView mTrueAnswers;
        TextView mPosition;

        public SessionInfoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateUI();
                }
            });
            mPlayerName = itemView.findViewById(R.id.player_name);
            mTrueAnswers = itemView.findViewById(R.id.true_answers);
            mPosition = itemView.findViewById(R.id.number);
        }

        void bindSessionInfo(SessionInfo sessionInfo, int position){
            mSessionInfo = sessionInfo;
            mPlayerName.setText(mSessionInfo.getPlayerName());
            mTrueAnswers.setText(String.valueOf(mSessionInfo.getTrueAnswers()));
            mPosition.setText(String.valueOf(position) + ". ");
        }

        void setTextColor(int color){
            mPlayerName.setTextColor(color);
            mTrueAnswers.setTextColor(color);
            mPosition.setTextColor(color);
        }

    }

    private class SessionInfoAdapter extends RecyclerView.Adapter<SessionInfoHolder>{

        private List<SessionInfo> mSessionInfoList;
        private int positionForSelect=-1;

        public SessionInfoAdapter(List<SessionInfo> sessionInfoList) {
            mSessionInfoList = sessionInfoList;
        }

        @Override
        public SessionInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.statistics_list_item,parent,false);
            return new SessionInfoHolder(view);
        }

        @Override
        public void onBindViewHolder(SessionInfoHolder holder, int position) {
            SessionInfo sessionInfo = mSessionInfoList.get(position);
            holder.bindSessionInfo(sessionInfo,position+1);
            if(position==positionForSelect){
                holder.setTextColor(getResources().getColor(R.color.colorTrue));
            }
        }

        public int getPositionForSelect() {
            return positionForSelect;
        }

        public void setPositionForSelect(int positionForSelect) {
            this.positionForSelect = positionForSelect;
        }

        @Override
        public int getItemCount() {
            return mSessionInfoList.size();
        }

        public void setSessionInfoList(List<SessionInfo> sessionInfoList) {
            mSessionInfoList = sessionInfoList;
        }

        public List<SessionInfo> getSessionInfoList() {
            return mSessionInfoList;
        }
    }

}
