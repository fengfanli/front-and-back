package com.feng.user_manager.vo.req;

import java.io.Serializable;
import java.util.Date;

public class UserDataUpdateReq implements Serializable {
    private Integer user_id;
    private String user_name;
    private Integer gender;
    private String birthday;
    private String total_travel_road;
    private String total_travel_time;

    public UserDataUpdateReq(Integer user_id, String user_name, Integer gender, String birthday, String total_travel_road, String total_travel_time) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.gender = gender;
        this.birthday = birthday;
        this.total_travel_road = total_travel_road;
        this.total_travel_time = total_travel_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getTotal_travel_road() {
        return total_travel_road;
    }

    public void setTotal_travel_road(String total_travel_road) {
        this.total_travel_road = total_travel_road;
    }

    public String getTotal_travel_time() {
        return total_travel_time;
    }

    public void setTotal_travel_time(String total_travel_time) {
        this.total_travel_time = total_travel_time;
    }
}
