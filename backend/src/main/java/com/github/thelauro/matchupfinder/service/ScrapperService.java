package com.github.thelauro.matchupfinder.service;

import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.repository.ChampionRepository;
import com.github.thelauro.matchupfinder.repository.MatchupRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ScrapperService {

private final MatchupRepository matchupRepository;
private final ChampionRepository championRepository;

    public ScrapperService(MatchupRepository matchupRepository, ChampionRepository championRepository) {
        this.matchupRepository = matchupRepository;
        this.championRepository = championRepository;
    }

    public void scrapChampions(){

        Document champions;

        try {
            champions = Jsoup.connect("https://u.gg/lol/champions").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36").get();

            Elements listOfChampions = champions.select("div.truncate");
            Elements listOfImages = champions.select("img");
            List<String> championsNames = listOfChampions.eachText();
            List<String> championsImagesUrl = listOfImages.eachAttr("src");

            for(int i = 0; i<championsNames.size();i++){
                ChampionDTO data = new ChampionDTO(championsNames.get(i), championsImagesUrl.get(i));

                Champion champion = data.toEntity();

                if(!championRepository.existsByName(champion.getName())){
                    championRepository.save(champion);
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
