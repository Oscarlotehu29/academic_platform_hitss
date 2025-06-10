package com.hitss.academic_platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hitss.academic_platform.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
