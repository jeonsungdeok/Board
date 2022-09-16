package com.test2;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.test2.UserDTO;

public class TestAction extends ActionSupport implements Preparable, ModelDriven<UserDTO>{

	private static final long serialVersionUID = 1L;

	private UserDTO dto;
	
	public UserDTO getDto() {  //���͸� ���� 
		return dto;           // ModelDriven �갡 �־ request.setAttribute("dto",dto); ��� ���� 
	}

	@Override
	public UserDTO getModel() {
		return dto;
	}

	@Override
	public void prepare() throws Exception {  // ������� ������ �׻� ����� ��
		dto = new UserDTO();
		
	}

	public String created() throws Exception{
		
		if(dto==null||dto.getMode()==null||dto.getMode().equals("")) {
			
			return INPUT; 
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		request.setAttribute("message", "��Ʈ����2");
		
		//request.setAttribute("dto", dto);
		return SUCCESS;
	}
	
}
