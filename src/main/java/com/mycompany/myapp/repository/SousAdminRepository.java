package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SousAdmin;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SousAdmin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SousAdminRepository extends JpaRepository<SousAdmin, Long> {}
