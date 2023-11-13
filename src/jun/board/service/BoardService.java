package jun.board.service;

import java.util.ArrayList;

import jun.board.dto.BoardDTO;

public interface BoardService {

	public ArrayList<BoardDTO> boardSelectAll();
	public boolean NoticeInsert(BoardDTO boardDTO);
	public BoardDTO NoticeSelect(int num);
	public boolean NoticeUpdate(BoardDTO boardDTO);
	public boolean NoticeDelete(int num);
}
