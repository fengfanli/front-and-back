package com.feng.user_manager.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataReq implements Serializable {
    private Integer startBirth;
    private Integer endBirth;
    private Integer startRoad;
    private Integer endRoad;
    private Integer startTime;
    private Integer endTime;

    // 只要 饼状图 自定义间隔使用
    private Integer birthInterval;

    private Integer pageNum;
    private Integer pageSize;

}
