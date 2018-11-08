package com.qgstudio.aniwork.collection;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.qgstudio.aniwork.core.Apis;
import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.data.RetrofitClient;
import com.qgstudio.aniwork.data.model.Question;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectionViewModel extends ViewModel {
    CollectionApi api = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(CollectionApi.class);
    private MutableLiveData<List<Question>> listLiveData = new MutableLiveData<>();

    public LiveData<List<Question>> getAllContions() {
        api.getAllCollections(Apis.getAllCollectionsApi())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<List<Question>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        listLiveData.postValue(null);
                    }

                    @Override
                    public void onNext(ResponseResult<List<Question>> listResponseResult) {
                        listLiveData.postValue(listResponseResult.getData());
                    }
                });
        return listLiveData;
    }

}
