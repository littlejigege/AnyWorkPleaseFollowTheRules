package com.qgstudio.anywork.core;

public class Apis {
    static {
        System.loadLibrary("jni_apis");
    }
    public static native String loginApi();
    public static native String collectApi();
    public static native String unCollectApi();
    public static native String getAllCollectionsApi();
    public static native String registerApi();
    public static native String getTestpaperApi();
    public static native String submitTestpaperApi();
    public static native String uploadFeedbackApi();
    public static native String changeInfoApi();
    public static native String getJoinOrganizationApi();
    public static native String getAllOrganizationApi();
    public static native String joinOrganizationApi();
    public static native String leaveOrganizationApi();
    public static native String getNoticeApi();
    public static native String markWatchedApi();
    public static native String getTotalRankingApi();
    public static native String getRankingApi();
    public static native String changeUserInfoApi();
    public static native String changePasswordApi();
    public static native String changePicApi();
    public static native String logoutApi();
    public static native String getChapterApi();
    public static native String getCatalogApi();


}
