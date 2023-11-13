package jun.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jun.board.dto.BoardDTO;
import jun.board.service.BoardService;


public class BoardDAO implements BoardService{
	private static Log log = LogFactory.getLog(BoardDAO.class);
	
	@Override
	public ArrayList<BoardDTO> boardSelectAll() {
		
		Connection connection =null;
		PreparedStatement preparedStatement =null;
		ResultSet resultSet =null;
		ArrayList<BoardDTO> arrayList = new ArrayList<BoardDTO>();
		try {
			Context context = new InitialContext( );
			DataSource dataSource =(DataSource) context.lookup("java:comp/env/jdbc");
			connection =dataSource.getConnection( );
			
			String sql ="select num, title, content, userid, writeday from notice ORDER BY num DESC";
			log.info("SQL 확인 -" + sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next( )) {
				BoardDTO boardDTO = new BoardDTO();
			boardDTO.setNum(resultSet.getInt("num"));
			boardDTO.setTitle(resultSet.getString("title"));
			boardDTO.setContent(resultSet.getString("content"));
			boardDTO.setUserid(resultSet.getString("userid"));
			boardDTO.setWriteday(resultSet.getString("writeday"));

			arrayList.add(boardDTO);
			}
			resultSet.getRow( );
			if(resultSet.getRow( )== 0) {
				log.info("등록된 게시글이 없습니다.");
			}
			
		} catch (Exception e) {
			log.info("전체 게시글 조회 실패 -" + e);
		}
		finally {
			try {
				resultSet.close();
				preparedStatement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return arrayList;
	}

	@Override
	public boolean NoticeInsert(BoardDTO boardDTO) {
		int num = 0;
		String sql = "";
		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			// 글 번호의 최대값을 조회하여 글을 등록할 때 글 번호를 순차적으로 설정한다.
			/*
			 * max 함수로 최대값을 얻는 대신 count 함수로 레코드 수를 얻어서 1을 더하는 방법은 레코드를 삭제하고 등록할 때 무결성 제약 조건에
			 * 위배되므로 max 함수를 사용하여 글 번호를 계산해서 지정하는 것이 정확하다.
			 */
			sql = "select max(num) from notice";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				num = resultSet.getInt(1) + 1;

			} else {
				num = 1;
			}
			// 사전 컴파일된 SQL 문을 실행하고 생성된 결과를 반환하는 데 사용된 객체에 대한 자원 해제를 한다.
			preparedStatement.close( );
			sql = "insert into notice (num, title, content, userid, writeday) ";
			sql += " values(?, ?, ?, ?, sysdate )";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, num);
			preparedStatement.setString(2, boardDTO.getTitle());
			preparedStatement.setString(3, boardDTO.getContent());
			preparedStatement.setString(4, boardDTO.getUserid());
			
			
			result = preparedStatement.executeUpdate( );
			if(result == 0) {
				return false;
			}
			return true;
		} catch(Exception e) {
			log.info("글 등록 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return false;
	}

	@Override
	public BoardDTO NoticeSelect(int num) {
		
		BoardDTO boardDTO = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );

			String sql = "select num, title, content, userid, to_char(writeday, 'yyyy-mm-dd') writeday from notice ";
			sql += " where num = ?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, num);
			resultSet = preparedStatement.executeQuery( );
			if(resultSet.next( )) {
				boardDTO = new BoardDTO( );
				boardDTO.setNum(resultSet.getInt("num"));
				boardDTO.setTitle(resultSet.getString("title"));
				boardDTO.setContent(resultSet.getString("content"));
				boardDTO.setUserid(resultSet.getString("userid"));
				boardDTO.setWriteday(resultSet.getString("writeday"));			
			}
			
			return boardDTO;
		} catch(Exception e) {
			log.info("글 내용 보기 실패 - " + e);
		} finally {
			try {
				resultSet.close( );
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return null;
	}

	@Override
	public boolean NoticeUpdate(BoardDTO boardDTO) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "update notice set title=?, content=?, userid = userid, writeday = sysdate ";
			sql += " where num=?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, boardDTO.getTitle());
			preparedStatement.setString(2, boardDTO.getContent( ));
			preparedStatement.setInt(3, boardDTO.getNum( ));
			preparedStatement.executeUpdate( );
			return true;

		} catch(Exception e) {
			log.info("글 수정 실패 - " + e);
		} finally {
			try {
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e) {
				e.printStackTrace( );
			}
		}
		return false;
	}

	@Override
	public boolean NoticeDelete(int num) {
		
		int result = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Context context = new InitialContext( );
			DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection( );
			String sql = "delete from notice where num=?";
			log.info("SQL 확인 - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, num);
			result = preparedStatement.executeUpdate( );

			if(result == 0) {
				return false;

			}
		} catch(Exception e) {
			log.info("글 삭제 실패 - " + e);
		} finally {
			try {
				preparedStatement.close( );
				connection.close( );
			} catch(SQLException e2) {
				e2.printStackTrace( );
			}
		}

		return false;
	}
	

}
