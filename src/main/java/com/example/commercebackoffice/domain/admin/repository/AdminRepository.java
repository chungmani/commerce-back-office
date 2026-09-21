package com.example.commercebackoffice.domain.admin.repository;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import com.example.commercebackoffice.domain.admin.enums.AdminRole;
import com.example.commercebackoffice.domain.admin.enums.AdminState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByEmail(String email);

    Optional<Admin> findByEmail(String email);

    @Query("""
    SELECT a FROM Admin a
     WHERE (:keyword IS NULL OR (a.name LIKE CONCAT('%', :keyword, '%')) OR (a.email LIKE CONCAT('%', :keyword, '%')))
         AND (:state IS NULL OR a.state = :state) AND (:role IS NULL OR a.role = :role) AND a.deletedAt IS NULL
    """)
    Page<Admin> findAllByKeywordAndFilter(@Param("keyword") String keyword, @Param("state") AdminState state, @Param("role") AdminRole role, Pageable pageable);

    @Query("""
    SELECT a FROM Admin a
    WHERE a.email = :email AND NOT a.id = :adminId
    """)
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("adminId")Long adminId);

    @Query("SELECT a FROM Admin a WHERE a.id = :adminId AND a.deletedAt IS NULL")
    Optional<Admin> findByIdNotDeleted(@Param("adminId") Long adminId);
}
