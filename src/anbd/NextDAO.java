package anbd;

import java.sql.SQLException;

public class NextDAO extends DbInfo{
	
	//이전글 
	public int preNo (int no) {
		int preNo=0;
		getConnection();
		String SQL= "select max(no) from board where no<"+no;
		prepareStatement(SQL);    //쿼리를 미리 준비해서 메모리에 저장시키는 거
		executeQuery(); 		//쿼리 실행
		try {
			while(rs.next()){
				return preNo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rsClose();
		pstateClose();
		conClose();
		return preNo;
	}
}