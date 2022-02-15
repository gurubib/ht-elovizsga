package hu.gurubib.ht.elovizsga.task1.api.controllers;

import hu.gurubib.ht.elovizsga.task1.domain.services.RandomNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class RandomNumberController {

    final RandomNumberService service;

    private final int DEFAULT_LIMIT = 10;

    @Autowired
    public RandomNumberController(RandomNumberService service) {
        this.service = service;
    }

    @GetMapping("/random")
    public String randomWithLimit(@RequestParam(required = false) String limit) {
        try {
            final int max = Optional.ofNullable(limit)
                    .map(Integer::parseInt)
                    .orElse(DEFAULT_LIMIT);
            return service.random(max);
        } catch (final NumberFormatException nfe) {
            return "Invalid number: " + limit + "!";
        }
    }
}
