package com.feng.user_manager.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataResp implements Serializable {
    private Integer id;
    private Integer gender;
    private Integer birthday;
    private Integer total_travel_road;
    private Integer total_travel_time;

}
