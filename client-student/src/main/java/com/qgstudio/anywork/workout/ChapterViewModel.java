package com.qgstudio.anywork.workout;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.qgstudio.anywork.App;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.workout.data.Chapter;
import com.qgstudio.anywork.workout.data.ChapterApi;
import com.qgstudio.anywork.workout.data.Testpaper;
import com.qgstudio.anywork.workout.data.WorkoutInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChapterViewModel extends ViewModel {
    private WorkoutCatalogFragment fragment;
    private ChapterApi api;

    public void setFragment(WorkoutCatalogFragment fragment) {
        this.fragment = fragment;
        api = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(ChapterApi.class);
    }

    public void getChapter() {
        Map map = new HashMap();
        map.put("organizationId", fragment.classId);
        api.getChapter(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<List<Chapter>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseResult<List<Chapter>> listResponseResult) {
                        fragment.onChapterGet(listResponseResult.getData());
                    }
                });
    }

    public void getCatalog(int chapterID) {
        Map map = new HashMap();
        map.put("organizationId", fragment.classId);
        map.put("chapter", chapterID);
        map.put("testPaperType", fragment.type.toInt());
        api.getCatalog(map)
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
                        fragment.onCatalogGet(listResponseResult.getData());
                    }
                });
    }

}
