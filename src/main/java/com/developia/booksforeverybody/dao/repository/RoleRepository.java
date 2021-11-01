package com.developia.booksforeverybody.dao.repository;

import com.developia.booksforeverybody.dao.entity.Role;
import com.developia.booksforeverybody.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(Role name);
}
