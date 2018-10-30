package com.qgstudio.anywork.enter.login;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ForgetPasswordFragment extends DialogFragment {

    private ForgetApi forgetApi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_forget_password, container, false);

        final EditText email = view.findViewById(R.id.email_input);
        Button confirm = view.findViewById(R.id.confirm);
        Button cancle = view.findViewById(R.id.cancel);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().equals("")) {
                    email.setError("邮箱输入不能为空");
                    email.requestFocus();
                } else {
                    if (forgetApi == null) {
                        forgetApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(ForgetApi.class);
                    }
                    Map<String, String> info = new HashMap<>();
                    info.put("email", email.getText().toString());
                    forgetApi.forget(info)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseResult>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    ToastUtil.showToast(e.getMessage() == null ? "发生未知错误" : e.getMessage());

                                }

                                @Override
                                public void onNext(ResponseResult responseResult) {
                                    ToastUtil.showToast(responseResult.getStateInfo());
                                    dismiss();
                                }
                            });
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }
}
