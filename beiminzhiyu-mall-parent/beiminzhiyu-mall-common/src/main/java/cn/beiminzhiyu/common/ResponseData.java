package cn.beiminzhiyu.common;

import lombok.*;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 15:14
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

    private Integer code;
    private String msg;
    private Object data;


}
