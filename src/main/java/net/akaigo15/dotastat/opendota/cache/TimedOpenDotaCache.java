package net.akaigo15.dotastat.opendota.cache;

import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;

@Component
public class TimedOpenDotaCache implements OpenDotaCache {

  private DotaCacheConfig config;
  private Map<Integer, TimedCachedObject<PlayerHeroInfo>> playerHeroInfoMap;
  private Map<Integer, TimedCachedObject<TeamHeroInfo>> teamHeroInfoMap;
  private Map<Integer, TimedCachedObject<TeamMatchInfo>> teamMatchInfoMap;


  @Autowired
  public TimedOpenDotaCache(DotaCacheConfig config) {
    this.config = config;
    playerHeroInfoMap = new HashMap<>();
    teamHeroInfoMap = new HashMap<>();
    teamMatchInfoMap = new HashMap<>();
  }

  @Override
  public void addPlayerHeroInfo(List<PlayerHeroInfo> playerHeroInfoList, int steam32Id) {
    playerHeroInfoMap.put(steam32Id, new TimedCachedObject<>(playerHeroInfoList));
  }

  @Override
  public void addPlayerHeroInfo(List<PlayerHeroInfo> playerHeroInfoList, int steam32Id, int patch) {
    
  }

  @Override
  public void addTeamHeroInfo(List<TeamHeroInfo> teamHeroInfoList, int teamId) {
    teamHeroInfoMap.put(teamId, new TimedCachedObject<>(teamHeroInfoList));
  }

  @Override
  public void addTeamMatchInfo(List<TeamMatchInfo> teamMatchInfoList, int teamId) {
    teamMatchInfoMap.put(teamId, new TimedCachedObject<>(teamMatchInfoList));
  }

  @Override
  public Optional<List<PlayerHeroInfo>> getPlayerHeroInfo(int steam32Id) {
    TimedCachedObject<PlayerHeroInfo> cachedObject = playerHeroInfoMap.get(steam32Id);

    if(cachedObject == null) {
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      return Optional.of(cachedObject.getCashedData());
    }

    return Optional.empty();
  }

  @Override
  public Optional<List<PlayerHeroInfo>> getPlayerHeroInfo(int steam32Id, int patch) {
    return Optional.empty();
  }

  @Override
  public Optional<List<TeamHeroInfo>> getTeamHeroInfo(int teamId) {
    TimedCachedObject<TeamHeroInfo> cachedObject = teamHeroInfoMap.get(teamId);

    if(cachedObject == null) {
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      return Optional.of(cachedObject.getCashedData());
    }

    return Optional.empty();
  }

  @Override
  public Optional<List<TeamMatchInfo>> getTeamMatchInfo(int teamId) {
    TimedCachedObject<TeamMatchInfo> cachedObject = teamMatchInfoMap.get(teamId);

    if(cachedObject == null) {
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      return Optional.of(cachedObject.getCashedData());
    }

    return Optional.empty();
  }

  private boolean timeCheck(Date timeCached) {
    long nowInMili = Date.from(Instant.now()).getTime();
    long cachedInMili = timeCached.getTime();

    if( (nowInMili - cachedInMili) > config.getTimeToLiveSeconds()*1000) {
      return false;
    }

    return true;
  }
}
