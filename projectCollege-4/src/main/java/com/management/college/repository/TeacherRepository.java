package com.management.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.college.entity.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query(value = "select * from Teacher t where t.teacherid like %:keyword% or t.teacherNAME like %:keyword% or t.teacherNUMBER like %:keyword%" , nativeQuery = true)
    List<Teacher> findByKeyword(@Param("keyword") String keyword);

}
