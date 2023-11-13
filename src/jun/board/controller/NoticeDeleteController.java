package jun.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jun.board.control.BoardController;
import jun.board.dao.BoardDAO;
import jun.board.hander.BoardHandlerAdapter;

public class NoticeDeleteController implements BoardController{

	@Override
	public BoardHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		BoardDAO boardDAO = new BoardDAO();
		boolean delete = boardDAO.NoticeDelete(num);
		System.out.println(delete);
		if (!delete) {
			request.setAttribute("success", true);
			BoardHandlerAdapter boardHandlerAdapter = new BoardHandlerAdapter();
			boardHandlerAdapter.setPath("/WEB-INF/view/board/noticeDelete.jsp");
			return boardHandlerAdapter;
		}
		
		return null;
	}

}
