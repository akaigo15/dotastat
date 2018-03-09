package net.akaigo15.dotastat.logic;

import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.hero.HeroData;
import net.akaigo15.dotastat.opendota.OpenDotaStatClient;
import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultDotaStatService implements DotaStatService {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultDotaStatService.class);


  private OpenDotaStatClient openDotaStatClient;
  private HeroData heroData;



  @Autowired
  public DefaultDotaStatService(OpenDotaStatClient openDotaStatClient, HeroData heroData) {
    this.openDotaStatClient = openDotaStatClient;
    this.heroData = heroData;

  }

  @Override
  public List<PlayerHeroStats> filterPlayerHeroInfo(final int steam32Id, final Integer patch, final List<Hero.Role> heroType, final int minimumGamesPlayedInput, final double minimumWinRate) {
    List<PlayerHeroInfo> rawList;

    final int minimumGamesPlayed;

    if(minimumGamesPlayedInput == 0 || minimumGamesPlayedInput < 1) {
      minimumGamesPlayed = 1;
    } else {
      minimumGamesPlayed = minimumGamesPlayedInput;
    }

    if(patch == null) {
      rawList = openDotaStatClient.getHeroInfoList(steam32Id);
      LOG.debug("filterPlayerHeroInfo with steam id: {}",steam32Id);
    }
    else {
      rawList = openDotaStatClient.getHeroInfoList(steam32Id, patch);
      LOG.debug("filterPlayerHeroInfo with steam id: {} and patch: {}",steam32Id,patch);
    }

    if(heroType == null || heroType.size() == 0) {
      LOG.debug("heroType is null or empty");
      return rawList.stream()
          .peek(s-> LOG.trace("Filtering element: {}",s))
          .filter(s -> s.getGames() >= minimumGamesPlayed)
          .peek(s -> LOG.trace("Hero id: {} passed minimumGamesPlayed test",s.getHero_id()))
          .filter(s -> (calcWinRate(s.getGames(),s.getWin()) >= minimumWinRate))
          .peek(s -> LOG.trace("Hero id: {} passed minimumWinRate test",s.getHero_id()))
          .map(s -> new PlayerHeroStats(s, heroData.getHero(s.getHero_id())))
          .collect(Collectors.toList());

    }
    LOG.debug("heroType has: {} values",heroType.size());
    return rawList.stream()
        .peek(s-> LOG.trace("Filtering element: {}",s))
        .filter(s -> hasRole(heroType,getHeroType(s.getHero_id())))
        .peek(s -> LOG.trace("Hero id: {} passed role test",s.getHero_id()))
        .filter(s -> s.getGames() >= minimumGamesPlayed)
        .peek(s -> LOG.trace("Hero id: {} passed minimumGamesPlayed test",s.getHero_id()))
        .filter(s -> (calcWinRate(s.getGames(),s.getWin()) >= minimumWinRate))
        .peek(s -> LOG.trace("Hero id: {} passed minimumWinRate test",s.getHero_id()))
        .map(s -> new PlayerHeroStats(s, heroData.getHero(s.getHero_id())))
        .collect(Collectors.toList());
  }

  @Override
  public List<TeamHeroStats> filterTeamHeroInfo(final int teamId, final List<Hero.Role> heroType, final int minimumGamesPlayedInput, final double minimumWinRate) {
    List<TeamHeroInfo> rawList = openDotaStatClient.getTeamHeroInfoList(teamId);

    final int minimumGamesPlayed;

    if(minimumGamesPlayedInput == 0 || minimumGamesPlayedInput < 1) {
      minimumGamesPlayed = 1;
    } else {
      minimumGamesPlayed = minimumGamesPlayedInput;
    }

    if (heroType == null || heroType.size() == 0) {
      return rawList.stream()
          .peek(s-> LOG.trace("Filtering element: {}",s))
          .filter(s -> s.getGames_played() >= minimumGamesPlayed)
          .peek(s -> LOG.trace("Hero id: {} passed minimumGamesPlayed test",s.getHero_id()))
          .filter(s -> (calcWinRate(s.getGames_played(),s.getWins()) >= minimumWinRate))
          .peek(s -> LOG.trace("Hero id: {} passed minimumWinRate test",s.getHero_id()))
          .map(s -> new TeamHeroStats(s,heroData.getHero(s.getHero_id())))
          .collect(Collectors.toList());

    }
    return rawList.stream()
        .peek(s-> LOG.trace("Filtering element: {}",s))
        .filter(s -> hasRole(heroType,getHeroType(s.getHero_id())))
        .peek(s -> LOG.trace("Hero id: {} passed role test",s.getHero_id()))
        .filter(s -> s.getGames_played() >= minimumGamesPlayed)
        .peek(s -> LOG.trace("Hero id: {} passed minimumGamesPlayed test",s.getHero_id()))
        .filter(s -> (calcWinRate(s.getGames_played(),s.getWins()) >= minimumWinRate))
        .peek(s -> LOG.trace("Hero id: {} passed minimumWinRate test",s.getHero_id()))
        .map(s -> new TeamHeroStats(s,heroData.getHero(s.getHero_id())))
        .collect(Collectors.toList());
  }

  @Override
  public List<TeamMatchInfo> filterTeamMatchInfo(final int teamId, final Integer leagueId, final boolean win) {
    List<TeamMatchInfo> rawList = openDotaStatClient.getTeamMatchInfoList(teamId);

    if(leagueId == null) {
      return rawList.stream()
          .peek(s-> LOG.trace("Filtering element: {}",s))
          .filter(s -> winningTeam(teamId,s.getRadiant_team_id(),s.getDire_team_id(),s.isRadiant_win()))
          .peek(s -> LOG.trace("Match id: {} passed winningTeam test",s.getMatch_id()))
          .collect(Collectors.toList());
    } else {
      return rawList.stream()
          .peek(s-> LOG.trace("Filtering element: {}",s))
          .filter(s -> winningTeam(teamId,s.getRadiant_team_id(),s.getDire_team_id(),s.isRadiant_win()))
          .peek(s -> LOG.trace("Match id: {} passed winningTeam test",s.getMatch_id()))
          .filter(s -> leagueId == s.getLeagueid())
          .peek(s -> LOG.trace("Match id: {} passed leagueId test",s.getMatch_id()))
          .collect(Collectors.toList());
    }

  }

  private List<Hero.Role> getHeroType(final int id) {
    return heroData.getHero(id).getRole();
  }

  private boolean winningTeam(final int teamId, final int radiantId, final int direId, final boolean radiantWin) {
    if(teamId == radiantId && radiantWin) {
      return true;
    }
    else if(teamId == direId && !radiantWin) {
      return true;
    }

    return false;
  }

  private boolean hasRole(final List<Hero.Role> requiredRoleList, final List<Hero.Role> heroRoleList) {
    for(Hero.Role r : requiredRoleList) {
      if(heroRoleList.contains(r)) {
        LOG.trace("{} is found within the requiredRoleList",r);
        return true;
      }
      LOG.trace("{} was not found in requiredRoleList",r);
    }
    LOG.trace("Hero did not meet the requiredRoleList");
    return false;
  }

  private double calcWinRate(final int gamesPlayed, final int wins) {
    return ((double) wins) / ((double) gamesPlayed);
  }
}
