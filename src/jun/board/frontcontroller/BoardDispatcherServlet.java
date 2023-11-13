package jun.board.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jun.board.control.BoardController;
import jun.board.controller.NoticeDeleteController;
import jun.board.controller.NoticeInsertController;
import jun.board.controller.NoticeInsertViewController;
import jun.board.controller.NoticeSelectViewController;
import jun.board.controller.NoticeUpdateController;
import jun.board.controller.NoticeUpdateViewController;
import jun.board.controller.NoticeViewController;
import jun.board.hander.BoardHandlerAdapter;

public class BoardDispatcherServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(BoardDispatcherServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI =request.getRequestURI( );
		String contextPath =request.getContextPath( );
		String pathURL =requestURI.substring(contextPath.length( ));
		log.info("매핑명 조회" + pathURL);
		
		BoardHandlerAdapter boardHandlerAdapter = new BoardHandlerAdapter();
		BoardController controller =null;
		
		if (pathURL.equals("/NoticeView.board")) {
			controller = new NoticeViewController();
			boardHandlerAdapter = controller.execute(request, response);
			log.info("공지사항 이동  - "+boardHandlerAdapter);	
		}
		else if (pathURL.equals("/NoticeInsertView.board")) {
			controller = new NoticeInsertViewController();
			boardHandlerAdapter = controller.execute(request, response);
			log.info("공지사항 글쓰기 뷰 이동  - "+boardHandlerAdapter);	
		}
		else if (pathURL.equals("/NoticeInsert.board")) {
			controller = new NoticeInsertController();
			boardHandlerAdapter = controller.execute(request, response);
			log.info("공지사항 글쓰기 이동  - "+boardHandlerAdapter);	
		}
		else if (pathURL.equals("/NoticeSelect.board")) {
			controller = new NoticeSelectViewController();
			boardHandlerAdapter = controller.execute(request, response);
			log.info("공지사항 글 조회 이동  - "+boardHandlerAdapter);	
		}
		else if (pathURL.equals("/NoticeUpdateView.board")) {
			controller = new NoticeUpdateViewController();
			boardHandlerAdapter = controller.execute(request, response);
			log.info("공지사항 글 수정 이동  - "+boardHandlerAdapter);	
		}
		else if (pathURL.equals("/NoticeUpdate.board")) {
			controller = new NoticeUpdateController();
			boardHandlerAdapter = controller.execute(request, response);
			log.info("공지사항 수정 이동  - "+boardHandlerAdapter);	
		}
		else if (pathURL.equals("/NoticeDelete.board")) {
			controller = new NoticeDeleteController();
			boardHandlerAdapter = controller.execute(request, response);
			log.info("공지사항 글 삭제  - "+ boardHandlerAdapter);	
		}
		
		if (boardHandlerAdapter != null) {
			if (boardHandlerAdapter.isRedirect()) {
				response.sendRedirect(boardHandlerAdapter.getPath());
			}
			else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(boardHandlerAdapter.getPath());
				dispatcher.forward(request, response);
			}
		}
	
	
	
	}

	
	
}
	

