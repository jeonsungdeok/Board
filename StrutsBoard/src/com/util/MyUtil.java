package com.util;

public class MyUtil {

	//��ü������                  �� 34p          3p
	public int getPageCount(int numPerPage,int dataCount) {

		int pageCount = 0;

		pageCount = dataCount / numPerPage;

		if(dataCount%numPerPage !=0) {  //���� �� �������� ������ �������� ������
			pageCount++;  //������ �ϳ� �� �����
		}
		return pageCount;

	}

	//������ ó�� �޼ҵ�              
	public String pageIndexList(int currentPage,int totalPage,String listUrl) {

		int numPerBlock=5;  //������ 6 7 8 9 10 ������6~10���� ����
		int currentPageSetup;  // �� 
		int page; // 6 7 8 9 10 

		StringBuffer sb = new StringBuffer();  //String�� ���� ���� 

		if(currentPage==0 || totalPage==0) {
			return "";
		}

		//list.jsp?searchKey=subject&searchValue=aa&pageNum=2
		if (listUrl.indexOf("?")!=-1) { //����ǥ�� ������ 
			//list.jsp �길 ���� ��� 
			listUrl = listUrl + "&";  //list.jsp&
		}else {
			listUrl = listUrl +"?";
		}

		// 1 2 3 4 5 ������
		//������ 6 7 8 9 10 ������
		//������ 11 12 13 14 15 ������
		//  14p     /    5              5
		currentPageSetup = (currentPage/numPerBlock)*numPerBlock;
		//�ڱ� �ڽ��� ���õ� �� �˻� 
		if (currentPage % numPerBlock==0) {
			//���������� 5 �Ǵ� 10�� currentPageSetup
			currentPageSetup = currentPageSetup - numPerBlock;
		}  //������ �𸣰����� ���� �������� ���� ���� ���ϴ� �����̾��ٰ� �Ѵ�

		//������
		if (totalPage > numPerBlock && currentPageSetup > 0) {

			sb.append("<a href=\""+listUrl+"pageNum="
					+currentPageSetup+ "\">������</a>&nbsp;");
			//<a href="list.jsp?pageNum=5>������</a>" �̰� ����� ���� 		
		}

		//�ٷΰ��� ������ : 6 7 8 9 10 ����� 
		page = currentPageSetup + 1; //���۰� 5p
		//     6   <=   12     &&  6   <= 5+5(10���� ���� �Ǵϱ�) 
		while(page <=totalPage && page <=(currentPageSetup+numPerBlock)) {
			//���� ������ ���� �ٲٱ� 
			if(page == currentPage) {
				sb.append("<font color=\"Fuchsia\">"+page+ "</font>&nbsp;");
				//<font color = "Fuchsia">9</font>&nbsp; �̰� ���� ���� 

			}else {
				sb.append("<a href=\""+listUrl+"pageNum="+page+
						"\">"+page+"</a>&nbsp;");
				//<a href="list.jsp?pageNum=10">7</a>&nbsp;
			}
			page++;
		}

		//������
		//������ 6 7 8 9 10 ������
		//������ 11 12 
		//      12   -   5   >   
		if (totalPage - currentPageSetup > numPerBlock) {

			sb.append("<a href=\""+listUrl+"pageNum="+page+"\">������</a>&nbsp;");
			//<a href="list.jsp?pageNum=11">������</a>&nbsp;
		}
		return sb.toString();  //sb�� ����ȭ�ؼ� ��ȯ�ض� 


	}

	//�ڹٽ�ũ��Ʈ�� ����¡ ó��
		public String pageIndexList(int currentPage, int totalPage) {
			
			int numPerBlock = 5;
			int currentPageSetup;
			
			int page;
			String strList = "";
			
			if(currentPage==0) {
				return "";
			}
			
			//ǥ���� ù������
			currentPageSetup = (currentPage/numPerBlock)*numPerBlock;
			if(currentPage % numPerBlock == 0) {
				currentPageSetup = currentPageSetup - numPerBlock;
			}
			
			//������
			if(totalPage>numPerBlock && currentPageSetup>0) {
				
				strList = "<a onclick='listPage(" 
						+ currentPageSetup + ");'>������</a>&nbsp;";
				
			}
			
			//������
			page = currentPageSetup + 1;
			
			while((page<=totalPage) && (page<=currentPageSetup + numPerBlock)) {
				
				if(page==currentPage) {
					strList += "<font color='Fuchsia'>" + page + "</font>&nbsp;";
				}else {
					strList += "<a onclick='listPage(" + page + ");'>" +
								page + "</a>&nbsp;";
				}
				
				page++;
				
			}
			
			//������
			if(totalPage-currentPageSetup>numPerBlock) {
				strList += "<a onclick='listPage(" + page + ");'>������</a>&nbsp;";
			}
			
			return strList;
			
		}
		

	}

