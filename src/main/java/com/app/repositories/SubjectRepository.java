package com.app.repositories;

import com.app.models.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Optional<Subject> findByTitle(String title);
}
