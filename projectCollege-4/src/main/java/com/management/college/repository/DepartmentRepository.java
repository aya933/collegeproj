package com.management.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.college.entity.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "select * from Department d where d.departmentNAME like %:keyword% or d.departmentid like %:keyword%", nativeQuery = true)
    List<Department> findByKeyword(@Param("keyword") String keyword);

}
