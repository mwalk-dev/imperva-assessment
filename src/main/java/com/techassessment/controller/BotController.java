package com.techassessment.controller;

import com.techassessment.repository.model.Bot;
import com.techassessment.service.BotService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("v1/bots")
@ResponseBody
public class BotController {
    private final BotService botService;

    public BotController(BotService botService) {
        this.botService = botService;
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Bot> getBot(@PathVariable(name = "id", required = true) int id) {        
        return ResponseEntity.of(botService.findById(id));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<Bot>> getBots() {
        return ResponseEntity.ok(botService.findAll());
    }

    @PostMapping(path = "/add/{name}")
    public ResponseEntity<Bot> addBot(@PathVariable(name = "name", required = true) String name) {
        // Manual validation of bot name here due to https://github.com/spring-projects/spring-framework/issues/11041
        if (name == null || name.isBlank() || name.length() > 100) {
            return ResponseEntity.badRequest().build();
        }
        Bot bot = new Bot(name);
        return ResponseEntity.ok(botService.save(bot));
    }
}
