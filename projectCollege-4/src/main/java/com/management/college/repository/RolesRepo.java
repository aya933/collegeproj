package com.management.college.repository;
import com.management.college.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RolesRepo extends JpaRepository<Roles,Long> {

}
