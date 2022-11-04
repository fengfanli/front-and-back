package com.feng.user_manager.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BirthdayPieChartResp implements Serializable {

    private String birthday;
    private Integer num;
}
