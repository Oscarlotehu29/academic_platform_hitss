package com.hitss.academic_platform.services;

import com.hitss.academic_platform.dto.ReportCourseDto;
import com.hitss.academic_platform.dto.ReportSubjectDto;

public interface ReportsService {

	ReportCourseDto getReportCourse(Long id);
	ReportSubjectDto getReportSubjectDto(Long id);
}
