package com.kh.jdbc.day01.basic.student.model.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.kh.jdbc.day01.basic.student.model.vo.Student;

public class StudentDAO {
	
	public List<Student> selectAll() {
		/*
		 * 1. 드라이버 등록
		 * 2. DB 연결 생성
		 * 3. 쿼리문 실행 준비
		 * 4. 쿼리문 실행 
		 * 5. 결과 받기
		 * 6. 자원해제(close())
		 */
		String driverName = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "student";
		String password = "student";
		String query = "SELECT * FROM STUDENT_TBL";
		
		List<Student> sList = null;
		Student student = null;
		try {
			// 1. 드라이버 등록
			Class.forName(driverName);
			
			// 2. DB 연결 생성(DriverManager)
			Connection conn = DriverManager.getConnection(url, user, password);
			
			// 3. 쿼리문 실행 준비
			Statement stmt = conn.createStatement();
			
			// 4. 쿼리문 실행 , 5. 결과 받기
			ResultSet rset = stmt.executeQuery(query);  // SELECT형태일 때 ResultSet사용
			
			
			sList = new ArrayList<Student>();
			// 후처리
			while(rset.next()) {
				student = new Student();
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

}
