package com.qgstudio.anywork.user;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.util.LogTime;
import com.qgstudio.anywork.App;
import com.qgstudio.anywork.R;
import com.qgstudio.anywork.StartActivity;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.User;
import com.qgstudio.anywork.utils.DataBaseUtil;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.LogUtil;
import com.qgstudio.anywork.utils.MyOpenHelper;
import com.qgstudio.anywork.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChangePasswordActivity extends AppCompatActivity {

    private static final String TAG = "ChangePasswordActivity";
    
    @BindView(R.id.editText_old)
    EditText etOldPw;

    @BindView(R.id.editText_new)
    EditText etNewPw;

    @BindView(R.id.editText_again)
    EditText etAgainPw;

    @BindView(R.id.button_save)
    Button btSave;

    @BindView(R.id.image_cancel)
    ImageView IgCancel;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.sample_blue));
        }
        ButterKnife.bind(this);
        user = App.getInstance().getUser();

        IgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassWord = etOldPw.getText().toString();
                final String newPassWord = etNewPw.getText().toString();
                String againPassWord = etAgainPw.getText().toString();
                LogUtil.d("changepassword", oldPassWord);
                LogUtil.d("changepassword", user.getPassword());
                if (!oldPassWord.equals(user.getPassword())){
                    ToastUtil.showToast("原始密码不正确");
                    return;
                }
                if (newPassWord.equals("")){
                    ToastUtil.showToast("重置密码不能为空");
                    return;
                }
                if (!againPassWord.equals(newPassWord)){
                    ToastUtil.showToast("确认密码不一致");
                    return;
                }

                UserApi userApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(UserApi.class);

                Map<String, String> info = new HashMap<>();
//              info.put("userName", user.getUserName());
                info.put("oldPassword", oldPassWord);
                info.put("newPassword", newPassWord);

                Log.i(TAG, "changePassword: " + GsonUtil.GsonString(info));
                userApi.changePassword(info)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseResult>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
//                                mView.showError("网络连接错误");
//                                mView.hidProgressDialog();
                                ToastUtil.showToast("网络连接错误");
                            }

                            @Override
                            public void onNext(ResponseResult result) {
                                assert result != null;

                                if (result.getState() == 1) {
                                    ToastUtil.showToast("密码修改成功");
                                    user.setPassword(newPassWord);
                                    MyOpenHelper myOpenHelper = DataBaseUtil.getHelper();
                                    myOpenHelper.save(user);
                                    App.getInstance().setUser(user);
                                    finish();
                                } else {
                                    ToastUtil.showToast(result.getStateInfo());
                                }
                            }
                        });
            }
        });
    }
}
