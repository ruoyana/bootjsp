package com.mycs.dao;



import com.mycs.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserMapper {



	public List<User> test();

	@Select("select * from users limit #{pageNo},#{pageSize}")
	public List<User> queryUser(@Param("pageNo") int pageNo, @Param("pageSize")int pageSize);

	@Select("select count(*) from users")
	public int sums(int pageSize);

	@Select("select count(*) from users")  //获取到所有数据总数
	public int qushangye(@Param("pageNo")int pageNo,@Param("pageSize")int pageSize);

	@Delete("delete FROM  users where id = #{id}")
	public void delete(int id);

	@Insert("insert into users(name,sex,addr,password,birth,head,grs) value( #{name},#{sex},#{addr},#{password},#{birth},#{head},#{grs} )")
	public void add(User user);

	@Select("select * from users where id = #{id}")
	 public User queryid(int id);

	 @Update("update users set  name=#{name},sex=#{sex},addr=#{addr},password=#{password},birth=#{birth},head=#{head},grs=#{grs} where id=#{id}")
	 public void update(User user);

//	 @Select()
//	 public List<User> querysy();



}
