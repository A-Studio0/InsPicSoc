/**
 * 
 */
package com.astudio.inspicsoc.model;

import java.util.Date;

/**
 * @author CJH 用户所有信息（不含密码） 用于用户查询显示
 */
public class UserDetailDto {

	private Integer userId;

	private String userName;

	private String userNickName;

	private Date birthDay;

	private String email;

	private String phone;

	private String realName;

	private int points;

	private String address;

	private Integer gender;

	private String personalGraph;

	private String headPic;

	private Integer type;

	public UserDetailDto() {
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPersonalGraph() {
		return personalGraph;
	}

	public void setPersonalGraph(String personalGraph) {
		this.personalGraph = personalGraph;
	}

}
