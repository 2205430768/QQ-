package com.example.xxy.lovell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.xxy.lovell.R;
import com.example.xxy.lovell.view.RoundImageView;

import java.util.List;

/**
 * Created by wang on 16/7/29.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<String> mStrings;

    public MyRecyclerAdapter(List<String> mStrings) {
        this.mStrings = mStrings;
    }
    @Override
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_picture_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.ViewHolder holder, int position) {
        String contact = mStrings.get(position);
        TextView nameTextView = holder.nameTextView;
        nameTextView.setText(contact);
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RoundImageView headImage;
        public TextView nameTextView;
        public TextView signTextView;
        public Button liaotaButton;

        public ViewHolder(View itemView) {
            super(itemView);
            headImage= (RoundImageView) itemView.findViewById(R.id.head_image);
            nameTextView = (TextView) itemView.findViewById(R.id.user_name);
          //  signTextView= (TextView) itemView.findViewById(R.id.sign_name);
            liaotaButton= (Button) itemView.findViewById(R.id.liaota_button);
        }
    }
}
