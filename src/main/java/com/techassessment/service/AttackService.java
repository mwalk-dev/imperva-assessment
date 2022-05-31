package com.techassessment.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.techassessment.repository.AttackRepository;
import com.techassessment.repository.BotRepository;
import com.techassessment.repository.model.Attack;
import com.techassessment.repository.model.Bot;

import org.springframework.stereotype.Service;

@Service
public class AttackService {
    private final AttackRepository attackRepository;
    private final BotRepository botRepository;

    public AttackService(AttackRepository attackRepository, BotRepository botRepository) {
        this.attackRepository = attackRepository;
        this.botRepository = botRepository;
    }

    // Assumes that attacks are defined referencing previously known bots; we don't also insert new bots when inserting
    // an attack. Requirements did not specify. In a real application we probably want to support an attack consisting
    // of existing and/or new bots
    public Optional<Attack> createAttack(String site, Date attackDate, Set<Integer> botIds) {
        Iterable<Bot> bots = botRepository.findAllById(botIds);
        List<Bot> botList = StreamSupport.stream(bots.spliterator(), false).collect(Collectors.toList());
        if (botList.size() == 0) {
            // If we got an empty list of bots from the repository, it means that none of the supplied bot IDs were
            // valid. This is not acceptable and we should not save this attack.
            return Optional.empty();
        }
        // Note that if we're here, it just means we have at least one valid bot that is part of the attack. It does
        // not mean that ALL of the bots in botIds were valid. This may or may not be desirable behavior.
        Attack attack = new Attack(site, attackDate, botList);
        return Optional.of(attackRepository.save(attack));
    }

    public Iterable<Attack> findAll() {
        return attackRepository.findAll();
    }

    public Optional<Attack> findById(Integer id) {
        return attackRepository.findById(id);
    }

    public List<Attack> findByAttackDate(Date startDate, Date endDate) {
        return attackRepository.findAllByAttackDateBetween(startDate, endDate);
    }
}
