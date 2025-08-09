package com.example.project.repository;
import java.util.List;
import com.example.project.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Department findByName(String name); // Optional: useful if you search by department name

    List<Department> findAll(); // Optional: to get all departments sorted by name
}
