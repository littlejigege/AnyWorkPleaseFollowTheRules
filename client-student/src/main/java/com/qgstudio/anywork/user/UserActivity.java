package com.qgstudio.anywork.user;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qgstudio.anywork.App;
import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.User;
import com.qgstudio.anywork.dialog.LoadingDialog;
import com.qgstudio.anywork.mvp.MVPBaseActivity;
import com.qgstudio.anywork.utils.GlideUtil;
import com.qgstudio.anywork.utils.LogUtil;
import com.qgstudio.anywork.utils.StorageUtil;
import com.qgstudio.anywork.utils.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *  查看和修改用户信息的 activity
 *  Created by chenyi on 2017/7/12.
 */
public class UserActivity extends MVPBaseActivity<UserContract.View, UserPresenter> implements UserContract.View {

    public static final String TAG = "UserActivity";

    private boolean isEdit = false;
    private boolean isFinish = true;
    private User user;

    @BindView(R.id.head_pic) CircleImageView pic;

    @BindView(R.id.name) TextView name;

    @BindView(R.id.student_id) TextView studentId;

    @BindView(R.id.email) EditText email;

    @BindView(R.id.phone) EditText phone;

    @BindView(R.id.button_save) Button edit;

    @OnClick(R.id.button_save)
    public void save() {
//        backResult();
        String p = phone.getText().toString();
        String m = email.getText().toString();
        if (!m.matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}")) {
            ToastUtil.showToast("请输入正确的邮箱地址");
        }
        if (!p.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$")) {
            ToastUtil.showToast("请输入正确的电话号码");
            return ;
        }
    }

    @OnClick(R.id.edit)
    public void edit() {
        if (isFinish) {
//            editFocusable(true);
            edit.setText("完成");
            edit.setBackgroundResource(R.drawable.bg_btn_blue);
        } else {
//            String n = name.getText().toString();
            String p = phone.getText().toString();
            String m = email.getText().toString();
//            if (!n.matches("[a-z0-9A-Z\\u4e00-\\u9fa5]{1,15}")) {
//                ToastUtil.showToast("请输入1-15个字符的姓名");
//                return ;
//            }
            if (!m.matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}")) {
                ToastUtil.showToast("请输入正确的邮箱地址");
            }
            if (!p.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$")) {
                ToastUtil.showToast("请输入正确的电话号码");
                return ;
            }

            User nUser = user.clone();
//            nUser.setUserName(n);
            nUser.setPhone(p);
            nUser.setEmail(m);
            mPresenter.changeInfo(nUser);
//            editFocusable(false);
            edit.setText("编辑");
            edit.setBackgroundResource(R.drawable.bg_btn_yellow);
        }
        isFinish = !isFinish;
    }

    @OnClick(R.id.head_pic)
    public void changePic() {
        // 调用系统图库获取图片
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);

        //设置不可点击，防止重复调用
        pic.setClickable(false);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //点击头像按钮后的操作结束后，设置头像可点击
        pic.setClickable(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }
        user = App.getInstance().getUser();
        Log.d("linzongzhan", "onCreate: " + user.getEmail());
        setInfo(user);
//        editFocusable(false);
    }

    private void setInfo(User user1) {
        studentId.setText(user1.getStudentId());
        name.setText(user1.getUserName());
        email.setText(user1.getEmail());
        phone.setText(user1.getPhone());
        GlideUtil.setPictureWithOutCache(pic, user1.getUserId(), R.drawable.icon_head);
    }

//    private void editFocusable(boolean focusable) {
////        name.setEnabled(focusable);
//        email.setEnabled(focusable);
//        phone.setEnabled(focusable);
//
//        if (focusable) {
////            name.requestFocus();
//            email.requestFocus();
//            phone.requestFocus();
//
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Fade enterTransition = new Fade();
        enterTransition.setDuration(200);
        getWindow().setEnterTransition(enterTransition);
        getWindow().setExitTransition(enterTransition);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为 back 键，并为 back 键设置监听
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backResult();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void backResult() {
        // 实例化 Bundle，设置需要传递的参数
        Bundle bundle = new Bundle();

        if (isEdit) {
            bundle.putParcelable("user", user);
        } else {
            bundle.putParcelable("user", null);
        }
        // 将修改后的用户信息返回给主页面
        setResult(RESULT_OK, this.getIntent().putExtras(bundle));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        // 将图片设置到头像中
                        Glide.with(this)
                                .load(uri)
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>(250, 250) {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        saveHeadPic(resource);
                                    }
                                });
                    }
                }
                break;
        }
    }

    /**
     * 将获取的图片进行压缩并保存
     */
    private void saveHeadPic(Bitmap bitmap) {
        if (!StorageUtil.isExternalStorageWritable()) {
            ToastUtil.showToast("保存头像失败！");
            return;
        }
        //创建目录
        File dir = StorageUtil.getStoragePrivateDir(this, "anywork");
        if (dir == null) {
            ToastUtil.showToast("保存头像失败！");
            LogUtil.e2(TAG, "saveHeadPic", "create directory fail!");
            return;
        }
        //创建图片
        File file = new File(dir, user.getUserId() + ".jpg");
        try {
            if(!file.exists()){
                if(!file.createNewFile()){
                    ToastUtil.showToast("保存头像失败！");
                    LogUtil.e2(TAG, "saveHeadPic", "create image fail!");
                    return;
                }
            }
            //保存图片
            saveImage(bitmap, file);
            mPresenter.changePic(file.getAbsolutePath());
        } catch (IOException e) {
            ToastUtil.showToast("保存头像失败！");
            LogUtil.e2(TAG, "saveHeadPic", "save image fail!");
        }
    }

    /**
     * bitmap后存到对应路径
     */
    public void saveImage(Bitmap photo, File file) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            LogUtil.e2(TAG, "saveImage", "error：" + e.getMessage());
        }
    }

    @Override
    public void showSuccess(User user) {
        App.getInstance().setUser(user);
        ToastUtil.showToast("信息修改完成");
    }

    @Override
    public void showError(String s) {
        setInfo(user);
        ToastUtil.showToast(s);
    }

    @Override
    public void setUser(User user) {
        setInfo(user);
    }

    @Override
    public void changeImg() {
        GlideUtil.setPictureWithOutCache(pic, user.getUserId(), R.drawable.icon_head);
    }

    private LoadingDialog dialog;

    @Override
    public void showProgressDialog() {
        if (dialog == null) {
            dialog = new LoadingDialog();
        }
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void hidProgressDialog() {
        if (dialog != null && dialog.isResumed()) {
            dialog.dismiss();
        }
    }
}
