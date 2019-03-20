package com.dawid.ems.repository;

import com.dawid.ems.entity.QuiltingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuiltingDataRepository extends JpaRepository<QuiltingData, Integer> {
}
