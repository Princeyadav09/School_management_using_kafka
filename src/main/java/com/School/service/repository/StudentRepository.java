package com.School.service.repository;

import com.School.service.modal.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

   Optional<Student> findById(Long studentId);

}
