package com.jdbc.springweb;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.jdbc.dao.BoardDAO2;
import com.jdbc.dto.BoardDTO;
import com.jdbc.util.MyUtil;

@Controller
public class BoardController {
	
	@Autowired
	@Qualifier("boardDAO2") //�Ƚᵵ ������ ��������� ��������.
	BoardDAO2 dao; 
	
	@Autowired //�ڵ����� ������ �����ؼ� ������ ��ü����
	MyUtil myUtil; 
	
	//http://localhost:8080/springweb/
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String home() {
		
		return "index";
	}
	
	/*
	@RequestMapping(value = "/created.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public String created(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		return "bbs/created";
		
	}
	*/
	
	@RequestMapping(value = "/created.action") //method = {RequestMethod.GET,RequestMethod.POST}) �Ⱦ��� �⺻�� �ٹ��
	public ModelAndView created() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("bbs/created");
		
		return mav;
	}
	
	@RequestMapping(value = "/created_ok.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public String created_ok(BoardDTO dto,HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		int maxNum = dao.getMaxNum();
		
		dto.setNum(maxNum + 1);
		dto.setIpAddr(request.getRemoteAddr());
		
		dao.insertData(dto);
		
		return "redirect:/list.action";
	}
	/*
	@RequestMapping(value = "/list.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public String list(BoardDTO dto,HttpServletRequest request, HttpServletResponse response) throws Exception{
	*/	
	
	@RequestMapping(value = "/list.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView list(BoardDTO dto,HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		String cp = request.getContextPath();
		
		String pageNum = request.getParameter("pageNum");
		
		int currentPage = 1;
		
		if(pageNum!=null)
			currentPage = Integer.parseInt(pageNum);
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if(searchValue==null) {
			searchKey = "subject";
			searchValue = "";
		}else {
			if(request.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
		}
		
		int dataCount = dao.getDataCount(searchKey, searchValue);
		
		int numPerPage = 5;
		int totalPage = myUtil.getPageCount(numPerPage, dataCount);
		
		if(currentPage>totalPage)
			currentPage = totalPage;
		
		int start = (currentPage-1)*numPerPage+1; // 1 6 11 16
		int end = currentPage*numPerPage;
		
		List<BoardDTO> lists = dao.getLists(start, end, searchKey, searchValue);
		
		String param = "";
		
		if(searchValue!=null&&!searchValue.equals("")) { //���� ã�Ƴ��� ���ϴ°�찡 �ֱ⶧���� ���ʿ� �������� ���ش�.
			param = "searchKey=" + searchKey;
			param+= "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
		}
		
		String listUrl = cp + "/list.action";
		
		if(!param.equals("")) {
			listUrl += "?" + param;
		}

		String pageIndexList = myUtil.pageIndexList(currentPage, totalPage, listUrl);
		
		String articleUrl = cp + "/article.action?pageNum=" + currentPage;
		
		if(!param.equals("")) {
			articleUrl += "&" + param;
		}
		
		/*
		request.setAttribute("lists", lists);
		request.setAttribute("articleUrl", articleUrl);
		request.setAttribute("pageIndexList", pageIndexList);
		request.setAttribute("dataCount", dataCount);
		
		return "bbs/list";
		*/
		//ModelAndView�� ����
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("lists", lists);
		mav.addObject("articleUrl", articleUrl);
		mav.addObject("pageIndexList", pageIndexList);
		mav.addObject("dataCount", dataCount);
		
		mav.setViewName("bbs/list");  
		
		return mav;
	}
	
	@RequestMapping(value = "/article.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public String article(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		String cp = request.getContextPath();
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
	
		if(searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		}
		
		dao.updateHitCount(num);
		
		BoardDTO dto = dao.getReadData(num);
		
		if(dto==null) {
			return "redirect:/list.action"; //�𵨾غ信�� �� ��� �� ��
		}
		
		int lineSu = dto.getContent().split("\n").length;
		
		dto.setContent(dto.getContent().replace("\n", "<br/>"));
		
		String param = "pageNum=" + pageNum;
		if(searchValue!=null&&!searchValue.equals("")) { //�˻��� �ߴٴ¶�
			
			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
	}
		
		request.setAttribute("dto", dto);
		request.setAttribute("params", param);
		request.setAttribute("lineSu", lineSu);
		request.setAttribute("pageNum", pageNum);
		
			return "bbs/article";
		
		
	}
	
	@RequestMapping(value = "/updated.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public String updated(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		String cp = request.getContextPath();
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");
		
		if (searchValue!=null) {
			searchValue = URLDecoder.decode(searchValue,"UTF-8");
		}
		
		BoardDTO dto = dao.getReadData(num);
		
		if(dto==null) {
			return "redirect:/list.action";
		}
		
		String param = "pageNum="+pageNum;
		if (searchValue!=null&&!searchValue.equals("")) {
			param += "&searchKey=" +searchKey;
			param += "&searchValue=" +URLEncoder.encode(searchValue,"UTF-8");
		}
		
		request.setAttribute("dto",dto);
		request.setAttribute("pageNum",pageNum);
		request.setAttribute("params",param);
		request.setAttribute("searchKey",searchKey);
		request.setAttribute("searchValue",searchValue);
		
		return "bbs/updated";
	}

	@RequestMapping(value = "/updated_ok.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public String updated_ok(HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		System.out.println("_ok�� �Դ�");

		//String cp = request.getContextPath();
		//String url;

		String pageNum = request.getParameter("pageNum");

		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");

		BoardDTO dto = new BoardDTO();

		dto.setNum(Integer.parseInt(request.getParameter("num")));
		dto.setSubject(request.getParameter("subject"));
		dto.setName(request.getParameter("name"));
		dto.setEmail(request.getParameter("email"));
		dto.setPwd(request.getParameter("pwd")); 
		dto.setContent(request.getParameter("content"));

		dao.updateData(dto);

		String param = "?pageNum=" + pageNum;

		if(searchValue!=null&&!searchValue.equals("")) {

			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + 
					URLEncoder.encode(searchValue, "UTF-8");

		}

		return "redirect:/list.action" + param;

	}

	@RequestMapping(value = "/deleted_ok.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public String deleted_ok(HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");

		String searchKey = request.getParameter("searchKey");
		String searchValue = request.getParameter("searchValue");

		dao.deleteData(num);

		String param = "?pageNum=" + pageNum;

		if(searchValue!=null&&!searchValue.equals("")) {

			param += "&searchKey=" + searchKey;
			param += "&searchValue=" + 
					URLEncoder.encode(searchValue, "UTF-8");

		}

		return "redirect:/list.action" + param;
	}
	
}
