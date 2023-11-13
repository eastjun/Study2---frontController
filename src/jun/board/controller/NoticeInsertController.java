package jun.board.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jun.board.control.BoardController;
import jun.board.dao.BoardDAO;
import jun.board.dto.BoardDTO;
import jun.board.hander.BoardHandlerAdapter;

public class NoticeInsertController implements BoardController{
	private static Log log = LogFactory.getLog(NoticeInsertController.class);

	@Override
	public BoardHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String userid = (String) request.getSession().getAttribute("userid");
		
		BoardDTO boardDTO = new BoardDTO();
		BoardDAO boardDAO = new BoardDAO();
		
		
		boardDTO.setTitle(title);
		boardDTO.setContent(content);
		boardDTO.setUserid(userid);
		
		boolean insert = boardDAO.NoticeInsert(boardDTO);
		if (insert) {
			log.info("성공적인 글쓰기");
			 request.setAttribute("success", true);
		}
		else {
			log.info("글작성 실패");
			request.setAttribute("false", true);
		}
		
		BoardHandlerAdapter boardHandlerAdapter = new BoardHandlerAdapter();
		boardHandlerAdapter.setPath("/WEB-INF/view/board/noticeInsert.jsp");
		return boardHandlerAdapter;
		
		
	}

}
