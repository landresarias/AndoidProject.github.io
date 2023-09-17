package com.androidproject.reminderpal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SQLiteDBAdapter extends RecyclerView.Adapter<SQLiteDBAdapter.MyViewHolder>{
    private Context context;
    private ArrayList arrTypeName,arrTypeIcon,arrEvDetail,arrEvDate,arrEvTime;
    private View view;
    public String strGetIcon;

    //----------------------------------------------------------------
    public SQLiteDBAdapter(Context parContext, ArrayList parTypeName,ArrayList parTypeIcon,ArrayList parEvDetail,
                           ArrayList parEvDate,ArrayList parEvTime) {
        this.context = parContext;
        this.arrTypeName = parTypeName;
        this.arrTypeIcon = parTypeIcon;
        this.arrEvDetail = parEvDetail;
        this.arrEvDate = parEvDate;
        this.arrEvTime = parEvTime;
    }

    //----------------------------------------------------------------
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.listdetail,
                parent,false);
        return new MyViewHolder(view);
    }

    //----------------------------------------------------------------
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder parHolder,int parPosition) {
        parHolder.tvTypeName.setText(String.valueOf(arrTypeName.get(parPosition)));
        parHolder.tvEvDetail.setText(String.valueOf(arrEvDetail.get(parPosition)));
        parHolder.tvEvDate.setText(String.valueOf(arrEvDate.get(parPosition)));
        parHolder.tvEvTime.setText(String.valueOf(arrEvTime.get(parPosition)));
        strGetIcon = (String) arrTypeIcon.get(parPosition);
        if(strGetIcon.equals("birthday.png")){
            new MyViewHolder(view).ivTypeIcon.setImageResource(R.drawable.birthday);
        }
        else if(strGetIcon.equals("holly_day.png")){
            new MyViewHolder(view).ivTypeIcon.setImageResource(R.drawable.holly_day);
        }
        else if(strGetIcon.equals("special_occasion.png")){
            new MyViewHolder(view).ivTypeIcon.setImageResource(R.drawable.special_occasion);
        }
        else if(strGetIcon.equals("company_event.png")){
            new MyViewHolder(view).ivTypeIcon.setImageResource(R.drawable.company_event);
        }
        else if(strGetIcon.equals("project_deadline.png")){
            new MyViewHolder(view).ivTypeIcon.setImageResource(R.drawable.project_deadline);
        }
    }

    //----------------------------------------------------------------
    @Override
    public int getItemCount() {
        return arrTypeName.size();
    }

    //----------------------------------------------------------------
    // ----This is an internal Class--------
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTypeName,tvEvDetail,tvEvDate,tvEvTime;
        public ImageView ivTypeIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTypeName = itemView.findViewById(R.id.tv_typename);
            tvEvDetail = itemView.findViewById(R.id.tv_evdetail);
            tvEvDate = itemView.findViewById(R.id.tv_evdate);
            tvEvTime = itemView.findViewById(R.id.tv_evtime);
            ivTypeIcon = itemView.findViewById(R.id.iv_typeicon);
        }
    }
}
