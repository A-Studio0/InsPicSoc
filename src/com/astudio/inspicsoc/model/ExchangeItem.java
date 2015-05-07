/**
 * 
 */
package com.astudio.inspicsoc.model;

/**
 * @author CJH
 *
 */
public class ExchangeItem {

	private int itemId;

	private String userName;

	private String explain;

	private int picId;

	private int headpicId;

	public int getHeadpicId() {
		return headpicId;
	}

	public void setHeadpicId(int headpicId) {
		this.headpicId = headpicId;
	}

	public void setPicId(int picId) {
		this.picId = picId;
	}

	/**
	 * @param itemId
	 * @param userName
	 * @param explain
	 * @param pic
	 * @param point
	 */

	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 * @param userName
	 * @param explain
	 * @param picId
	 * @param headpicId
	 * @param point
	 */
	public ExchangeItem(int itemId, String userName, String explain, int picId,
			int headpicId) {
		super();
		this.itemId = itemId;
		this.userName = userName;
		this.explain = explain;
		this.picId = picId;
		this.headpicId = headpicId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public int getPicId() {
		return picId;
	}

	public void setPic(int picId) {
		this.picId = picId;
	}

	/**
	 * 
	 */
	public ExchangeItem() {
		// TODO Auto-generated constructor stub
	}

}
