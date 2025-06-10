drop database if exists academic_platform;
create database academic_platform;
use academic_platform;

create table roles(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE,
    id_user_created INT,
    id_user_updated INT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1 
);


create table users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25),
    father_last_name VARCHAR(25),
    mother_last_name VARCHAR(25),
    birth_date DATE,
    user_name VARCHAR(15) UNIQUE, 
    email VARCHAR(255) UNIQUE,
    passwd VARCHAR(510),
    role_id INT,
    id_user_created int,
    id_user_updated INT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1,
    FOREIGN KEY(role_id) REFERENCES roles(id) on update CASCADE on delete RESTRICT
);

create table students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_enrollment CHAR(10),
    user_id INT UNIQUE,
    id_user_created INT,
    id_user_updated INT,
    is_active INT DEFAULT 1,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id) ON UPDATE CASCADE ON DELETE RESTRICT
);

create table teachers(
    id INT PRIMARY KEY AUTO_INCREMENT,
    -- area_id INT,
    user_id INT UNIQUE,
    subject_area VARCHAR(150),
    id_user_created INT,
    id_user_updated INT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1,
    -- FOREIGN KEY(area_id) REFERENCES areas(id) on update CASCADE on delete RESTRICT,
    FOREIGN KEY(user_id) REFERENCES users(id) on update CASCADE on delete RESTRICT
);

create table school_periods(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    start_date DATE,
    end_date DATE,
    id_user_created INT,
    id_user_updated INT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1
);

create table courses(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    academic_year INT,
    id_user_created INT,
    id_user_updated INT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1
);

create table subjects(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    teacher_id INT,
    course_id INT,
    id_user_created INT,
    id_user_updated INT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1,
    FOREIGN KEY(teacher_id) REFERENCES teachers(id) on update CASCADE on delete RESTRICT,
    FOREIGN KEY(course_id) REFERENCES courses(id) on update CASCADE on delete RESTRICT
);

create table enrollments(
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_id INT,
    school_period_id INT,
    id_user_created INT,
    id_user_updated INT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1,
    FOREIGN KEY(course_id) REFERENCES courses(id) on update CASCADE on delete RESTRICT,
    FOREIGN KEY(student_id) REFERENCES students(id) on update CASCADE on delete RESTRICT,
    FOREIGN KEY(school_period_id) REFERENCES school_periods(id) on update CASCADE on delete RESTRICT,
    CONSTRAINT unqStudentCoursePeriod UNIQUE (student_id, course_id, school_period_id)
);

create table grades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    subject_id INT,
    school_period_id INT,
    grade DECIMAL(3,1),
    comments VARCHAR(255),
    id_user_created INT,
    id_user_updated INT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1,
    FOREIGN KEY(student_id) REFERENCES students(id) on update CASCADE on delete RESTRICT,
    FOREIGN KEY(school_period_id) REFERENCES school_periods(id) on update CASCADE on delete RESTRICT,
    FOREIGN KEY(subject_id) REFERENCES subjects(id) on update CASCADE on delete RESTRICT,
    CONSTRAINT unqGradeStudentSubjectPeriod UNIQUE (student_id, subject_id, school_period_id)
);

create table subjects_materials(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    description VARCHAR(255),
    file_url VARCHAR(510),
    subject_id INT,
    teacher_id INT,
    id_user_created INT,
    id_user_updated INT,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_active INT DEFAULT 1,
    FOREIGN KEY(teacher_id) REFERENCES teachers(id) on update CASCADE on delete RESTRICT,
    FOREIGN KEY(subject_id) REFERENCES subjects(id) on update CASCADE on delete RESTRICT
);


drop user if exists 'hitss_user'@'%';
create user 'hitss_user'@'%' identified by 'holaMundo';

GRANT SELECT, INSERT, UPDATE, DELETE ON academic_platform.* TO 'hitss_user';