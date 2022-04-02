package com.robotic.hoover.repository;

import com.robotic.hoover.entity.NavigateResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigateResultRepository extends CrudRepository<NavigateResult, Long> {
}
