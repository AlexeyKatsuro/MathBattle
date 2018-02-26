package com.dedalexey.mathbattle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dedalexey.mathbattle.model.MenuRow;

import java.util.List;

/**
 * Created by Alexey on 07.02.2018.
 */

public class MenuRowAdapter extends RecyclerView.Adapter<MenuRowAdapter.MenuRowHolder>{
    protected class MenuRowHolder extends RecyclerView.ViewHolder {


        TextView mTitle;
        ImageView mIcon;
        MenuRow mMenuRow;

        public MenuRowHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_text_view);
            mIcon = itemView.findViewById(R.id.icon_image_view);
        }

        public void bindMenuRow(MenuRow menuRow){
            mMenuRow = menuRow;
            mTitle.setText(mMenuRow.getTitle());
            mIcon.setImageResource(mMenuRow.getImageId());
            itemView.setOnClickListener(mMenuRow.getOnClickListener());
        }

    }
    private List<MenuRow> mMenuRowList;
    private Context mContext;
    private int mResLayout;

    public MenuRowAdapter(Context context,int resLayout,List<MenuRow> menuRowList) {
        mContext = context;
        mMenuRowList = menuRowList;
        mResLayout = resLayout;
    }

    @Override
    public MenuRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(mResLayout,parent,false);
        return new MenuRowHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuRowHolder holder, int position) {
        MenuRow menuRow = mMenuRowList.get(position);
        holder.bindMenuRow(menuRow);
    }

    @Override
    public int getItemCount() {
        return mMenuRowList.size();
    }
}