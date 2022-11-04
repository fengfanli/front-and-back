package com.feng.user_manager.mapper;

import com.feng.user_manager.bean.UserData;
import com.feng.user_manager.vo.req.UserDataReq;
import com.feng.user_manager.vo.req.UserDataUpdateReq;
import com.feng.user_manager.vo.resp.BirthdayPieChartResp;
import com.feng.user_manager.vo.resp.GenderBarChartResp;
import com.feng.user_manager.vo.resp.TotalTravelTimeLineChartResp;
import com.feng.user_manager.vo.resp.UserDataResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface UserDataMapper {
    List<UserDataResp> queryUserDataList(UserDataReq user);

    Integer getAllUserDataCount(UserDataReq user);

    ArrayList<Integer> getBirthday();

    List<UserDataResp> queryUserDataListTest();

    UserDataResp queryUserDataById(int id);

    List<UserDataResp> queryUserDataByName(String name);

    int addUserData(UserDataReq user);

    int updateUser(UserDataUpdateReq user);

    int deleteUser(int id);


    // 性别维度，柱状图
    List<GenderBarChartResp> getGenderBarChart(UserDataReq user);

    // 生日维度，饼状图
    List<BirthdayPieChartResp> getBirthdayPieChart(UserDataReq user);

    // 旅行时间维度，折线图
    List<TotalTravelTimeLineChartResp> getTotalTravelTimeLineChart(UserDataReq user);

    int getBirthdayIntervalPieChart(UserDataReq user);
}
