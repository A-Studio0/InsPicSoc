/**
 * 
 */
package com.astudio.inspicsoc.model;

/**
 * @author CJH 用户基本信息 用于某用户显示其关注好友基本信息
 */
public class UserDto {

	// 用户id
	private String userId;

	// 账号
	private String userName;

	// 备注名
	private String RemarkSName;

	// 用户头像
	private String HeadPic;

	private String userNickName;

	/*
	 * 关注状态 1，相互关注，2，关注
	 */
	private String ConcernState;

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemarkSName() {
		return RemarkSName;
	}

	public void setRemarkSName(String remarkSName) {
		RemarkSName = remarkSName;
	}

	public String getHeadPic() {
		return HeadPic;
	}

	public void setHeadPic(String headPic) {
		HeadPic = headPic;
	}

	public String getConcernState() {
		return ConcernState;
	}

	public void setConcernState(String concernState) {
		ConcernState = concernState;
	}

}
