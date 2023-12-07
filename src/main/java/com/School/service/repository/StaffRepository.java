package com.School.service.repository;

import com.School.service.modal.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Long> {

    Optional<Staff> findById(Long staffId);
}
