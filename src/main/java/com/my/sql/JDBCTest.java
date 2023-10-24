package com.my.sql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTest {

	public static void selectTest() {

		// 전자지갑 테스트

		// 1. JDBC 드라이버 설치
		// maven repository가서 jar 다운!!

		//		-------------------------------------

		// 2. 드라이버 클래스들 JVM에 로드
		// (프로젝트 우클릭 후 BuildPath - add external 어쩌구 설정해서 jar 추가해주기)
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("JDBC 드라이버 로드 성공 :-)");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return; // void로 선언된 method는 return;으로 종료 가능
		} // try-catch

		//		-------------------------------------

		// 3. DB와 연결
		Connection conn = null;

		String url ="jdbc:oracle:thin:@STUDYPROJECT_medium?TNS_ADMIN=C://opt//OracleCloudWallet//VFX";
		String user ="admin";
		String password = "Kosaproject2023";

		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("DB와 연결 성공 :-)");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		} // try-catch

		// 4. SQL문 DB 서버로 송신
		PreparedStatement pstmt = null;

		// 5. SQL문 결과 수신하기
		ResultSet rs = null;

		// 바인드 변수 사용할 경우, 컬럼이나 테이블 명에는 사용할 수 없음!
		// 무조건 값에만 사용이 가능!
		String selectSQL =
				"SELECT * "
						+ "FROM emp_table";

		try {
			pstmt = conn.prepareStatement(selectSQL); // 송신

			rs = pstmt.executeQuery(); // ?값(바인드변수)만 보냄

			// 출력
			while(rs.next()) {
				int age =
						rs.getInt("age");

				System.out.println(age);

			} // while

		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {

			// 연결 끊기!!! 중요!!
			if(rs != null) {
				try { rs.close(); } catch (SQLException e) {} // try-catch
			} // if

			if(pstmt != null) {
				try { pstmt.close(); } catch (SQLException e) {}	// try-catch
			} // if

			if(conn != null) {
				try { conn.close(); } catch (SQLException e) {}	// try-catch
			} // if

		} // try-catch-finally

	} // selectTest()

//	public static void insertTest() {
//
//		// 2. JDBC 드라이버 로드
//		try {
//			Class.forName("oracle.jdbc.OracleDriver");
//			System.out.println("JDBC 드라이버 로드 성공 :-)");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		// 3. DB와 연결
//		Connection conn = null;
//
//		String url = "jdbc:oracle:thin:@192.168.1.84:1521:xe";
//		//		String url = "jdbc:oracle:thin:@192.168.1.22:1521:xe";
//		String user = "hr";
//		String password = "hr";
//
//		try {
//			conn = DriverManager.getConnection(url, user, password);
//			System.out.println("DB와 연결 성공 :-)");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return;
//		} // try-catch
//
//		// 4. SQL 구문 송신
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		String insertSQL = "INSERT INTO customer(id, pwd, name) VALUES (?,?,?)";
//
//		try {
//			pstmt = conn.prepareStatement(insertSQL);
//
//			pstmt.setString(1, "id21");
//			pstmt.setString(2, "p21");
//			pstmt.setString(3, "seng");
//
//			int rowcnt =
//					pstmt.executeUpdate();
//			// INSERT문 사용할 경우 executeUpdate() 사용!
//			// executeUpdate() = 처리된 행 수를 반환함
//
//			System.out.println(rowcnt + "건이 추가되었습니다. :-)");
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//
//			// 자원해제
//			if(pstmt != null) {
//				try {pstmt.close();} catch (SQLException e) {}
//			}; // if
//
//			if(conn != null) {
//				try {conn.close();} catch (SQLException e) {}
//			}; // if
//
//		} // try-catch-finally
//
//	} // insertTest()

	//	----------------------------------------------------------------------------

	public static void main(String[] args) {

		selectTest();
		//		insertTest();

	} // end main

} // end class
