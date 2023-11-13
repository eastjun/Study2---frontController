package jun.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jun.board.control.BoardController;
import jun.board.dao.BoardDAO;
import jun.board.dto.BoardDTO;
import jun.board.hander.BoardHandlerAdapter;

public class NoticeUpdateController implements BoardController{
	private static Log log = LogFactory.getLog(NoticeUpdateController.class);
	@Override
	public BoardHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response) {
		log.info("여기까지 못옴?");
		String title= request.getParameter("title");
		String content = request.getParameter("content");
		String num= request.getParameter("num");
		log.info("여기까지는 오나요??");
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setTitle(title);
		boardDTO.setContent(content);
		boardDTO.setNum(Integer.parseInt(num));
		BoardDAO boardDAO = new BoardDAO();
		log.info("여기가 문제인가?");
		boolean update = boardDAO.NoticeUpdate(boardDTO);
		
		if (update) {
			request.setAttribute("success", true);
			BoardHandlerAdapter boardHandlerAdapter = new BoardHandlerAdapter();
			boardHandlerAdapter.setPath("/WEB-INF/view/board/noticeUpdate.jsp");
			return boardHandlerAdapter;
			
		}
		return null;
		
		
	}

}
