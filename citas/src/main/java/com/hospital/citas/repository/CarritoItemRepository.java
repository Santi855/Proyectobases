package com.hospital.citas.repository;

import com.hospital.citas.model.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Integer> {
}

