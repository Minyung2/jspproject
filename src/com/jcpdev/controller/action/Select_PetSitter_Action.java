package controller.action;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PetsitterDao;
import dto.AdopttimeDto;
import dto.AdopttimeDto_second;
import dto.PageDto;
import dto.PetsitterDto;


public class Select_PetSitter_Action implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ActionForward forward = new ActionForward();
		//비지니스 로직을 처리하는 jsp 파일
		PetsitterDao cdao = PetsitterDao.getInstance();
		//페이지 번호는 파라미터로 전달됩니다.
		request.setCharacterEncoding("UTF-8");
		String m_addr = request.getParameter("m_addr");
		String wdate_start = request.getParameter("wdate_start");
		String wdate_final = request.getParameter("wdate_final");
		String terms = Arrays.toString(request.getParameterValues("terms"));
		terms = terms.substring(1, terms.length()-1);

		request.setAttribute("s_date", wdate_start);
		request.setAttribute("f_date", wdate_final);
		
		int pageNo;
		if(request.getParameter("page")==null) pageNo=1;
		else pageNo = Integer.parseInt(request.getParameter("page"));
		int pageSize =3;
		
		AdopttimeDto adopt = new AdopttimeDto(m_addr,wdate_start,wdate_final,terms);

		if(m_addr.equals("") && wdate_start.equals("") && wdate_final.equals("") && terms.equals("ul")){
//		if(m_addr == null && wdate_start == null && wdate_final == null && terms == "ul"){
			PageDto pageDto = new PageDto(pageNo,cdao.getCount_All(),pageSize,m_addr,wdate_start,wdate_final,terms);
			int StartNo = pageDto.getStartNo();
			AdopttimeDto_second adopt_second = new AdopttimeDto_second(m_addr,wdate_start,wdate_final,terms,pageSize,StartNo);
			
			List<PetsitterDto> cmts = cdao.select_All(adopt_second);
		
			request.setAttribute("pageDto",pageDto);
			request.setAttribute("cmtlist",cmts);
			
			forward.isRedirect=false;
			forward.url="petsitter/home_View.jsp";
			return forward; 
			
		//}else if(wdate_start == null && wdate_final == null && terms == "ul"){
		}else if(wdate_start.equals("") && wdate_final.equals("") && terms.equals("ul")){
			
			
			PageDto pageDto = new PageDto(pageNo,cdao.getCount_Adrr(adopt),pageSize,m_addr,wdate_start,wdate_final,terms);
			
			int StartNo = pageDto.getStartNo();
			
			AdopttimeDto_second adopt_second = new AdopttimeDto_second(m_addr,wdate_start,wdate_final,terms,pageSize,StartNo);
			
			List<PetsitterDto> cmts = cdao.select_addr(adopt_second);

			request.setAttribute("pageDto",pageDto);
			request.setAttribute("cmtlist",cmts);
			forward.isRedirect=false;
			forward.url="petsitter/home_View.jsp";
			return forward; 
		
		}else if(terms.equals("ul")){ //주소, 날짜 클릭하고 조회하기
			
			PageDto pageDto = new PageDto(pageNo,cdao.getCount_Add_Date(adopt),pageSize,m_addr,wdate_start,wdate_final,terms);
			
			int StartNo = pageDto.getStartNo();
			
			AdopttimeDto_second adopt_second = new AdopttimeDto_second(m_addr,wdate_start,wdate_final,terms,pageSize,StartNo);
			
			List<PetsitterDto> cmts = cdao.select_addr_date(adopt_second);
			request.setAttribute("pageDto",pageDto);
			request.setAttribute("cmtlist",cmts);
			forward.isRedirect=false;
			forward.url="petsitter/home_View.jsp";
			return forward; 
		
		}else if(m_addr.equals("") && wdate_start.equals("") && wdate_final.equals("") && terms.equals("ul")==false){ //체크박스 체크만 하고 조회
			
			PageDto pageDto = new PageDto(pageNo,cdao.getCount_terms(adopt),pageSize,m_addr,wdate_start,wdate_final,terms);
		
			int StartNo = pageDto.getStartNo();
			
			AdopttimeDto_second adopt_second = new AdopttimeDto_second(m_addr,wdate_start,wdate_final,terms,pageSize,StartNo);
			
			List<PetsitterDto> cmts = cdao.select_terms(adopt_second);
			
			request.setAttribute("pageDto",pageDto);
			request.setAttribute("cmtlist",cmts);
			
			forward.isRedirect=false;
			forward.url="petsitter/home_View.jsp";
			return forward; 
		}else if(wdate_start.equals("") && wdate_final.equals("")){ //추가
			
			PageDto pageDto = new PageDto(pageNo,cdao.getCount_Adrr_terms(adopt),pageSize,m_addr,wdate_start,wdate_final,terms);
			
			int StartNo = pageDto.getStartNo();
			
			AdopttimeDto_second adopt_second = new AdopttimeDto_second(m_addr,wdate_start,wdate_final,terms,pageSize,StartNo);
			
			List<PetsitterDto> cmts = cdao.select_Adrr_terms(adopt_second);

			request.setAttribute("pageDto",pageDto);
			request.setAttribute("cmtlist",cmts); 
			
			forward.isRedirect=false;
			forward.url="petsitter/home_View.jsp";
			return forward; 
		}else{

			PageDto pageDto = new PageDto(pageNo,cdao.getCount(adopt),pageSize,m_addr,wdate_start,wdate_final,terms);
		
			int StartNo = pageDto.getStartNo();

			AdopttimeDto_second adopt_second = new AdopttimeDto_second(m_addr,wdate_start,wdate_final,terms,pageSize,StartNo);
		
			List<PetsitterDto> cmts = cdao.select(adopt_second);
		
			request.setAttribute("pageDto",pageDto);
			request.setAttribute("cmtlist",cmts);
			forward.isRedirect=false;
			forward.url="petsitter/home_View.jsp";
			return forward; 
		
		}
	}

}