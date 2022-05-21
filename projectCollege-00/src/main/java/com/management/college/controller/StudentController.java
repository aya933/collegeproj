package com.management.college.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;

import com.management.college.entity.Major;
import com.management.college.entity.Student;
import com.management.college.repository.MajorRepository;
import com.management.college.services.StudentService;

@Controller

public class StudentController {
	@Autowired
	private MajorRepository majornamerep;
	private StudentService studentService;
	public StudentController(StudentService studentService) {
	    super ();
	    this.studentService = studentService;
	}
	
	// @GetMapping annotation for
    // mapping HTTP GET requests onto
    // specific handler methods. 
	@GetMapping("/students")
	public String listStudents( Model model) {
		model.addAttribute("students", studentService.getAllStudents());
		 return findPaginated(1, "studentNAME", "asc", model);
	}
	
	
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model){
		
		List<Major>listmajor = majornamerep.findAll();
		// create student object to hold student form data
		Student student = new Student();
		
		model.addAttribute("student", student);
		model.addAttribute("listmajor", listmajor);
		
		return "create_student";
		
	}
	
	// @PostMapping annotation maps HTTP POST
    // requests onto specific handler methods
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
	    studentService.saveStudent(student);
	    return "redirect:/students";
}
	@GetMapping("/students/edit/{studentid}")
	public String editStudentForm(@PathVariable Long studentid, Model model) {
		model.addAttribute("student", studentService.getStudentById(studentid));
		return "edit_student";
	}
	
	@PostMapping("/students/{studentid}")
	public String updateStudent(@PathVariable Long studentid,
			@ModelAttribute("student") Student student,
			Model model) {

		// get student from database by id
		Student existingStudent = studentService.getStudentById(studentid);
		existingStudent.setStudentid(student.getStudentid());
		existingStudent.setStudentNAME(student.getStudentNAME());
		existingStudent.setStudentNUMBER(student.getStudentNUMBER());
		existingStudent.setStudentBIRTH(student.getStudentBIRTH());
		existingStudent.setStudentGENDER(student.getStudentGENDER());
		existingStudent.setStudentEMAIL(student.getStudentEMAIL());
		existingStudent.setStudentPASSWORD(student.getStudentPASSWORD());

		// save updated student object
		studentService.updateStudent(existingStudent);
		return "redirect:/students";		
	}
	
	// handler method to handle delete student request
		@GetMapping("/students/{studentid}")
		public String deleteStudent(@PathVariable Long studentid) {
			studentService.deleteStudentById(studentid);
			return "redirect:/students";
		}
		
		@GetMapping("/students/page/{pageNo}")
		public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
				@RequestParam("sortField") String sortField,
				@RequestParam("sortDir") String sortDir,
				Model model) {
			int pageSize = 5;

			Page<Student> page = studentService.findPaginated(pageNo, pageSize, sortField, sortDir);
			List<Student> listStudents = page.getContent();

			model.addAttribute("currentPage", pageNo);
			model.addAttribute("totalPages", page.getTotalPages());
			model.addAttribute("totalItems", page.getTotalElements());

			model.addAttribute("sortField", sortField);
			model.addAttribute("sortDir", sortDir);
			model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

			model.addAttribute("listStudents", listStudents);
			return "students";
		}
		
		 @RequestMapping(path = {"/student","/student/search"})
		 public String home(Student student, Model model, String keyword) {
		  if(keyword!=null) {
		   List<Student> list = studentService.getByKeyword(keyword);
		   model.addAttribute("list", list);
		  }else {
		  List<Student> list = studentService.getAllStudents();
		  model.addAttribute("list", list);}
		  return "students";
		 }
		
}
