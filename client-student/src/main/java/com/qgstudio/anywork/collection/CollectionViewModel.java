package com.qgstudio.anywork.collection;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.Question;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectionViewModel extends ViewModel {
    CollectionApi api = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(CollectionApi.class);
    private MutableLiveData<List<Question>> listLiveData = new MutableLiveData<>();

    public LiveData<List<Question>> getAllContions() {
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
                        listLiveData.postValue(listResponseResult.getData());
                    }
                });
        return listLiveData;
    }

}
