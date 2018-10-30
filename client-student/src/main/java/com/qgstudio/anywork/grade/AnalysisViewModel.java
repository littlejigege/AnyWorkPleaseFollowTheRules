package com.qgstudio.anywork.grade;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.qgstudio.anywork.collection.CollectionApi;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AnalysisViewModel extends ViewModel {
    CollectionApi api = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(CollectionApi.class);

    public void getAllContions(final OnResultListener listener) {
        final MutableLiveData<List<Question>> liveData = new MutableLiveData<>();
        api.getAllCollections()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<List<Question>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseResult<List<Question>> listResponseResult) {
                        if (listener != null) {
                            listener.onResult(listResponseResult.getData());
                        }
                    }
                });
    }

    public void collect(int questionID, final OnResultListener listener) {
        Map map = new HashMap();
        map.put("questionId", questionID);
        api.collect(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onResult(false);
                        }
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        if (listener != null) {
                            listener.onResult(true);
                        }
                    }
                });
    }

    public void uncollect(int questionID, final OnResultListener listener) {
        Map map = new HashMap();
        map.put("questionId", questionID);
        api.uncollect(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onResult(false);
                        }
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        if (listener != null) {
                            listener.onResult(true);
                        }
                    }
                });
    }

    public interface OnResultListener {
        void onResult(Object o);
    }
}
