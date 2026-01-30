package com.github.thelauro.matchupfinder.service;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.dto.MatchupInputDTO;
import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.Matchup;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import com.github.thelauro.matchupfinder.repository.ChampionRepository;
import com.github.thelauro.matchupfinder.repository.MatchupRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
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
            champions = Jsoup.connect("https://u.gg/lol/champions")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .get();

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

    public void scrapMatchups(){

        List<Champion> champions = championRepository.findAll();
        List<String> lanes = Arrays.stream(Lane.values()).map(Enum::name).map(String::toLowerCase).toList();

        Document matchup;

        for(Champion champion : champions){

            String championName = champion.getName().replace(" ","");

            for(String lane : lanes){

                try{

                    matchup = Jsoup.connect("https://u.gg/lol/champions/"+championName+"/counter?role="+lane)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                            .get();

                    Elements enemies = matchup.select("div.truncate");
                    Elements winRates = matchup.select("div[class='text-[12px] font-bold leading-[15px] whitespace-nowrap text-right text-accent-blue-400']");
                    Elements gamesPlayeds = matchup.select("div[class='mt-[2px] text-accent-gray-100 text-[11px] font-normal whitespace-nowrap']");

                    List<String> listOfEnemies = enemies.eachText();
                    List<Double> listOfWinRates = winRates.eachText().stream()
                            .map(text -> text.replace("% WR",""))
                            .map(Double::parseDouble).toList();

                    List<Integer> listOfGames = gamesPlayeds.eachText().stream()
                            .map(text -> text.replace(" games",""))
                            .map(text -> text.replace(",",""))
                            .map(Integer::parseInt).toList();

                    Lane enumLane = Lane.valueOf(lane.toUpperCase());

                    System.out.println(listOfEnemies);
                    System.out.println(listOfWinRates);
                    System.out.println(listOfGames);

                    for(int i = 0; i<listOfWinRates.size();i++){

                        Champion enemy = championRepository.findByName(listOfEnemies.get(i));

                        Matchup newMatchup = new Matchup(null,champion,enemy,enumLane,listOfWinRates.get(i),listOfGames.get(i));

                        matchupRepository.save(newMatchup);

                    }

                } catch(IOException e){
                    e.printStackTrace();
                }

            }

        }

    }
}
