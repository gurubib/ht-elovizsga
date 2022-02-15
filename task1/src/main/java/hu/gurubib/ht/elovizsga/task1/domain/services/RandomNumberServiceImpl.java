package hu.gurubib.ht.elovizsga.task1.domain.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomNumberServiceImpl implements RandomNumberService {

    @Override
    public String random(int max) {
        final Random random = new Random();
        final int min = 1;
        return String.valueOf(random.nextInt((max + 1) - min) + min);
    }

}
