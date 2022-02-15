package hu.gurubib.ht.elovizsga.task1.api.controllers;

import hu.gurubib.ht.elovizsga.task1.domain.services.RandomNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomNumberController {

    final RandomNumberService service;

    private final int DEFAULT_LIMIT = 10;

    @Autowired
    public RandomNumberController(RandomNumberService service) {
        this.service = service;
    }

    @GetMapping("/random")
    public String random() {
        return service.random(DEFAULT_LIMIT);
    }

    @GetMapping("/random/{limit}")
    public String randomWithLimit(@PathVariable String limit) {
        try {
            return service.random(Integer.parseInt(limit));
        } catch (final NumberFormatException nfe) {
            return "Invalid number: " + limit + "!";
        }
    }
}
