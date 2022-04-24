package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Frais;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Frais entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraisRepository extends JpaRepository<Frais, Long> {}
