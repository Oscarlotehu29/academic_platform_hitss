package com.hitss.academic_platform.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hitss.academic_platform.services.ReportsService;

@RequestMapping("/api/reports")
@RestController
public class ReportsController {

	
	@Autowired
	private ReportsService reportService;
	
	@GetMapping("/{id}/getAverageByCourseId")
	private ResponseEntity<?> getGradesByIdStudents(@PathVariable Long id){
		
		try {
			return ResponseEntity.ok(reportService.getReportCourse(id));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}	
	
	@GetMapping("/{id}/getAverageBySubjectId")
	private ResponseEntity<?> getGradesBySubjectId(@PathVariable Long id){
		
		try {
			return ResponseEntity.ok(reportService.getReportSubjectDto(id));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}	
}
