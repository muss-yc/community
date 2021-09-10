package com.mousse.dto;

import lombok.Data;

import java.util.List;

/**
 * @author mousse
 * @data 2021/9/10
 */
@Data
public class TagDTO {

    private String categoryName;
    private List<String> tags;

}
