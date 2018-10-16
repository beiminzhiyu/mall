package cn.beiminzhiyu.entity;

import lombok.*;

import java.util.Date;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 21:29
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Date created;
    private Date updated;
    private String openId;
}
