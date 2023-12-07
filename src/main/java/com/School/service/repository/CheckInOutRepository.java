package com.School.service.repository;

import com.School.service.modal.CheckInOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInOutRepository extends JpaRepository<CheckInOut,Long> {
}
