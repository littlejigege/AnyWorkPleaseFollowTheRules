package com.qgstudio.aniwork.workout;

import android.arch.lifecycle.ViewModel;

import com.qgstudio.aniwork.core.Apis;
import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.data.RetrofitClient;
import com.qgstudio.aniwork.workout.data.Chapter;
import com.qgstudio.aniwork.workout.data.ChapterApi;
import com.qgstudio.aniwork.workout.data.Testpaper;

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
        if (fragment == null) {
            return;
        }
        Map map = new HashMap();
        map.put("organizationId", fragment.classId);
        fragment.loading();
        api.getChapter(Apis.getChapterApi(), map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<List<Chapter>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                        if (fragment == null) {
                            return;
                        }
                        fragment.loadError();
                    }

                    @Override
                    public void onNext(ResponseResult<List<Chapter>> listResponseResult) {
                        if (fragment == null) {
                            return;
                        }
                        if (listResponseResult.getData().isEmpty()) {
                            fragment.loadEmpty();
                        } else {
                            fragment.loadSuccess();
                        }
                        fragment.onChapterGet(listResponseResult.getData());

                    }
                });
    }

    public void getCatalog(int chapterID) {
        Map map = new HashMap();
        map.put("organizationId", fragment.classId);
        map.put("chapter", chapterID);
        map.put("testPaperType", fragment.type.toInt());
        fragment.loading();
        api.getCatalog(Apis.getCatalogApi(), map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<List<Testpaper>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                        if (fragment == null) {
                            return;
                        }
                        fragment.loadError();
                    }

                    @Override
                    public void onNext(ResponseResult<List<Testpaper>> listResponseResult) {
                        if (fragment == null) {
                            return;
                        }
                        fragment.loadSuccess();
                        fragment.onCatalogGet(listResponseResult.getData());
                    }
                });
    }


}
