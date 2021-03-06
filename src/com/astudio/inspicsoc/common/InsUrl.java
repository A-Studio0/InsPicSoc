/**
 * 
 */
package com.astudio.inspicsoc.common;

/**
 * @author CJH
 *
 */
public class InsUrl {

	/**
	 * 
	 */

	public static final String BASE_URL = "http://192.168.253.1:8080";

	public static final String USER_BASE = BASE_URL + "/user";

	public static final String USER_LOGIN = USER_BASE
			+ "/login.json?username=@un&password=@ps";

	public static final String USER_REGISTER = USER_BASE
			+ "/adduser.json?username=@un&password=@ps";

	public static final String GET_MSG_BASE = BASE_URL + "/msg";

	public static final String GET_FIRSTPAGE_MSG = GET_MSG_BASE
			+ "/firstpage.json";

	public static final String GET_USER_DETAIL = USER_BASE
			+ "/userdetail.json?username=@un";

	public static final String GET_IMAGE_REQ = BASE_URL
			+ "/image/showImage.json?imagePath=@path";

	public static final String GET_HEADPIC = USER_BASE
			+ "/userheadpic.json?username=@un";

	public static final String UPLOAD_IMAGE_BASE = BASE_URL
			+ "/image/uploadImage.json";

	public static final String ADD_MSG = GET_MSG_BASE + "/addmsg.json";

	public static final String GET_MSG_DETAIL = GET_MSG_BASE
			+ "/getmsgdetail.json?username=@un&msgId=@mi";

	public static final String CHANGE_USER_HEADPIC = USER_BASE
			+ "/changeHeadpic.json";

	public static final String GET_FRIENDSLIST = USER_BASE
			+ "/getFriendsList.json?username=@un";

	public static final String ADD_CONCERN = USER_BASE
			+ "/addConcern.json?username=@un&consernName=@cn";

	public static final String GET_USERMSGLIST = GET_MSG_BASE
			+ "/getUsermsgList.json?username=@un";
}
