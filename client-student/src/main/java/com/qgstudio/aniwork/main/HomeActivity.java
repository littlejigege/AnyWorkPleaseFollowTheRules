package com.qgstudio.aniwork.main;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.mobile.utils.ActivityManager;
import com.qgstudio.aniwork.App;
import com.qgstudio.aniwork.R;
import com.qgstudio.aniwork.common.DialogManagerActivity;
import com.qgstudio.aniwork.core.Apis;
import com.qgstudio.aniwork.data.ResponseResult;
import com.qgstudio.aniwork.data.RetrofitClient;
import com.qgstudio.aniwork.my.MyFragment;
import com.qgstudio.aniwork.notice.data.Notice;
import com.qgstudio.aniwork.ranking.RankingFragment;
import com.qgstudio.aniwork.user.UserApi;
import com.qgstudio.aniwork.websocket.ThreadMode;
import com.qgstudio.aniwork.websocket.WS;
import com.qgstudio.aniwork.websocket.WebSocketHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeActivity extends DialogManagerActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "HomeActivity";
    public static final String ACTION = TAG + "$Receiver";//广播action
    public static int statusBarHeight = 0;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    private FragmentManager mFragmentManager;
    private BroadcastReceiver mReceiver;
    private HomeFragment homeFragment;
    private RankingFragment rankingFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        initView();
        registerBroadcast();
        WebSocketHolder.getDefault().register(this);
        String baseUrl = "ws://" + RetrofitClient.RETROFIT_CLIENT.getRetrofit().baseUrl().host() + ":" +
                RetrofitClient.RETROFIT_CLIENT.getRetrofit().baseUrl().port();
        WebSocketHolder.getDefault().connect("ws://39.98.41.126/anywork" + "/websocket/" + App.getInstance().getUser().getUserId());
    }

    @Override
    protected void onDestroy() {
        unregisterBroadcast();
        super.onDestroy();
    }

    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        registerReceiver(mReceiver, intentFilter);
    }

    private void unregisterBroadcast() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }

    private void initView() {
        ButterKnife.bind(this);
        //设置底部导航栏的颜色
        bottomNavigationView.setItemIconTintList(null);
        mFragmentManager = getSupportFragmentManager();
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
        }
        mFragmentManager.beginTransaction()
                .replace(R.id.frame, homeFragment)
                .commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//获得系统状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelOffset(resourceId);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        ActivityManager.INSTANCE.doubleExit(2000, "再按一次退出", new Function0<Unit>() {
            @Override
            public Unit invoke() {
                finish();
                return null;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0: {
                if (resultCode == RESULT_OK) {

                }
                break;
            }
            default: {
            }
        }
    }

    private void logout() {
        UserApi userApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(UserApi.class);
        userApi.logout(Apis.logoutApi()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {

                    }
                });
    }

    @WS(threadMode = ThreadMode.MAIN)
    public void onNoticeGet(Notice notice) {
        //收到公告推送，显示提醒
        Log.e("HomeActivity", "收到notice");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getTitle().toString()) {
            case "首页":
                if (homeFragment == null) homeFragment = HomeFragment.newInstance();
                mFragmentManager = getSupportFragmentManager();
                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, homeFragment)
                        .commit();
                break;
            case "排行榜":
                if (rankingFragment == null) rankingFragment = new RankingFragment();
                mFragmentManager = getSupportFragmentManager();
                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, rankingFragment)
                        .commit();
                break;
            case "我的":
                if (myFragment == null) myFragment = new MyFragment();
                mFragmentManager = getSupportFragmentManager();
                mFragmentManager.beginTransaction()
                        .replace(R.id.frame, myFragment)
                        .commit();
                break;
        }
        return true;
    }


}
