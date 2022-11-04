package com.feng.user_manager.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Integer id;
    private Integer gender;
    private Integer birthday;
    private Integer total_travel_road;
    private Integer total_travel_time;
}
