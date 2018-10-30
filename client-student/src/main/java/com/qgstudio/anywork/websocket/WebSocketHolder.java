package com.qgstudio.anywork.websocket;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qgstudio.anywork.notice.data.Message;
import com.qgstudio.anywork.notice.data.MessageFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


public class WebSocketHolder extends WebSocketListener {
    /**
     * 默认的WebSocketHolder，也是唯一的
     */
    private static WebSocketHolder defaultHolder;
    private WebSocket webSocket;
    private OkHttpClient client;
    private Map<Object, Subscription> subscriptions;
    //计数君，自带监听机制
    public MutableLiveData<Integer> onlineCount;

    private WebSocketHolder() {
        //初始化在线人数，0
        onlineCount = new MutableLiveData<>();
        onlineCount.postValue(0);
    }

    /**
     * @return 获取WebSocketHolder单例
     */
    public static WebSocketHolder getDefault() {
        if (defaultHolder == null) {
            defaultHolder = new WebSocketHolder();
        }

        return defaultHolder;
    }

    /**
     * 注册为WebSocket消息订阅者
     * @param subscriber 消息订阅者
     */
    public void register(Object subscriber) {

        if (subscriptions != null && subscriptions.get(subscriber) != null) {
            //已经注册过了
            return;
        }
        Class<?> subscriberClass = subscriber.getClass();
        Method[] methods = subscriberClass.getDeclaredMethods();
        List<SubscriberMethod> subscriberMethodList = new ArrayList<>();
        for (Method method : methods) {
            WS wsAnnotation = method.getAnnotation(WS.class);
            if (wsAnnotation != null) {
                SubscriberMethod subscriberMethod = new SubscriberMethod(method.getParameterTypes()[0],
                        wsAnnotation.threadMode(), method);
                subscriberMethodList.add(subscriberMethod);
            }
        }
        Subscription subscription = new Subscription(subscriber, subscriberMethodList);
        if (subscriptions == null) {
            subscriptions = new HashMap<>();
        }
        subscriptions.put(subscriber, subscription);
    }

    /**
     * 取消注册
     * @param subscriber 消息订阅者
     */
    public void unregister(Object subscriber) {
        subscriptions.remove(subscriber);
    }

    public void post(Object object) {
        if (subscriptions == null) {
            return;
        }
        Iterator iterator = subscriptions.keySet().iterator();
        if (iterator.hasNext()) {
            Subscription subscription = subscriptions.get(iterator.next());
            subscription.invokeMethods(object);
        }
    }

    /**
     * 连接WebSocket服务器
     * @param url WebSocketServer的地址
     */
    public void connect(String url) {
        Log.e("webSocketUrl", url);
        if (webSocket == null) {
            Request request = new Request.Builder().url(url).build();
            if (client == null) {
                client = new OkHttpClient.Builder().build();
            }
            client.newWebSocket(request, this);
        }
    }

    /**
     * 向WebSocket服务器发送字符串
     * @param text 要发送的字符串
     */
    public void sendTextToServer(String text) {
        if (webSocket != null) {
            webSocket.send(text);
        } else {
            Log.e("WebSocketError", "webSocket is null,can't send " + text);
        }
    }

    /**
     * 断开连接
     */
    public void disconnect(){
        //强行关闭连接
        if (webSocket != null) {
            webSocket.cancel();
        }
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        this.webSocket = webSocket;
        Log.e("WebSocketOpen", "长连接建立成功");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        Log.d("WebSocketMessage", text);
        //转换信息
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(text, JsonObject.class);
        Message message = MessageFactory.fromJsonObject(jsonObject);
        //下发信息
        post(message);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
        Log.e("WebSocketClosed",reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
        Log.e("WebSocketFailure", t.getMessage());
    }
}
