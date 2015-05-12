/**
 * 
 */
package com.astudio.inspicsoc.model;

/**
 * @author CJH 用户所有信息（不含密码） 用于用户查询显示
 */
public class UserDetail {

	private Integer UserId;

	private String UserName;

	private String UserNickName;

	private String BirthDay;

	private String Email;

	private String Phone;

	private String RealName;

	private int Points;

	private String Address;

	private String Gender;

	private String PersonalGraph;

	private String HeadPic;

	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHeadPic() {
		return HeadPic;
	}

	public void setHeadPic(String headPic) {
		HeadPic = headPic;
	}

	public Integer getUserId() {
		return UserId;
	}

	public void setUserId(Integer userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserNickName() {
		return UserNickName;
	}

	public void setUserNickName(String userNickName) {
		UserNickName = userNickName;
	}

	public String getBirthDay() {
		return BirthDay;
	}

	public void setBirthDay(String birthDay) {
		this.BirthDay = birthDay;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getRealName() {
		return RealName;
	}

	public void setRealName(String realName) {
		RealName = realName;
	}

	public int getPoints() {
		return Points;
	}

	public void setPoints(int points) {
		this.Points = points;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getPersonalGraph() {
		return PersonalGraph;
	}

	public void setPersonalGraph(String personalGraph) {
		PersonalGraph = personalGraph;
	}

}
