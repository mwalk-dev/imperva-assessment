package com.techassessment.repository;

import java.util.Date;
import java.util.List;

import com.techassessment.repository.model.Attack;

import org.springframework.data.repository.CrudRepository;

public interface AttackRepository extends CrudRepository<Attack, Integer> {
    public List<Attack> findAllByAttackDateBetween(
        Date attackDateStart,
        Date attackDateEnd
    );
}
