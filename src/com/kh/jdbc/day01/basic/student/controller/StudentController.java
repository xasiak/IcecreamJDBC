package com.kh.jdbc.day01.basic.student.controller;

import java.util.List;

import com.kh.jdbc.day01.basic.student.model.dao.StudentDAO;
import com.kh.jdbc.day01.basic.student.model.vo.Student;

public class StudentController {
	
	private StudentDAO studentDao;
	
	public StudentController() {
		studentDao = new StudentDAO();
	}

	public List<Student> printStudentList() {
		List<Student> sList = studentDao.selectAll();
		return sList;
	}

}
