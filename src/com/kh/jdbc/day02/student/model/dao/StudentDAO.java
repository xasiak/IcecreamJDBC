package com.kh.jdbc.day02.student.model.dao;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.student.model.vo.Student;

public class StudentDAO {
	
	private final String DRIVERNAME = "oracle.jdbc.driver.OracleDriver";
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	private final String USER = "student";
	private final String PASSWORD = "student";
	
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 
		 * 5. 결과 받기
		 * 6. 자원해제(close())
		 */
		
		String query = "SELECT * FROM STUDENT_TBL";
		
		List<Student> sList = null;
		Student student = null;
		try {
			// 1. 드라이버 등록
			Class.forName(DRIVERNAME);
			
			// 2. DB 연결 생성(DriverManager)
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			
			// 4. 쿼리문 실행 , 5. 결과 받기
			ResultSet rset = stmt.executeQuery(query);  // SELECT형태일 때 ResultSet사용
			
			
			sList = new ArrayList<Student>();
			// 후처리
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			rset.close();
			stmt.close();
			conn.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;

	}
	
	public List<Student> selectAllByName(String studentName) {
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_NAME = '"+studentName+"'";
		List<Student> sList = new ArrayList<Student>();
		Student student = null;
		try {
			Class.forName(DRIVERNAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			// 후처리
			while(rset.next()) {
				student = rsetToStudent(rset);
				sList.add(student);
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sList;
	}

	public Student selectOneById(String studentId) {
						// SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = 'khuser01'
		String query = "SELECT * FROM STUDENT_TBL WHERE STUDENT_ID = '"+studentId +"'";
		Student student = null;
		try {
			Class.forName(DRIVERNAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
//			while(rset.next()) {}; 	// 여러개일때는 while문 사용!!!!!!!!!!!!!!!!!!!!!!!!!
			if(rset.next()) {
				student = new Student();
				student.setStudentId(rset.getString("STUDENT_ID"));
				student.setStudentPw(rset.getString("STUDENT_PW"));
				student.setStudentName(rset.getString("STUDENT_NAME"));
				student.setAge(rset.getInt("AGE"));
				student.setEmail(rset.getString("EMAIL"));
				student.setPhone(rset.getString("PHONE"));
				// 문자는 문자열에 문자로 잘라서 사용, charAt() 메소드 사용
				student.setGender(rset.getString("GENDER").charAt(0));
				student.setAddress(rset.getString("ADDRESS"));
				student.setHobby(rset.getString("HOBBY"));
				student.setEnrollDate(rset.getDate("ENROLL_DATE"));
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}

	public int insertStudent(Student student) {
			/*
			 * 1. 드라이버 등록
			 * 2. DB 연결 생성
			 * 3. 쿼리문 실행 준비
			 * 4. 쿼리문 실행 
			 * 5. 결과 받기
			 * 6. 자원해제(close())
			 */
			// INSERT INTO STUDENT_TBL VALUES ('khuser01', 'pass01', '일용자', 'M', 11, 'khuser01@kh.com', '01012345678', '서울시 중구 남대문로 120', '독서, 수영', DEFAULT);
			String query = "INSERT INTO STUDENT_TBL VALUES('"+student.getStudentId()+"', "
					+ "'"+student.getStudentPw()+"', "
							+ "'"+student.getStudentName()+"', "
									+ "'"+student.getGender()+"', "
											+ ""+student.getAge()+", "
													+ "'"+student.getEmail()+"', "
															+ "'"+student.getPhone()+"' , "
																	+ "'"+student.getAddress()+"', "
																			+ "'"+student.getHobby()+"', "
																					+ "SYSDATE)";
			// (시험)홑따옴표 안에 쌍따옴표!!!!!!!!!!!!!!!
			int result = -1;
			try {
				// 1. 드라이버 등록
				Class.forName(DRIVERNAME);
				// 2. 디비 연결 생성
				Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				// 3. 쿼리 실행 준비
				Statement stmt =conn.createStatement();
				// 4. 실행하고 5. 결과받기
	//			stmt.executeQuery(query);	// (시험)SELECT용 !!!!!!!!!!!
				result = stmt.executeUpdate(query); 	// DML(INSERT, UPDATE, DELETE)
				
				stmt.close();
				conn.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}

	public int updateStudent(Student student) {
		String query = "UPDATE STUDENT_TBL SET "
				+ "STUDENT_PW = '"+ student.getStudentPw() +"', "
					+ "EMAIL = '"+student.getEmail()+"', "
							+ "PHONE = '"+student.getPhone()+"', "
									+ "ADDRESS = '"+student.getAddress()+"', "
											+ "HOBBY = '"+student.getHobby()+"' "
													+ "WHERE STUDENT_ID = '"+student.getStudentId()+"'";
		int result = -1;
		try {
			Class.forName(DRIVERNAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int deleteStudent(String studentId) {
		String query = "DELETE FROM STUDENT_TBL WHERE STUDENT_ID = '" + studentId +"'";
		int result = -1;
		try {
			Class.forName(DRIVERNAME);
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Student rsetToStudent(ResultSet rset) throws SQLException {
		Student student = new Student();
		student.setStudentId(rset.getString("STUDENT_ID")); //  set.~ = "가져온 데이터"
		student.setStudentPw(rset.getString("STUDENT_PW"));
		student.setStudentName(rset.getString("STUDENT_NAME"));
		student.setAge(rset.getInt("AGE"));
		student.setEmail(rset.getString("EMAIL"));
		student.setPhone(rset.getString("PHONE"));
		// 문자는 문자열에 문자로 잘라서 사용, charAt() 메소드 사용
		student.setGender(rset.getString("GENDER").charAt(0));
		student.setAddress(rset.getString("ADDRESS"));
		student.setHobby(rset.getString("HOBBY"));
		student.setEnrollDate(rset.getDate("ENROLL_DATE"));
		return student;
	}

}
