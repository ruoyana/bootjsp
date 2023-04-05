package com.mycs.entity;


import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Table(name = "users")
public class User {
	
	 
	private int id;

	@NotEmpty
	private String name;
	private String addr;
	private String sex;

//	@NotEmpty
	private String head;
	private String money;

	private String grs;

	public String getGrs() {
		return grs;
	}

	public void setGrs(String grs) {
		this.grs = grs;
	}

	public int getId() {
		return id; 
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	
	
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
 
	
	
 

	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}





	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birth;
 

	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	private String password;


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", addr='" + addr + '\'' +
				", sex='" + sex + '\'' +
				", head='" + head + '\'' +
				", money='" + money + '\'' +
				", grs='" + grs + '\'' +
				", birth=" + birth +
				", password='" + password + '\'' +
				'}';
	}
}
