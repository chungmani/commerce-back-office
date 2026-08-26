package com.example.commercebackoffice.domain.admin.repository;

import com.example.commercebackoffice.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
