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
        return "????????????";
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestBody UserDataUpdateReq userDataUpdateReq){

        userMapper.updateUser(userDataUpdateReq);
        return "????????????";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(){
        userMapper.deleteUser(1);
        return "????????????";
    }

//    ???????????? ?????????
    @PostMapping("/genderBar")
    public ResultData getGenderBarChart(@RequestBody UserDataReq userDataReq){
        List<GenderBarChartResp> genderBarChart = userMapper.getGenderBarChart(userDataReq);
        return new ResultData(200, "OK", genderBarChart);
    }

    //    ???????????? ?????????
    @PostMapping("/birthdayPie")
    public ResultData getBirthdayPieChart(@RequestBody UserDataReq userDataReq){
        ResultData resultData = null;
        Integer birthInterval = userDataReq.getBirthInterval();
        // ??????????????????
        if(birthInterval == null || birthInterval == 0){
            List<BirthdayPieChartResp> birthdayPieChart = userMapper.getBirthdayPieChart(userDataReq);
            resultData = new ResultData(200, "OK", birthdayPieChart);
        } else {
            // ???????????????
            Integer startBirth = userDataReq.getStartBirth();
            Integer endBirth = userDataReq.getEndBirth();
            int interval = endBirth - startBirth;

            // ???????????????
            ArrayList<BirthdayPieChartResp> list = new ArrayList();

            // ?????? ??????????????????????????????????????????????????????????????????
            if (interval < birthInterval) {
                int num = userMapper.getBirthdayIntervalPieChart(userDataReq);
                BirthdayPieChartResp resp = new BirthdayPieChartResp();
                resp.setBirthday(startBirth + "-" + endBirth); // 1990-2000
                resp.setNum(num);
                list.add(resp);
                resultData = new ResultData(200, "OK", list);
                return resultData;
            }
            // ?????? ?????? ????????????????????? ????????????
            int count = interval / birthInterval;
            int yushu = interval % birthInterval;

            for (int i = 0; i < count; i++){
                UserDataReq user = new UserDataReq();
                BeanUtils.copyProperties(userDataReq, user);
                user.setStartBirth(startBirth);
                user.setEndBirth(startBirth + birthInterval);
                // ?????????list??????????????????
                int num = userMapper.getBirthdayIntervalPieChart(user);
                BirthdayPieChartResp resp = new BirthdayPieChartResp();
                resp.setBirthday(user.getStartBirth() + "-" + user.getEndBirth());
                resp.setNum(num);
                list.add(resp);
                // ????????? ?????? ?????? ?????????
                startBirth = startBirth + birthInterval;
            }
            // ????????? ?????? ?????? ??????????????????
            if (yushu != 0){
                UserDataReq user = new UserDataReq();
                BeanUtils.copyProperties(userDataReq, user);
                user.setStartBirth(startBirth);
                user.setEndBirth(startBirth + yushu);
                // ?????????list??????????????????
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

    //    ???????????? ?????????
    @PostMapping("/travelTimeLine")
    public ResultData getTotalTravelTimeLineChart(@RequestBody UserDataReq userDataReq){
        List<TotalTravelTimeLineChartResp> totalTravelTimeLineChart = userMapper.getTotalTravelTimeLineChart(userDataReq);
        return new ResultData(200, "OK", totalTravelTimeLineChart);
    }
}
