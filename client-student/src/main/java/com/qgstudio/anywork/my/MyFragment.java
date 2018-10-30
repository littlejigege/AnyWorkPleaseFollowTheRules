package com.qgstudio.anywork.my;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qgstudio.anywork.App;
import com.qgstudio.anywork.R;
import com.qgstudio.anywork.enter.EnterActivity;
import com.qgstudio.anywork.feedback.FeedbackActivity;
import com.qgstudio.anywork.main.NewOrganizationActivity;
import com.qgstudio.anywork.user.ChangeInfoActivity;
import com.qgstudio.anywork.user.ChangePasswordActivity;
import com.qgstudio.anywork.user.UserActivity;
import com.qgstudio.anywork.utils.GlideUtil;
import com.qgstudio.anywork.utils.ToastUtil;
import com.tencent.bugly.beta.Beta;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment extends Fragment {

    private CircleImageView head;
    private View edit;
    private TextView feedback;
    private TextView name;
    private TextView studentId;
    private ImageView viewBackground;

    private TextView exerciseRecord;
    private TextView changePassword;
    private TextView about;
    private TextView update;
    private TextView logout;

    static Toast toast;

    public MyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);

//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        initView(view);
        setDetails(view);

        return view;
    }

    /**
     * 细节优化
     *
     * @param viewGroup
     */
    private void setDetails(View viewGroup) {
        //获得系统状态栏高度
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelOffset(resourceId);
        }
        //将整个布局向下平移状态栏的高度
        ViewGroup.LayoutParams viewP = viewBackground.getLayoutParams();
        viewP.height = viewP.height + result;
        viewBackground.setLayoutParams(viewP);
        FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.my_frame_layout);
        ViewGroup.MarginLayoutParams frameLayoutP = (ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams();
        frameLayoutP.topMargin = frameLayoutP.topMargin + result;
        frameLayout.setLayoutParams(frameLayoutP);
        ViewGroup.MarginLayoutParams nameP = (ViewGroup.MarginLayoutParams) name.getLayoutParams();
        nameP.topMargin = nameP.topMargin + result;
        name.setLayoutParams(nameP);
        ViewGroup.MarginLayoutParams headP = (ViewGroup.MarginLayoutParams) head.getLayoutParams();
        headP.topMargin = headP.topMargin + result;
        head.setLayoutParams(headP);
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        head = (CircleImageView) view.findViewById(R.id.my_head);
        feedback = (TextView) view.findViewById(R.id.feedback);
        edit = view.findViewById(R.id.my_frame_layout);
        name = (TextView) view.findViewById(R.id.my_name);
        studentId = (TextView) view.findViewById(R.id.my_student_id);
        viewBackground = (ImageView) view.findViewById(R.id.view);
        exerciseRecord = (TextView) view.findViewById(R.id.exercise_record);
        changePassword = (TextView) view.findViewById(R.id.change_password);
        about = (TextView) view.findViewById(R.id.about);
        update = (TextView) view.findViewById(R.id.update);
        logout = (TextView) view.findViewById(R.id.logout);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeInfoActivity.class);
                startActivity(intent);
            }
        });
        name.setText(App.getInstance().getUser().getUserName());
        studentId.setText(App.getInstance().getUser().getStudentId());

        exerciseRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("此功能暂未开放");
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toast == null){
                    toast = Toast.makeText(getActivity(),
                            "Copyright (C) 2018\n" +
                                    "AnyWork2.0\n" +
                                    "小平科技创新团队\n" +
                                    "计算机学院QG工作室\n" +
                                    "荣誉出品",
                            Toast.LENGTH_SHORT);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        TextView textView = (TextView) toast.getView().findViewById(Resources.getSystem().getIdentifier("message", "id", "android"));
                        if (textView != null) {
                            textView.setGravity(Gravity.CENTER);
                        }
                    }
                }
                toast.show();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Beta.checkUpgrade();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.getContext(), EnterActivity.class);
                startActivity(intent);

                getActivity().finish();
            }
        });

//        //将头像设置为背景
//        GlideUtil.setPictureWithOutCacheWithBlur(viewBackground, App.getInstance().getUser().getUserId(), R.drawable.ic_user_default, getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        GlideUtil.setPictureWithOutCache(head, App.getInstance().getUser().getUserId(), R.drawable.icon_head);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
