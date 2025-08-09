// src/main/java/com/example/project/seed/DepartmentSeeder.java
package com.example.project.seed;

import com.example.project.entity.Department;
import com.example.project.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DepartmentSeeder implements ApplicationRunner {

    private final DepartmentRepository repo;

    @Autowired
    public DepartmentSeeder(DepartmentRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<String> canonical = List.of(
                "Computer Science",
                "Business",
                "Mathematics",
                "Engineering",
                "Health Sciences",
                "Arts & Humanities",
                "Education",
                "Media & Communication",
                "Law & Legal Studies"
        );

        int inserted = 0;
        for (String name : canonical) {
            // relies on unique constraint on Department.name
            if (repo.findByName(name) == null) {
                repo.save(new Department(name));
                inserted++;
            }
        }
        System.out.println("✅ Departments seeder complete. Inserted " + inserted + " new rows, left existing as-is.");
    }
}
