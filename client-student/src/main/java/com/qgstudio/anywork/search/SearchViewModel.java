package com.qgstudio.anywork.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.qgstudio.anywork.App;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.workout.data.Testpaper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {
    public LiveData<List<Testpaper>> paperList = new MutableLiveData<>();
    private SearchApi api = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(SearchApi.class);

    /**
     * @param keyword 搜索的关键字
     * @return
     */
    public void search(String keyword) {

        Map map = new HashMap();
        map.put("keyword", keyword);
        api.searchTestPaper(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<List<Testpaper>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseResult<List<Testpaper>> listResponseResult) {
                        //下发数据
                        ((MutableLiveData) paperList).postValue(listResponseResult.getData());
                    }
                });
    }
}
