package com.kas.repository;

import com.kas.entity.LogStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogStatusRepository extends JpaRepository<LogStatus, Long> {
}
