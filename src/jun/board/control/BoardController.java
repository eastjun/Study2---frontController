package jun.board.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jun.board.hander.BoardHandlerAdapter;


public interface BoardController {
	public BoardHandlerAdapter execute(HttpServletRequest request, HttpServletResponse response);
	
}

