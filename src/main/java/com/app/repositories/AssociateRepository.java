package com.app.repositories;

import com.app.models.Associate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface AssociateRepository extends CrudRepository<Associate, Long> {
}
