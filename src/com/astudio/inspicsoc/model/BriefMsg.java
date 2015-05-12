/**
 * 
 */
package com.astudio.inspicsoc.model;

/**
 * @author CJH 简略消息 用于首页和用户关注等需要缩略信息的页面显示，只显示用户名评论数以及缩略图和用户头像 文字内容
 */
public class BriefMsg {

	private String msgId;
	private String userName;
	private String headPic;
	private String smallfirstPic;
	private String text;
	private int commentNum;
	private String userNickName;

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getSmallfirstPic() {
		return smallfirstPic;
	}

	public void setSmallfirstPic(String smallfirstPic) {
		this.smallfirstPic = smallfirstPic;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

}
