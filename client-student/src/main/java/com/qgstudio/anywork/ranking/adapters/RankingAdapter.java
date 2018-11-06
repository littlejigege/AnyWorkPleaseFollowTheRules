package com.qgstudio.anywork.ranking.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.RankingMessage;
import com.qgstudio.anywork.utils.GlideUtil;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RankingMessage> rankingMessages;
    private Typeface ttf;

    public RankingAdapter(Context context, ArrayList<RankingMessage> rankingMessages) {
        this.context = context;
        this.rankingMessages = rankingMessages;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView numberImage;
        TextView numberText;
        ImageView headPic;
        TextView name;
        TextView studentId;
        TextView score;

        public ViewHolder(View itemView) {
            super(itemView);

            numberImage = itemView.findViewById(R.id.number_image);
            numberText = itemView.findViewById(R.id.number_text);
            headPic = itemView.findViewById(R.id.head_pic);
            name = itemView.findViewById(R.id.name);
            studentId = itemView.findViewById(R.id.student_id);
            score = itemView.findViewById(R.id.score);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
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
            holder.numberText.setTypeface(ttf == null ? Typeface.createFromAsset(context.getAssets(), "zhankukuaileti.ttf") : ttf);

            holder.numberImage.setVisibility(View.GONE);
            holder.numberText.setVisibility(View.VISIBLE);
            holder.numberText.setText("NO." + (position + 1));
        }
        GlideUtil.setPictureWithOutCache(holder.headPic, rankingMessage.getImagePath(), R.drawable.icon_head);
        holder.name.setText(rankingMessage.getUsername());
        holder.studentId.setText(rankingMessage.getStudentId());
        holder.score.setTypeface(ttf == null ? Typeface.createFromAsset(context.getAssets(), "zhankukuaileti.ttf") : ttf);
        //根据分数设置背景颜色
        int score = (int) rankingMessage.getScore();
        if (score < 60) {
            //保持默认红色背景
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.score.setBackground(context.getDrawable(R.drawable.background_score_red));
            }
        } else if (score < 80) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.score.setBackground(context.getDrawable(R.drawable.background_score_yellow));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.score.setBackground(context.getDrawable(R.drawable.background_score_green));
            }
        }
        holder.score.setText(score + "");
        holder.headPic.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("66666", "onTouch: " + event.getAction());

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showPopup(holder.headPic);
                        break;
                    case MotionEvent.ACTION_UP:
                        hidePopup();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        hidePopup();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return rankingMessages.size();
    }

    PopupWindow popupWindow;

    private void showPopup(ImageView view) {
        ImageView view1 = new ImageView(context);
        view1.setImageDrawable(view.getDrawable());
        hidePopup();
        popupWindow = new PopupWindow(view1, 200, 200);
        popupWindow.showAsDropDown(view, view.getWidth() + 50, -100 - view.getHeight() / 2);
    }

    private void hidePopup() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        popupWindow = null;
    }
}
