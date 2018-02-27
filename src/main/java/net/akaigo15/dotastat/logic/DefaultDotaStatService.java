package net.akaigo15.dotastat.logic;

import net.akaigo15.dotastat.opendota.OpenDotaStatClient;
import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultDotaStatService implements DotaStatService {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultDotaStatService.class);


  private OpenDotaStatClient openDotaStatClient;



  @Autowired
  public DefaultDotaStatService(OpenDotaStatClient openDotaStatClient) {
    this.openDotaStatClient = openDotaStatClient;

  }

  @Override
  public List<PlayerHeroInfo> filterPlayerHeroInfo(int steam32Id, Integer patch, Integer heroType, int minimumGamesPlayed, int minimumWinRate) {
    List<PlayerHeroInfo> rawList;

    if(patch == null) {
      rawList = openDotaStatClient.getHeroInfoList(steam32Id);
      LOG.debug("filterPlayerHeroInfo with steam id: {}",steam32Id);
    }
    else {
      rawList = openDotaStatClient.getHeroInfoList(steam32Id, patch);
      LOG.debug("filterPlayerHeroInfo with steam id: {} and patch: {}",steam32Id,patch);
    }

    return rawList.stream()
        .filter(s -> s.getGames() >= minimumGamesPlayed)
        .filter(s -> ((s.getWin() / s.getGames()) >= minimumWinRate))
        .collect(Collectors.toList());
  }

  @Override
  public List<TeamHeroInfo> filterTeamHeroInfo(int teamId, int heroType, int minimumGamesPlayed, double minimumWinRate) {
    return null;
  }

  @Override
  public List<TeamMatchInfo> filterTeamMatchInfo(int teamId, Integer leagueId, boolean win) {
    return null;
  }
}
