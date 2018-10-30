package com.qgstudio.anywork.ranking.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.RankingMessage;
import com.qgstudio.anywork.utils.GlideUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RankingMessage> rankingMessages;

    public RankingAdapter(Context context, ArrayList<RankingMessage> rankingMessages) {
        this.context = context;
        this.rankingMessages = rankingMessages;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView numberImage;
        TextView numberText;
        CircleImageView headPic;
        TextView name;
        TextView studentId;

        public ViewHolder(View itemView) {
            super(itemView);

            numberImage = (ImageView) itemView.findViewById(R.id.number_image);
            numberText = (TextView) itemView.findViewById(R.id.number_text);
            headPic = (CircleImageView) itemView.findViewById(R.id.head_pic);
            name = (TextView) itemView.findViewById(R.id.name);
            studentId = (TextView) itemView.findViewById(R.id.student_id);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RankingMessage rankingMessage = rankingMessages.get(position);
        if (position == 0) {
            holder.numberText.setVisibility(View.GONE);
            holder.numberImage.setVisibility(View.VISIBLE);
            holder.numberImage.setImageResource(R.drawable.icon_ranking_first);
        } else if (position == 1) {
            holder.numberText.setVisibility(View.GONE);
            holder.numberImage.setVisibility(View.VISIBLE);
            holder.numberImage.setImageResource(R.drawable.icon_ranking_second);
        } else if (position == 2) {
            holder.numberText.setVisibility(View.GONE);
            holder.numberImage.setVisibility(View.VISIBLE);
            holder.numberImage.setImageResource(R.drawable.icon_ranking_third);
        } else {
            //设置字体
            holder.numberText.setTypeface(Typeface.createFromAsset(context.getAssets(), "zhankukuaileti.ttf"));

            holder.numberImage.setVisibility(View.GONE);
            holder.numberText.setVisibility(View.VISIBLE);
            holder.numberText.setText("NO." + (position + 1));
        }
        GlideUtil.setPictureWithOutCache(holder.headPic, rankingMessage.getImagePath(), R.drawable.icon_head);
        holder.name.setText(rankingMessage.getUsername());
        holder.studentId.setText(rankingMessage.getStudentId());
    }

    @Override
    public int getItemCount() {
        return rankingMessages.size();
    }
}
