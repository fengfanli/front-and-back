package com.feng.user_manager.controller;

import com.feng.user_manager.bean.UserData;
import com.feng.user_manager.mapper.UserDataMapper;
import com.feng.user_manager.vo.req.UserDataReq;
import com.feng.user_manager.vo.req.UserDataUpdateReq;
import com.feng.user_manager.vo.resp.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserDataController {

    @Autowired
    private UserDataMapper userMapper;

    @GetMapping("/test")
    public String getStr(){
        return "hello world";
    }

    @PostMapping("/list")
    public ResultData queryUserList(@RequestBody UserDataReq userDataReq){
        UserDataReq user = new UserDataReq();
        BeanUtils.copyProperties(userDataReq, user);
        user.setPageNum((userDataReq.getPageNum() -1) * userDataReq.getPageSize());
        List<UserDataResp> userList = userMapper.queryUserDataList(user);
        Integer count = userMapper.getAllUserDataCount(userDataReq);
        ResultData result = new ResultData(200, "ok", userList, count);
        return result;
    }

    @GetMapping("/test1")
    public ResultData queryUserListTest(){
        List<UserDataResp> userList = userMapper.queryUserDataListTest();
        ResultData result = new ResultData(200, "ok", userList);
        return result;
    }

    @GetMapping("/birthday")
    public ResultData getBirthday(){
        ArrayList<Integer> birthday = userMapper.getBirthday();
        return new ResultData(200, "ok", birthday);
    }


    // http://localhost:8080/userData/queryUserById/1
    @GetMapping("/queryUserById/{id}")
    public UserDataResp queryUserById(@PathVariable("id") Integer id){
        UserDataResp user = userMapper.queryUserDataById(id);
        return user;
    }

    // http://localhost:8080/userData/queryUserByName?name=feng
    @GetMapping("/queryUserByName")
    public List<UserDataResp> queryUserByName(@RequestParam("name") String name){
        List<UserDataResp> user = userMapper.queryUserDataByName(name);
        return user;
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserDataReq userDataReq){
        System.out.println("data:"+userDataReq.toString());
        userMapper.addUserData(userDataReq);
        return "添加成功";
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestBody UserDataUpdateReq userDataUpdateReq){

        userMapper.updateUser(userDataUpdateReq);
        return "更改成功";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(){
        userMapper.deleteUser(1);
        return "删除成功";
    }

//    性别维度 柱状图
    @PostMapping("/genderBar")
    public ResultData getGenderBarChart(@RequestBody UserDataReq userDataReq){
        List<GenderBarChartResp> genderBarChart = userMapper.getGenderBarChart(userDataReq);
        return new ResultData(200, "OK", genderBarChart);
    }

    //    生日维度 饼状图
    @PostMapping("/birthdayPie")
    public ResultData getBirthdayPieChart(@RequestBody UserDataReq userDataReq){
        ResultData resultData = null;
        Integer birthInterval = userDataReq.getBirthInterval();
        // 无时间间隔时
        if(birthInterval == null || birthInterval == 0){
            List<BirthdayPieChartResp> birthdayPieChart = userMapper.getBirthdayPieChart(userDataReq);
            resultData = new ResultData(200, "OK", birthdayPieChart);
        } else {
            // 有时间间隔
            Integer startBirth = userDataReq.getStartBirth();
            Integer endBirth = userDataReq.getEndBirth();
            int interval = endBirth - startBirth;

            // 返回的容器
            ArrayList<BirthdayPieChartResp> list = new ArrayList();

            // 如果 间隔小于起始生日差，则直接获取这个间隔的年龄
            if (interval < birthInterval) {
                int num = userMapper.getBirthdayIntervalPieChart(userDataReq);
                BirthdayPieChartResp resp = new BirthdayPieChartResp();
                resp.setBirthday(startBirth + "-" + endBirth); // 1990-2000
                resp.setNum(num);
                list.add(resp);
                resultData = new ResultData(200, "OK", list);
                return resultData;
            }
            // 间隔 大于 起始生日差，则 分段获取
            int count = interval / birthInterval;
            int yushu = interval % birthInterval;

            for (int i = 0; i < count; i++){
                UserDataReq user = new UserDataReq();
                BeanUtils.copyProperties(userDataReq, user);
                user.setStartBirth(startBirth);
                user.setEndBirth(startBirth + birthInterval);
                // 返回的list只有一个对象
                int num = userMapper.getBirthdayIntervalPieChart(user);
                BirthdayPieChartResp resp = new BirthdayPieChartResp();
                resp.setBirthday(user.getStartBirth() + "-" + user.getEndBirth());
                resp.setNum(num);
                list.add(resp);
                // 开始年 也得 加上 间隔。
                startBirth = startBirth + birthInterval;
            }
            // 最后的 余数 间隔 为一个数据。
            if (yushu != 0){
                UserDataReq user = new UserDataReq();
                BeanUtils.copyProperties(userDataReq, user);
                user.setStartBirth(startBirth);
                user.setEndBirth(startBirth + yushu);
                // 返回的list只有一个对象
                int num = userMapper.getBirthdayIntervalPieChart(user);
                BirthdayPieChartResp resp = new BirthdayPieChartResp();
                resp.setBirthday(startBirth + "-" + (startBirth + yushu));
                resp.setNum(num);
                list.add(resp);
            }
            resultData = new ResultData(200, "OK", list);
        }
        return resultData;
    }

    //    旅行时间 折线图
    @PostMapping("/travelTimeLine")
    public ResultData getTotalTravelTimeLineChart(@RequestBody UserDataReq userDataReq){
        List<TotalTravelTimeLineChartResp> totalTravelTimeLineChart = userMapper.getTotalTravelTimeLineChart(userDataReq);
        return new ResultData(200, "OK", totalTravelTimeLineChart);
    }
}
