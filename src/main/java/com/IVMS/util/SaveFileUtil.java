package com.IVMS.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class SaveFileUtil {
	/**
	 * 保存文件并得到路径
	 * @param checkingToolFiles
	 * @param request
	 * @return 文件保存路径
	 * @throws Exception
	 */
	public String saveFile(MultipartFile checkingToolFiles,HttpServletRequest request) throws Exception{
		String path=null;
		if(checkingToolFiles!=null){
			String filename = checkingToolFiles.getOriginalFilename();//获取上传的文件名称
			if(filename!=null && !filename.isEmpty()){
				String root=request.getSession().getServletContext().getRealPath("/checkingtoolfile/");
				System.out.println("root:"+root);
				int index = filename.lastIndexOf("\\");
				if(index != -1) {
					filename = filename.substring(index+1);
				}
				int hCode = filename.hashCode();
				String hex = Integer.toHexString(hCode);
				if(!hex.equals("0")){
					File dirFile = new File(root, hex.charAt(0) + "/" + hex.charAt(1));
					dirFile.mkdirs();
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
  	 	            String time=sdf.format(new Date());
  	 	            filename=time+"_"+filename;
					File destFile = new File(dirFile, filename);
					checkingToolFiles.transferTo(destFile);
					path="/"+hex.charAt(0) + "/" + hex.charAt(1)+"/"+filename;//数据库存的路径
					System.out.println("path:"+path);
				}
			}
		}
		return path;
	}
}
