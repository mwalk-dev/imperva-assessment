package com.techassessment.service;

import java.util.Optional;

import com.techassessment.repository.BotRepository;
import com.techassessment.repository.model.Bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Given the simple requirements, this service is just a pass-through to the underlying repository. It's just here
// for completeness, because in general it would hold any business logic or complex validation around bots.
@Service
public class BotService {
    @Autowired
    private BotRepository botRepository;

    public Iterable<Bot> findAll() {
        return botRepository.findAll();
    }

    public Optional<Bot> findById(Integer id) {
        return botRepository.findById(id);
    }

    public Bot save(Bot bot) {
        return botRepository.save(bot);
    }
}
