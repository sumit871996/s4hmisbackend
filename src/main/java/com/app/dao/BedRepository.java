package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Bed;

public interface BedRepository extends JpaRepository<Bed, Long> {

	Optional<Bed> findByWardNumberAndBedNumber(String wardNumber, Long bedNumber);

}
