package com.techassessment.repository;

import com.techassessment.repository.model.Bot;

import org.springframework.data.repository.CrudRepository;

public interface BotRepository extends CrudRepository<Bot, Integer> {
    
}
