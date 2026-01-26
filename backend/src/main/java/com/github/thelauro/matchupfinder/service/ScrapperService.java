package com.github.thelauro.matchupfinder.service;

import com.github.thelauro.matchupfinder.repository.ChampionRepository;
import com.github.thelauro.matchupfinder.repository.MatchupRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapperService {

private final MatchupService matchupService;
private final ChampionService championService;

    public ScrapperService(MatchupService matchupService, ChampionService championService) {
        this.matchupService = matchupService;
        this.championService = championService;
    }

    public void scrapChampions(){

        Document champions;

        try {
            champions = Jsoup.connect("https://u.gg/lol/champions").get();

            List<Element> listOfChampions = champions.select("div.truncate");
            List<Element> listOfImages = champions.select("img");
            List<String> championsNames = new ArrayList<>();
            List<String> championsImages = new ArrayList<>();


            for(Element element : listOfChampions){
                championsNames.add(element.text());
            }

            for(Element element : listOfImages){
                championsImages.add(element.attr("src"));
            }




        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
