package com.github.thelauro.matchupfinder.service;

import com.github.thelauro.matchupfinder.dto.ChampionDTO;
import com.github.thelauro.matchupfinder.model.Champion;
import com.github.thelauro.matchupfinder.model.Matchup;
import com.github.thelauro.matchupfinder.model.enums.Lane;
import com.github.thelauro.matchupfinder.repository.ChampionRepository;
import com.github.thelauro.matchupfinder.repository.MatchupRepository;
import jakarta.transaction.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.io.IOException;
import java.util.ArrayList;
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

    @Transactional
    public void scrapChampions(){

        Document champions;

        try {
            champions = Jsoup.connect("https://u.gg/lol/champions")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .get();

            Elements listOfChampions = champions.select("div.truncate");

            if(listOfChampions.size() == championRepository.findAll().size()){
                return;
            }

            Elements listOfIcons = champions.select("img");
            List<String> championsNames = listOfChampions.eachText();
            List<String> championsIconsUrl = listOfIcons.eachAttr("src");

            List<String> championsSplashArtUrl = new ArrayList<>();

            for(int i=0;i<championsNames.size();i++){

                String championName = championsNames.get(i).toLowerCase().replace(" ","").replace("'","");

                if(championName.equals("nunu&willump")){

                    championName = "nunu";
                }

                if(championName.equals("renataglasc")){
                    championName = "renata";
                }

                try{

                    Thread.sleep(500);

                    Document championPage = Jsoup.connect("https://u.gg/lol/champions/"+championName+"/build")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                            .get();

                    Element SplashArt = championPage.selectFirst("img.opacity-50");
                    if(SplashArt != null) {
                        championsSplashArtUrl.add(SplashArt.attr("src"));
                    }

                } catch (Exception e){

                    e.printStackTrace();
                }
            }

            System.out.println(championsSplashArtUrl.toString());

            for(int i = 0; i<championsNames.size();i++){
                ChampionDTO data = new ChampionDTO(championsNames.get(i), championsIconsUrl.get(i),championsSplashArtUrl.get(i),null);

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

        LocalDateTime scrapStart = LocalDateTime.now();

        List<Champion> champions = championRepository.findAll();
        List<String> lanes = Arrays.stream(Lane.values()).map(Enum::name).map(String::toLowerCase).toList();

        Document matchup;

        for(Champion enemy : champions){

            String enemyName = enemy.getName().replace(" ","").toLowerCase();

            if(enemyName.equals("nunu&willump")){

                enemyName = "nunu";
            }

            if(enemyName.equals("renataglasc")){
                enemyName = "renata";
            }

            for(String lane : lanes){

                try{

                    matchup = Jsoup.connect("https://u.gg/lol/champions/"+ enemyName +"/counter?role="+lane)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                            .get();

                    Elements opps = matchup.select("div.truncate");
                    Elements winRates = matchup.select("div[class='text-[12px] font-bold leading-[15px] whitespace-nowrap text-right text-accent-blue-400']");
                    Elements gamesPlayeds = matchup.select("div[class='mt-[2px] text-accent-gray-100 text-[11px] font-normal whitespace-nowrap']");

                    List<String> listOfOpps = opps.eachText();
                    List<Double> listOfWinRates = winRates.eachText().stream()
                            .map(text -> text.replace("% WR",""))
                            .map(Double::parseDouble).toList();

                    List<Integer> listOfGames = gamesPlayeds.eachText().stream()
                            .map(text -> text.replace(" games",""))
                            .map(text -> text.replace(",",""))
                            .map(Integer::parseInt).toList();

                    Lane enumLane = Lane.valueOf(lane.toUpperCase());

                    saveChampionMatchups(enemy, enumLane, listOfOpps, listOfWinRates, listOfGames);

                } catch(IOException e){
                    e.printStackTrace();
                }

            }

        }

        matchupRepository.deleteByLastUpdateBefore(scrapStart);
    }

    @Transactional
    protected void saveChampionMatchups(Champion enemy, Lane enumLane, List<String> opps, List<Double> winRates, List<Integer> gamesPlayeds){

        List<Matchup> championMatchups = new ArrayList<>();

        for(int i = 0; i<winRates.size();i++){

            Champion opp = championRepository.findByName(opps.get(i));

            Matchup newMatchup = matchupRepository.findByMyChampionAndEnemyChampionAndLane(opp, enemy,enumLane)
                    .orElseGet( ()->{

                        return new Matchup(null,opp, enemy,enumLane,0.0,0,null);
                    });

            newMatchup.setWinRate(winRates.get(i));
            newMatchup.setGamesPlayed(gamesPlayeds.get(i));
            newMatchup.setLastUpdate(LocalDateTime.now());

            championMatchups.add(newMatchup);
        }

        matchupRepository.saveAll(championMatchups);
    }
}
