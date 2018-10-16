package cn.beiminzhiyu.dao;

import cn.beiminzhiyu.entity.UserEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @Descption
 * @Author likun
 * @Date 2018/10/7 21:35
 */
@Mapper
public interface UserDao {

    @Select("select id,username,password,phone,email,created,updated,open_id from mb_user where id =#{userId}")
    UserEntity findByID(@Param("userId") Long userId);

    @Insert("INSERT  INTO `mb_user` (username,password,phone,email,created,updated,open_id) VALUES (#{username}, #{password},#{phone},#{email},#{created},#{updated},#{openId});")
    Integer insertUser(UserEntity userEntity);

    @Select("select id,username,password,phone,email,open_id,created,updated from mb_user where username =#{username} and password=#{password}")
    UserEntity login(@Param("username") String userName, @Param("password") String password);

    @Select("select id,username,password,phone,email,open_id,created,updated from mb_user where open_id =#{openId}")
    UserEntity findUserByOpenId(@Param("openId") String openId);

    @Update("update mb_user set openId = #{openId} where id= #{id}")
    Integer modifyOpenId(@Param("openId") String openId ,@Param("id") Integer id);

}
