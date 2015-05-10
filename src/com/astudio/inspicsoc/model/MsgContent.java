/**
 * 
 */
package com.astudio.inspicsoc.model;

import java.util.List;

/**
 * @author CJH 消息内容 消息实体类
 */
public class MsgContent {

	private int msgId;

	private String msgText;

	private List<PictureBean> pics;

	private String voiceUrl;

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	public String getMsgText() {
		return msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	public List<PictureBean> getPics() {
		return pics;
	}

	public void setPics(List<PictureBean> pics) {
		this.pics = pics;
	}

}
