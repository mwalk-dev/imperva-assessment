package com.techassessment.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.AdditionalAnswers.*;

import com.techassessment.repository.AttackRepository;
import com.techassessment.repository.BotRepository;
import com.techassessment.repository.model.Attack;
import com.techassessment.repository.model.Bot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.mockito.InjectMocks;

@ExtendWith(MockitoExtension.class)
class AttackServiceTest {

  @Mock
  private AttackRepository attackRepository;

  @Mock
  private BotRepository botRepository;

  @InjectMocks
  private AttackService attackService;

  @Test
  void validAttackIsCreated() {
    // The mock bot repository should find a bot for any ID
    when(botRepository.findAllById(anyCollection())).thenAnswer(new Answer<Iterable<Bot>>() {
      @Override
      @SuppressWarnings("unchecked")
      public Iterable<Bot> answer(InvocationOnMock invocation) {
        var ids = (Set<Integer>)invocation.getArgument(0);
        return ids.stream().map(id -> {
          var bot = new Bot("Bot " + id);
          bot.setId(id);
          return bot;
        }).collect(Collectors.toList());
      }
    });

    // The mock attack repository should save any attack
    when(attackRepository.save(any(Attack.class))).then(returnsFirstArg());
    
    // Therefore the attack should have been created
    var result = attackService.createAttack("site", new Date(), Set.of(1, 2, 3, 4, 5));
    assertTrue(result.isPresent());
  }

  @Test
  void attackWithNoValidBotsIsRejected() {
        // The mock bot repository contains no data
        when(botRepository.findAllById(anyCollection())).thenReturn(new ArrayList<Bot>());

        // The service should ideally never get to the point of trying to save to the repository
        // So we mark it as lenient but throw if it does happen
        lenient().when(attackRepository.save(any(Attack.class))).thenThrow(new RuntimeException("AttackService attempted to save an invalid attack"));
        
        // Therefore the attack should not have been created
        var result = attackService.createAttack("site", new Date(), Set.of(1, 2, 3, 4, 5));
        assertFalse(result.isPresent());
  }
}
