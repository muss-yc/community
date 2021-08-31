package com.mousse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mousse
 * @data 2021/8/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private int parentId;
    private String content;
    private Integer type;

}
