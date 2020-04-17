package com.by.vo;

import com.by.po.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogQuery {
    private Long id;
    private String title;
    private Boolean recommend;
    private Boolean published;
    private Date updateTime;
    private Type type;
    private Long typeId;
    public String getUpdateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(updateTime);
        return dateString;
    }
}
