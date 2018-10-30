package com.qgstudio.anywork.data;

import android.support.annotation.NonNull;

import rx.Subscriber;


/**
 * 此类对 Subscriber 进行了一层扩展
 * @author Yason 2017/4/12.
 */

public abstract class RetrofitSubscriber<T> extends Subscriber<ResponseResult<T>> {
    /**
     * 服务器返回数据成功
     * @param data 数据内容
     */
    protected abstract void onSuccess(T data);

    /**
     * 服务器返回数据失败
     * @param info 错误信息
     */
    protected abstract void onFailure(String info);

    /**
     * 访问网络出现异常
     * @param t 异常日志
     */
    protected abstract void onMistake(Throwable t);

    /** 成功的状态码 */
    public static final int mSuccessState = 1;

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable t) {
        onMistake(t);
    }

    @Override
    public void onNext(ResponseResult<T> tResponseResult) {
        if (tResponseResult.getState() != mSuccessState) {
            onFailure(tResponseResult.getStateInfo());
            return;
        }
        onSuccess(tResponseResult.getData());
    }

}
