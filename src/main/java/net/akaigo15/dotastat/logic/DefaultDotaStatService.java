package net.akaigo15.dotastat.logic;

import ch.qos.logback.core.db.dialect.SybaseSqlAnywhereDialect;
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
  public List<PlayerHeroStats> filterPlayerHeroInfo(final int steam32Id, final Integer patch, final List<Hero.Role> heroType, final int minimumGamesPlayed, final double minimumWinRate) {
    List<PlayerHeroInfo> rawList;

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
          .filter(s -> (calcWinRate(s.getGames(),s.getWin()) >= minimumWinRate))
          .map(s -> new PlayerHeroStats(s, heroData.getHero(s.getHero_id())))
          .collect(Collectors.toList());

    }
    LOG.debug("heroType has: {} values",heroType.size());
    return rawList.stream()
        .filter(s -> hasRole(heroType,getHeroType(s.getHero_id())))
        .filter(s -> s.getGames() >= minimumGamesPlayed)
        .filter(s -> (((double) (s.getWin()) / (double) (s.getGames())) >= minimumWinRate))
        .map(s -> new PlayerHeroStats(s, heroData.getHero(s.getHero_id())))
        .collect(Collectors.toList());
  }

  @Override
  public List<TeamHeroStats> filterTeamHeroInfo(final int teamId, final List<Hero.Role> heroType, final int minimumGamesPlayed, final double minimumWinRate) {
    List<TeamHeroInfo> rawList = openDotaStatClient.getTeamHeroInfoList(teamId);

    if (heroType == null || heroType.size() == 0) {
      return rawList.stream()
          .filter(s -> s.getGames_played() >= minimumGamesPlayed)
          .filter(s -> ((s.getWins() / s.getGames_played()) >= minimumWinRate))
          .map(s -> new TeamHeroStats(s,heroData.getHero(s.getHero_id())))
          .collect(Collectors.toList());

    }
    return rawList.stream()
        .filter(s -> hasRole(heroType,getHeroType(s.getHero_id())))
        .peek(s -> LOG.debug("Hero id: {} passed role test",s.getHero_id()))
        .filter(s -> s.getGames_played() >= minimumGamesPlayed)
        .filter(s -> ((s.getWins() / s.getGames_played()) >= minimumWinRate))
        .map(s -> new TeamHeroStats(s,heroData.getHero(s.getHero_id())))
        .collect(Collectors.toList());
  }

  @Override
  public List<TeamMatchInfo> filterTeamMatchInfo(final int teamId, final Integer leagueId, final boolean win) {
    List<TeamMatchInfo> rawList = openDotaStatClient.getTeamMatchInfoList(teamId);

    if(leagueId == null) {
      return rawList.stream()
          .filter(s -> winningTeam(teamId,s.getRadiant_team_id(),s.getDire_team_id(),s.isRadiant_win()))
          .collect(Collectors.toList());
    } else {
      return rawList.stream()
          .filter(s -> winningTeam(teamId,s.getRadiant_team_id(),s.getDire_team_id(),s.isRadiant_win()))
          .filter(s -> leagueId == s.getLeagueid())
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
        LOG.debug("{} is found within the requiredRoleList",r);
        return true;
      }
      LOG.debug("{} was not found in requiredRoleList",r);
    }
    LOG.debug("Hero did not meet the requiredRoleList");
    return false;
  }

  private boolean spy(int value) {
    LOG.debug("Stream spy value: {}", value);
    return true;
  }

  private double calcWinRate(final int gamesPlayed, final int wins) {
    return (double) wins / (double) gamesPlayed;
  }
}
