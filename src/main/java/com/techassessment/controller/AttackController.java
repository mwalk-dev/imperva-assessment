package com.techassessment.controller;

import java.util.Date;

import com.techassessment.controller.model.CreateAttackData;
import com.techassessment.repository.model.Attack;
import com.techassessment.service.AttackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("v1/attacks")
@ResponseBody
public class AttackController {
    @Autowired
    private AttackService attackService;

    @GetMapping(path = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Attack> getAttack(@PathVariable("id") int id) {
        return ResponseEntity.of(attackService.findById(id));
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Attack>> getAttacks() {
        return ResponseEntity.ok(attackService.findAll());
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Attack> addAttack(@Validated @RequestBody CreateAttackData attackData) {
        return ResponseEntity.of(
            attackService.createAttack(attackData.getSite(), attackData.getAttackDate(), attackData.getBotIds())
        );
    }

    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Attack>> findAttacks(
        @RequestParam(name = "start", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
        @RequestParam(name = "end", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate
    ) {
        return ResponseEntity.ok(attackService.findByAttackDate(startDate, endDate));
    }
}