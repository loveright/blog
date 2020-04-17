package com.by.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogAboutSearch {
    private String title;
    private Long typeId;
    private Boolean recommend;
    private Integer r;

    public Integer getR(){
       return r = recommend ? 1:0;
    }

}
