package com.hitss.academic_platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.academic_platform.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
