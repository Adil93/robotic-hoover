package com.robotic.hoover.repository;

import com.robotic.hoover.entity.NavigateRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigateRequestRepository extends CrudRepository<NavigateRequest, Long> {

}
