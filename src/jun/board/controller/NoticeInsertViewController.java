package jun.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jun.board.control.BoardController;
import jun.board.hander.BoardHandlerAdapter;

public class NoticeInsertViewController implements BoardController{

	@Override
	public BoardHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		BoardHandlerAdapter boardHandlerAdapter = new BoardHandlerAdapter();
		boardHandlerAdapter.setPath("/WEB-INF/view/board/noticeInsertView.jsp");
		return boardHandlerAdapter;
	}

}
