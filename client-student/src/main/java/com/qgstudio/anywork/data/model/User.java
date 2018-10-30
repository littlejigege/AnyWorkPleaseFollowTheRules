package com.qgstudio.anywork.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 用户实体
 * Created by FunriLy on 2017/7/10.
 * From small beginnings comes great things.
 */
public class User implements Parcelable, Cloneable {

    private int userId;         //id
    private String userName;    //昵称
    private String email;       //邮箱
    private String password;    //密码
    private String phone;       //手机
    private String studentId;   //学号
    private String imagePath;   //头像路径
    private int mark;           //标志，区分是学生还是教师, 0学生，1老师

    public User(int id, String name, String email, String password, String phone, String studentId, String imagePath, int mark) {
        this.userId = id;
        this.userName = (name == null ? "" : name);
        this.email = (email == null ? "" : email);
        this.password = (password == null ? "" : password);
        this.phone = (phone == null ? "" : phone);
        this.studentId = (studentId == null ? "" : studentId);
        this.imagePath = (imagePath == null ? "" : imagePath);
        this.mark = mark;
    }

    public User(String name, String email, String password, String phone, String studentId, String imagePath, int mark) {
        new User(-1, name, email, password, phone, studentId, imagePath, mark);
    }

    public User() {
        new User(-1, "", "", "", "", "", "",-1);
    }

    //get & set

    protected User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        email = in.readString();
        password = in.readString();
        phone = in.readString();
        studentId = in.readString();
        imagePath = in.readString();
        mark = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    // toString

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", studentId='" + studentId + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", mark=" + mark +
                '}';
    }

    @Override
    public User clone() {
        User user = null;
        try{
            user = (User) super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(phone);
        dest.writeString(studentId);
        dest.writeString(imagePath);
        dest.writeInt(mark);
    }
}
