package com.test1;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private UserDTO dto; //�̷��� userId, userName�� ������ [ Domain Object ]
	
	private String message;

	public UserDTO getDto() { //�̷��� userId, userName�� ������
		return dto;
	}

	public void setDto(UserDTO dto) { //�̷��� userId, userName�� ������
		this.dto = dto;
	}

	public String getMessage() {
		return message;
	} 
	
	public String execute() throws Exception{
		
		message = dto.getUserName() + "�� �氡�氡";
		
		return "ok"; //����� ���� ���ڸ� �ᵵ �� 
	}
	
}
