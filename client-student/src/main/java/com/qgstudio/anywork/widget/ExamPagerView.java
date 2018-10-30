package com.qgstudio.anywork.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.StudentAnswer;
import com.qgstudio.anywork.utils.DesityUtil;
import com.qgstudio.anywork.utils.ToastUtil;
import com.qgstudio.anywork.workout.AnswerCardAdapter;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;


/**
 * Created by Yason on 2017/7/10.
 */

public class ExamPagerView extends LinearLayout {

    private Context mContext;

    private Toolbar mToolbar;
    private TextView mTitleCenterTV;
    private TextView mTittleRightTV;
    private ImageView mTitleLeftIcon;
    private Drawable mTitleRightIcon;
    private ViewPager mViewPager;
    //    private RecyclerView mRecycler;
    private LinearLayout mBottomLinear;
    private Button mLeftBottomBtn;
    private Button mRightBottomBtn;
    //一推属性
    private String mTitleCenterTextString;
    private int mTitleCenterTextColor;
    private int mTitleLeftIconResId;
    private int mTitleRightIconResId;
    private int mTitleBackgroundColor;
    private String mLeftBottomTextString;
    private int mLeftBottomTextColor;
    private int mLeftBottomBackgroundColor;
    private float mLeftBottomAlpha;
    private String mRightBottomTextString;
    private String mRightIconTittle;
    private int mRightBottomTextColor;
    private int mRightBottomBackgroundColor;
    private float mRightBottomAlpha;
    FrameLayout frameLayout;

    private int mDefaultTextColor;
    private int mDefaultTitleBackgroundColor;
    private int mDefaultBottomBackgroundColor;
    private OnBottomButtonClickListener onBottomButtonClickListener;
    private Mode displayMode = Mode.TESTING;
    private RecyclerView answerCard;
    private AnswerCardAdapter answerCardAdapter;

    public enum Mode {
        ANSWER_CARD,
        TESTING
    }

    @IdRes
    int ID_VIEWPAGER = 1000;


//    private OnTitleRightIconClickListener mOnTitleRightIconClickListener;
//    private OnLeftBottomBtnClickListener mOnLeftBottomBtnClickListener;
//    private OnRightBottomBtnClickListener mOnRightBottomBtnClickListener;
//
//    public interface OnTitleRightIconClickListener {
//        void onTitleRightIconClick ();
//    }
//
//    public interface OnLeftBottomBtnClickListener {
//        void onLeftBottomBtnClick ();
//    }
//
//    public interface OnRightBottomBtnClickListener {
//        void onRightBottomBtnClick ();
//    }
//
//    public void setOnTitleRightIconClick(OnTitleRightIconClickListener onTitleRightIconClick) {
//        mOnTitleRightIconClickListener = onTitleRightIconClick;
//    }
//
//    public void setOnLeftBottomBtnClickListener(OnLeftBottomBtnClickListener onLeftBottomBtnClickListener) {
//        mOnLeftBottomBtnClickListener = onLeftBottomBtnClickListener;
//    }
//
//    public void setOnRightBottomBtnClickListener(OnRightBottomBtnClickListener onRightBottomBtnClickListener) {
//        mOnRightBottomBtnClickListener = onRightBottomBtnClickListener;
//    }

    public void setViewPagerNextItem() {
        int cp = mViewPager.getCurrentItem();
        if (cp < mViewPager.getAdapter().getCount()) {
            mViewPager.setCurrentItem(cp + 1);
        }
    }

    public void setViewPagerLastItem() {
        int cp = mViewPager.getCurrentItem();
        if (cp > 0) {
            mViewPager.setCurrentItem(cp - 1);
        }
    }

    public void setTitleCenterTextString(String text) {
        if (mTitleCenterTV != null) {
            mTitleCenterTV.setText(text);
        }
    }

    public void setViewPagerAdapter(PagerAdapter pagerAdapter) {
        mViewPager.setAdapter(pagerAdapter);
//        mViewPager.setOffscreenPageLimit(fragmentPagerAdapter.getCount());
    }

    public void setViewPagerListener(ViewPager.OnPageChangeListener onPageChangeLitener) {
        mViewPager.setOnPageChangeListener(onPageChangeLitener);
    }

    public ExamPagerView(Context context) {
        super(context);
    }

