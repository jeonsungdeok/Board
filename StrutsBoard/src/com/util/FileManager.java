package com.util;

import java.io.BufferedInputStream;  //���̹�Ƽ�� 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

public class FileManager {
	
	//���� ���ε� 
	public static String doFileUpload(File file, String originalFileName, String path) throws IOException {
		
		String newFileName = null;
		
		if(file==null) {
			return null;
		}
		
		if(originalFileName.equals("")) {
			return null;
		}
		
		//Ȯ���� ����(abc.txt) ������ ������ �����ؼ� �� 
		String fileExt = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		if(fileExt==null || fileExt.equals("")) {
			return null;
		}
		
		//������ ������ ���ο� ���� �̸� ����
		newFileName = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		newFileName += System.nanoTime(); //10�� -9��
		newFileName += fileExt;
		
		//���� ���ε�
		File f = new File(path);
		
		if(!f.exists()) {  //������
			f.mkdirs();   //������ 
		}
		
		String fullFilePath = path + File.separator + newFileName;
		
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(fullFilePath);
		
		int data = 0;
		byte[] buffer = new byte[1024];
		
		while((data=fis.read(buffer, 0, 1024))!=-1) {
			
			fos.write(buffer, 0, data);
		}
		fis.close();
		fos.close();
		
		return newFileName;
	}
	
	
	//���� �ٿ�ε�                               // ���� �ٿ� �� �ʼ� 
	public static boolean doFileDownload(HttpServletResponse response,
			String saveFileName, String originalFileName, String path) {
		
		try {
			
			String filepath =  path + File.separator + saveFileName;
			
			if (originalFileName==null ||  originalFileName.equals("")) {
				
				originalFileName = saveFileName; 
			}
			
			// ������ �ٿ�޾� Ŭ���̾�Ʈ �Ŀ� ������ �� �ѱ� �̸� ���� ����
			originalFileName = 
					new String(originalFileName.getBytes("euc-kr"),"ISO-8859-1"); // 8859_1 �� ���� ���� ���� 
			
			File f = new File(filepath);
			
			if(!f.exists()) {
				return false;
			}
			
			response.setContentType("application/octet-stream"); //�������� �⺻ ǥ������ ����(.)���� ������ �������� �˷��ִ� �� 
			response.setHeader("Content-disposition", 
					"attachment;fileName="+originalFileName);
			
			//������ �о� ���� �� 
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(f));
			
			OutputStream out = response.getOutputStream();
			
			int data;
			byte [] bytes = new byte[4096];
			while((data=bis.read(bytes,0, 4096))!=-1) {
				out.write(bytes,0,data);
			}
			
			out.flush();
			out.close();
			bis.close();
			
		} catch (Exception e) {
			System.err.println(e.toString());
			return false;
		}
		return true;
		
		
		
		
	}

	
	
	//���� ���� 
	public static void doFileDelete(String fileName,String path) {
		
		try {
			
			String filePath = path + File.separator + fileName;
			
			File f = new File(filePath);
			
			if(f.exists())
				f.delete(); //�������� ���� ���� 
			
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	}
	
	
}
