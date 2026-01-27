package com.github.thelauro.matchupfinder.config;

import com.github.thelauro.matchupfinder.service.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private ScrapperService scrapperService;

    public DatabaseSeeder(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @Override
    public void run(String... args) throws Exception {
        scrapperService.scrapChampions();
    }
}