    public ExamPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setDefault();
        getAttr(attrs);
        //位置
        ViewGroup.LayoutParams params = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        this.setLayoutParams(params);
        //性质
        this.setOrientation(VERTICAL);
        initLayout();
    }

    private void setDefault() {
        TypedValue value = new TypedValue();

        mContext.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        mDefaultTextColor = value.data;

        mContext.getTheme().resolveAttribute(R.attr.colorPrimaryDark, value, true);
        mDefaultTitleBackgroundColor = value.data;

        mContext.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        mDefaultBottomBackgroundColor = value.data;

    }

    private void getAttr(AttributeSet attrs) {
        //获取属性，没有则用默认值
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.ExamPagerView);
        try {
            mTitleCenterTextString = a.getString(R.styleable.ExamPagerView_sTitleCenterTextString);
            mTitleCenterTextColor = a.getColor(R.styleable.ExamPagerView_sTitleCenterTextColor, mDefaultTextColor);
            mTitleLeftIconResId = a.getResourceId(R.styleable.ExamPagerView_sTitleLeftIconResId, -1);
            mTitleRightIconResId = a.getResourceId(R.styleable.ExamPagerView_sTitleRightIconResId, -1);
            mRightIconTittle = a.getString(R.styleable.ExamPagerView_sTitleRightIconTittle);
            mTitleBackgroundColor = a.getColor(R.styleable.ExamPagerView_sTitleBackgroundColor, mDefaultTitleBackgroundColor);
            mLeftBottomTextString = a.getString(R.styleable.ExamPagerView_sLeftBottomTextString);
            mLeftBottomTextColor = a.getColor(R.styleable.ExamPagerView_sLeftBottomTextColor, mDefaultTextColor);
            mLeftBottomBackgroundColor = a.getColor(R.styleable.ExamPagerView_sLeftBottomBackgroundColor, mDefaultBottomBackgroundColor);
            mLeftBottomAlpha = a.getFloat(R.styleable.ExamPagerView_sLeftBottomAlpha, 0.8f);
            mRightBottomTextString = a.getString(R.styleable.ExamPagerView_sRightBottomTextString);
            mRightBottomTextColor = a.getColor(R.styleable.ExamPagerView_sRightBottomTextColor, mDefaultTextColor);
            mRightBottomBackgroundColor = a.getColor(R.styleable.ExamPagerView_sRightBottomBackgroundColor, mDefaultBottomBackgroundColor);
            mRightBottomAlpha = a.getFloat(R.styleable.ExamPagerView_sRightBottomAlpha, 0.8f);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }
    }

    private void initLayout() {
        initToolbar();
        initViewPager();
        initAnswerCard();
        initBottomLinear();
    }

    private void initViewPager() {
        mViewPager = new ViewPager(mContext);

        LayoutParams params = new LayoutParams(MATCH_PARENT, 0);
        params.weight = 1;
        mViewPager.setLayoutParams(params);

        mViewPager.setId(ID_VIEWPAGER);

        addView(mViewPager);
    }

    private void initAnswerCard() {
        answerCard = new RecyclerView(mContext);
        answerCard.setBackgroundColor(Color.WHITE);
        answerCard.setPadding(dp2px(mContext, 32), dp2px(mContext, 32), dp2px(mContext, 32), dp2px(mContext, 32));
        answerCard.setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉边界波纹
        LayoutParams params = new LayoutParams(MATCH_PARENT, 0);
        params.weight = 1;
        answerCard.setLayoutParams(params);
        answerCard.setVisibility(GONE);
        addView(answerCard);
    }
                                        


    public void showAnswerCard(SparseArray<StudentAnswer> answers, int questionSum) {

        displayMode = Mode.ANSWER_CARD;//切换模式
        int answersCount = answers.size();
        //准备好答题卡view
        if (answerCardAdapter == null) {
            answerCardAdapter = new AnswerCardAdapter(answers, questionSum, mContext);
            answerCard.setLayoutManager(new GridLayoutManager(mContext, 5));
            answerCard.setAdapter(answerCardAdapter);
        } else {
            answerCardAdapter.setmDatas(answers);
            answerCardAdapter.notifyDataSetChanged();
        }
        //调整toolbar
        changeToolbar();
        //调整中间显示内容
        answerCard.setVisibility(VISIBLE);
        mViewPager.setVisibility(GONE);
    }

    public void closeAnswerCard() {
        displayMode = Mode.TESTING;
        mViewPager.setVisibility(VISIBLE);
        answerCard.setVisibility(GONE);
        changeToolbar();
    }

    private void initBottomLinear() {
        mBottomLinear = new LinearLayout(mContext);
        //先位置，后性质
        LayoutParams params = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        mBottomLinear.setLayoutParams(params);
        mBottomLinear.setOrientation(HORIZONTAL);

        mLeftBottomBtn = new Button(mContext);
        LinearLayout.LayoutParams left_params = new LayoutParams(0, WRAP_CONTENT);
        left_params.weight = 1;
        mLeftBottomBtn.setLayoutParams(left_params);
        mLeftBottomBtn.setAlpha(mLeftBottomAlpha);
        mLeftBottomBtn.setBackgroundColor(mLeftBottomBackgroundColor);
        mLeftBottomBtn.setText(mLeftBottomTextString);
        mLeftBottomBtn.setTextColor(mLeftBottomTextColor);
        mLeftBottomBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBottomButtonClickListener != null) {
                    onBottomButtonClickListener.onLeftClick();
                }
            }
        });
        mBottomLinear.addView(mLeftBottomBtn);

        mRightBottomBtn = new Button(mContext);
        LinearLayout.LayoutParams right_params = new LayoutParams(0, WRAP_CONTENT);
        right_params.weight = 1;
        mRightBottomBtn.setLayoutParams(right_params);
        mRightBottomBtn.setAlpha(mRightBottomAlpha);
        mRightBottomBtn.setBackgroundColor(mRightBottomBackgroundColor);
        mRightBottomBtn.setText(mRightBottomTextString);
        mRightBottomBtn.setTextColor(mRightBottomTextColor);
        mRightBottomBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBottomButtonClickListener != null) {
                    onBottomButtonClickListener.onRightClick();
                }
            }
        });
        mBottomLinear.addView(mRightBottomBtn);
        showBottomButtons(false);
        this.addView(mBottomLinear);
    }

    private void initToolbar() {
        //1.创建view
        mToolbar = new Toolbar(mContext);

        //2.设置view默认属性
        LayoutParams params = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        params.gravity = Gravity.TOP;
        mToolbar.setLayoutParams(params);

        if (mTitleCenterTextString != null) {
            mTitleCenterTV = new TextView(mContext);
            mTitleCenterTV.setTextSize(18);
            //位置属性
            Toolbar.LayoutParams tv_params = new Toolbar.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            tv_params.gravity = Gravity.CENTER;
            mTitleCenterTV.setLayoutParams(tv_params);
            //性质属性
            mTitleCenterTV.setText(mTitleCenterTextString);
            mTitleCenterTV.setTextColor(mTitleCenterTextColor);
            mToolbar.addView(mTitleCenterTV);
        }
        //左图标
        if (mTitleLeftIconResId != -1) {
            frameLayout = new FrameLayout(mContext);
            mTitleLeftIcon = new ImageView(mContext);
            Toolbar.LayoutParams icon_params = new Toolbar.LayoutParams(dp2px(mContext, 44.0f), dp2px(mContext, 44.0f));
            icon_params.gravity = Gravity.LEFT;
            icon_params.leftMargin = 0;
            frameLayout.setLayoutParams(icon_params);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(dp2px(mContext, 16), dp2px(mContext, 16));
            layoutParams.gravity = Gravity.CENTER;
            mTitleLeftIcon.setLayoutParams(layoutParams);
            mTitleLeftIcon.setImageResource(mTitleLeftIconResId);
            mTitleLeftIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            frameLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext instanceof Activity) {
                        Activity activity = (Activity) mContext;
                        activity.onBackPressed();
                    }
                }
            });
            frameLayout.addView(mTitleLeftIcon);
            mToolbar.addView(frameLayout);
        }
        //右图标
        if (mTitleRightIconResId != -1) {

            mTitleRightIcon = getContext().getResources().getDrawable(mTitleRightIconResId);
            mTitleRightIcon.setBounds(0, 0, 50, 50);
            mTittleRightTV = new TextView(mContext);
            //位置属性
            Toolbar.LayoutParams tv_params = new Toolbar.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            tv_params.gravity = Gravity.RIGHT;
            tv_params.rightMargin = dp2px(mContext, 20);
            mTittleRightTV.setLayoutParams(tv_params);
            //性质属性
            mTittleRightTV.setText(mRightIconTittle);
            mTittleRightTV.setTextSize(8);
            mTittleRightTV.setCompoundDrawablePadding(3);
            mTittleRightTV.setTextColor(Color.WHITE);
            mTittleRightTV.setCompoundDrawables(null, mTitleRightIcon, null, null);

            mToolbar.addView(mTittleRightTV);
        }

        mToolbar.setBackgroundColor(mTitleBackgroundColor);

        //3.将view添加到容器
        this.addView(mToolbar);

    }

    public void setOnTopRightButtonClickListener(OnClickListener listener) {
        mTittleRightTV.setOnClickListener(listener);
    }

    private int dp2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }

    private int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5F);
    }

    public void showBottomButtons(boolean enable) {
        mLeftBottomBtn.setVisibility(enable ? VISIBLE : GONE);
        mRightBottomBtn.setVisibility(enable ? VISIBLE : GONE);

    }

    public void setOnBottomButtonClickListener(OnBottomButtonClickListener listener) {
        onBottomButtonClickListener = listener;
    }

    public interface OnBottomButtonClickListener {
        void onLeftClick();

        void onRightClick();
    }

    private void changeToolbar() {
        if (displayMode == Mode.ANSWER_CARD) {
            mTittleRightTV.setVisibility(GONE);
            mTitleCenterTV.setText("答题卡");
            mTitleCenterTV.setTextColor(Color.WHITE);
            mTitleCenterTV.setVisibility(VISIBLE);
            mTitleLeftIcon.setImageResource(R.drawable.icon_cancel);
            frameLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeAnswerCard();
                }
            });
        } else {
            mTittleRightTV.setVisibility(VISIBLE);
            mTitleCenterTV.setVisibility(GONE);
            mTitleLeftIcon.setImageResource(R.drawable.icon_register_back);
            frameLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mContext instanceof Activity) {
                        Activity activity = (Activity) mContext;
                        activity.onBackPressed();
                    }
                }
            });
        }
    }

}
