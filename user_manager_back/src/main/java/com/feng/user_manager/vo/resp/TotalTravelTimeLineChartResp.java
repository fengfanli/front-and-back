package com.feng.user_manager.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalTravelTimeLineChartResp implements Serializable {

    private Integer totalTravelTime;
    private Integer num;
}
