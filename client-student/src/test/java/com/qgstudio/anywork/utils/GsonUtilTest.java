package com.qgstudio.anywork.utils;

import com.qgstudio.anywork.data.model.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * GsonUtils 测试类
 * Created by chenyi on 2017/7/19.
 */
public class GsonUtilTest {

    User user = null;
    String json = null;

    @Before
    public void setUp() throws Exception {
        user = new User(15, "龙辉", "1581660518@qq.com", "", "15626155888", 0);
        json = "{\"userId\":15,\"userName\":\"龙辉\",\"email\":\"1581660518@qq.com\",\"password\":\"\",\"phone\":\"15626155888\",\"mark\":0}";
    }

    @Test
    public void gsonString() throws Exception {
        assertEquals(json, GsonUtil.GsonString(user));
    }

    @Test
    public void gsonToBean() throws Exception {
        assertEquals(user.getUserId(), (GsonUtil.GsonToBean(json, User.class)).getUserId());
    }

    @Test
    public void gsonToMaps() throws Exception {

    }

}