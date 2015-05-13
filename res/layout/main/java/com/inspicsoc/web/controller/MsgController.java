/**
 * 
 */
package com.inspicsoc.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.inspicsoc.common.Constant;
import com.inspicsoc.common.core.util.ImageUtil;
import com.inspicsoc.common.core.util.InspicXmlUtil;
import com.inspicsoc.common.core.util.MsgContentXmlUtil;
import com.inspicsoc.common.domain.Msg;
import com.inspicsoc.common.dto.MsgDto;
import com.inspicsoc.common.dto.PictureBean;
import com.inspicsoc.common.enums.LabelEnum;
import com.inspicsoc.common.service.MsgService;
import com.inspicsoc.common.service.UserService;

/**
 * @author CJH 分享信息控制器
 */

@Controller
@RequestMapping("/msg")
public class MsgController {

	@Autowired
	UserService userService;

	@Autowired
	MsgService msgService;
	
	@RequestMapping(value = "/addmsg.json", method = RequestMethod.POST)
	@ResponseBody
	public String AddMsg(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException,
			IOException {

		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据，
		System.out.println("UploadingMsg");

		if (multipartResolver.isMultipart(request)) {
			System.out.println("xxxxxxxxxxxxx");
			// 再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			String userName = multiRequest.getParameter("username");

			
			if (null != userName) {

				System.out.println("xxxxxxxxxxxxx");
				ImageUtil im = new ImageUtil();

				String content = multiRequest.getParameter("content");

				String locationX = multiRequest.getParameter("locationx");

				String locationY = multiRequest.getParameter("locationy");

				String locationName = multiRequest.getParameter("locationName");

				MsgDto msgDto = new MsgDto();

				Msg msg=new Msg();
				
				if (content != null) {
					msgDto.setContent(content);
					
				}
				if (locationX != null && locationY != null
						&& locationName != null) {
					
					msgDto.setLocationX(Long.getLong(locationX));
					msgDto.setLocationY(Long.getLong(locationY));
					msgDto.setLocationName(locationName);
					msg.setLocationX(msgDto.getLocationX());
					msg.setLocationY(msgDto.getLocationX());
					msg.setLocationName(locationName);
					
				}

				Date date = new Date();
				msgDto.setCommentsNum(0);
				msg.setCommentsNum(0);
				msgDto.setTime(date);
				msg.setTime(date);
				
				msgDto.setLabelId(LabelEnum.FUNNY.getId());
				msg.setLabelId(LabelEnum.FUNNY.getId());
				
				
				msgDto.setUserName(userName);
				msg.setUserName(userName);
				
				msgDto.setUserId(userService.FindUserByUserName(userName)
						.getId());

				msg.setUserId(msgDto.getUserId());
				
				List<String> pics = new ArrayList<String>();

				List<String> smallpics = new ArrayList<String>();

				Iterator iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					System.out.println("xxxxxxxxxxxxx");
					MultipartFile file = multiRequest.getFile((String) iter
							.next());
					if (file != null) {
						String type = file.getName();
						System.out.println(type);
						String fileName = file.getOriginalFilename();
						if (StringUtils.equals(type, "voice")) {
							msgDto.setVoice(fileName);
							File Basedir=new File(Constant.VOICE_BASE_PATH
									+ "/" + userName);
							if(!Basedir.exists()){
								Basedir.mkdir();
							}
							File localFile = new File(Constant.VOICE_BASE_PATH
									+ "/" + userName + "/" + fileName);
							file.transferTo(localFile);
							System.out.println(fileName + "============"
									+ file.getName());
							continue;
						} else if (StringUtils.equals(type, "image")) {
							File Basedir=new File(Constant.IMAGE_BASE_PATH
									+ "/" + userName);
							if(!Basedir.exists()){
								Basedir.mkdir();
							}
							File localFile = new File(Constant.IMAGE_BASE_PATH
									+ "/" + userName + "/" + fileName);
							file.transferTo(localFile);
							pics.add(localFile.getAbsolutePath());

							File smallfile = im.getSmallImage(localFile
									.getAbsolutePath());

							System.out.println(fileName + "============"
									+ file.getName());
							File smallLocalFile = new File(
									Constant.IMAGE_BASE_PATH + "/" + userName
											+ "/" + smallfile.getName());
							smallpics.add(smallLocalFile.getAbsolutePath());
						}

					}
					
				};
				
				if(!pics.isEmpty()&&!smallpics.isEmpty()&&pics.size()==smallpics.size()){
					msgDto.setPics(pics);
					msgDto.setSmallpics(smallpics);
					String path=InspicXmlUtil.CreateMsgContentXML(msgDto);
					if(path!=null){
						msg.setContent(path);
						if(msgService.CreateMsg(msg))
							return Constant.SUCCESS;
					}
				}
				return Constant.FAIL;
			}
		}
		return Constant.FAIL;
	}
	
	
	@RequestMapping(value = "/getmsgdetail.json", method = RequestMethod.POST)
	@ResponseBody
	public String GetMsgDetail(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException,
			IOException, DocumentException {
		System.out.println("getMsg");

		String userName=request.getParameter("username");
		int msgId=Integer.parseInt(request.getParameter("msgId"));
		
		Msg msg=msgService.FindMsgByid(msgId);
		if(msg!=null){
			String userNickName=userService.FindUserByUserName(userName).getUserNickName();
			MsgDto msgdto=MsgContentXmlUtil.writeXml2Msg(msg.getContent());
		
			if(msgdto==null){
				return Constant.FAIL;
			}
			
			msgdto.setUserName(userName);
			msgdto.setUserNickName(userNickName);
			
			Gson gson=new Gson();
			return gson.toJson(msgdto);
		}
		
		return Constant.FAIL;
	}
	
	@RequestMapping(value = "/getUsermsgList.json", method = RequestMethod.POST)
	@ResponseBody
	public String GetMsgList(HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException,
			IOException, DocumentException {
		System.out.println("getMsgList");

		String userName=request.getParameter("username");
		List<Msg> lists=msgService.FindMsgByUserName(userName);
		
		if(lists.isEmpty())
			return Constant.EMPTY;
		
		List<MsgDto> msgList=new ArrayList<MsgDto>();
		for(Msg t:lists){
			MsgDto msgdto=MsgContentXmlUtil.writeXml2Msg(t.getContent());
			if(msgdto==null)
				continue;
			msgList.add(msgdto);
		}
		Gson gson=new Gson();
		return gson.toJson(msgList);
		
	}
	
}
