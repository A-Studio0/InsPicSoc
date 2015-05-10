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

}
