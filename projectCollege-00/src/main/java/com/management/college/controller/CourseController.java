package com.management.college.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.management.college.entity.Course;
import com.management.college.services.CourseService;

@Controller
public class CourseController {
	
	
	private CourseService courseService;
	public CourseController(CourseService courseService) {
	    super();
	    this.courseService = courseService;
	}
	
	/* handler
	 * below
	 */
	
	@GetMapping("/courses")
	public String listCourses(Model model){
		model.addAttribute("courses", courseService.getAllCourses());
		return "courses";
	
}
	
	@GetMapping("/courses/new")
	public String createScoreForm(Model model){
		// create course object to hold course form data
		Course course = new Course();
		model.addAttribute("course", course);
		return "create_course";
		
	}
	
	
	
	@PostMapping("/courses")
	public String saveCourse(@ModelAttribute("course") Course course) {
	    courseService.saveCourse(course);
	    return "redirect:/courses";
}
	@GetMapping("/courses/edit/{courseid}")
	public String editCourseForm(@PathVariable Long courseid, Model model) {
		model.addAttribute("course", courseService.getCourseById(courseid));
		return "edit_course";
	}
	
	@PostMapping("/courses/{courseid}")
	public String updateCourse(@PathVariable Long courseid,
			@ModelAttribute("course") Course course,
			Model model) {
		// get Course from database by id
		//Student existingStudent = studentService.getStudentById(id);
		Course existingCourse = courseService.getCourseById(courseid);
		//existingStudent.setStudentid(student.getStudentid());
		existingCourse.setCourseid(course.getCourseid());
		existingCourse.setCourseNAME(course.getCourseNAME());
		existingCourse.setCourseSEMESTER(course.getCourseSEMESTER());
		existingCourse.setCourseDate(course.getCourseDate());
		existingCourse.setCourseTIME(course.getCourseTIME());
		existingCourse.setCredit(course.getCredit());
		existingCourse.setCourseTYPE(course.getCourseNAME());		

		// save updated course object
		courseService.updateCourse(existingCourse);
		return "redirect:/courses";		
	}
	
	@GetMapping("/courses/{courseid}")
	public String deleteCourse(@PathVariable Long courseid) {
		courseService.deleteCourseById(courseid);
		return "redirect:/courses";
	}
	
	
	
}

