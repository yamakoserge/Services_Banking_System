package com.gsoftcode.servicebankingsystem.repository;

import com.gsoftcode.servicebankingsystem.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
}
