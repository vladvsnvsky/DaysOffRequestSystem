package com.lab.model.repository;

import com.lab.model.model.DaysOff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DaysOffRepository extends JpaRepository<DaysOff, Long> {


    List<DaysOff> findByUserId(Long userId);
}
