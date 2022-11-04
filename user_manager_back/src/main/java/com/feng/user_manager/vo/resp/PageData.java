package com.feng.user_manager.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageData {
    private Integer pageSize;
    private Integer pageNum;
    private Integer total;
}
