package net.akaigo15.dotastat.opendota.cache;

import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TimedOpenDotaCache implements OpenDotaCache {
  private static final Logger LOG = LoggerFactory.getLogger(TimedOpenDotaCache.class);

  private DotaCacheConfig config;
  private Map<Integer, TimedCachedObject<PlayerHeroInfo>> playerHeroInfoMap;
  private Map<IdandPatchKey, TimedCachedObject<PlayerHeroInfo>> playerHeroInfoPatchMap;
  private Map<Integer, TimedCachedObject<TeamHeroInfo>> teamHeroInfoMap;
  private Map<Integer, TimedCachedObject<TeamMatchInfo>> teamMatchInfoMap;


  @Autowired
  public TimedOpenDotaCache(DotaCacheConfig config) {
    this.config = config;
    playerHeroInfoMap = new ConcurrentHashMap<>();
    playerHeroInfoPatchMap = new ConcurrentHashMap<>();
    teamHeroInfoMap = new ConcurrentHashMap<>();
    teamMatchInfoMap = new ConcurrentHashMap<>();
  }

  @Override
  public void addPlayerHeroInfo(final List<PlayerHeroInfo> playerHeroInfoList, final int steam32Id) {
    playerHeroInfoMap.put(steam32Id, new TimedCachedObject<>(playerHeroInfoList));
    LOG.debug("Put steam user: {} playerHeroInfoList into playerHeroInfoMap",steam32Id);
  }

  @Override
  public void addPlayerHeroPatchInfo(final List<PlayerHeroInfo> playerHeroInfoList, final int steam32Id, final int patch) {
    playerHeroInfoPatchMap.put(new IdandPatchKey(steam32Id, patch), new TimedCachedObject<>(playerHeroInfoList));
    LOG.debug("Put steam user: {} playerHeroInfoList and patch : {} into playerHeroPatchInfoMap",steam32Id,patch);
  }

  @Override
  public void addTeamHeroInfo(final List<TeamHeroInfo> teamHeroInfoList, final int teamId) {
    teamHeroInfoMap.put(teamId, new TimedCachedObject<>(teamHeroInfoList));
    LOG.debug("Put team: {} teamHeroInfoList into teamHeroInfoMap",teamId);
  }

  @Override
  public void addTeamMatchInfo(final List<TeamMatchInfo> teamMatchInfoList, final int teamId) {
    teamMatchInfoMap.put(teamId, new TimedCachedObject<>(teamMatchInfoList));
    LOG.debug("Put team: {} teamMatchInfoList into teamMatchInfoMap",teamId);
  }

  @Override
  public Optional<List<PlayerHeroInfo>> getPlayerHeroInfo(final int steam32Id) {
    TimedCachedObject<PlayerHeroInfo> cachedObject = playerHeroInfoMap.get(steam32Id);

    if(cachedObject == null) {
      LOG.debug("Steam user: {} was not found in playerHeroInfoMap.",steam32Id);
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      LOG.debug("Steam user: {} was found in playerHeroInfoMap and up to date.",steam32Id);
      return Optional.of(cachedObject.getCashedData());
    }

    LOG.debug("Steam user: {} was found in playerHeroInfoMap. Cache timed out.",steam32Id);
    return Optional.empty();
  }

  @Override
  public Optional<List<PlayerHeroInfo>> getPlayerHeroPatchInfo(final int steam32Id, final int patch) {
    TimedCachedObject<PlayerHeroInfo> cachedObject = playerHeroInfoPatchMap.get(new IdandPatchKey(steam32Id, patch));

    if(cachedObject == null) {
      LOG.debug("Steam user: {} with patch: {} Was not found in playerHeroPatchInfoMap.",steam32Id,patch);
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      LOG.debug("Steam user: {} with patch: {} Was found in playerHeroPatchInfoMap and is up to date.",steam32Id,patch);
      return Optional.of(cachedObject.getCashedData());
    }

    LOG.debug("Steam user: {} with patch: {} Was found in playerHeroPatchInfoMap. Cache timed out.",steam32Id,patch);
    return Optional.empty();
  }

  @Override
  public Optional<List<TeamHeroInfo>> getTeamHeroInfo(final int teamId) {
    TimedCachedObject<TeamHeroInfo> cachedObject = teamHeroInfoMap.get(teamId);

    if(cachedObject == null) {
      LOG.debug("Team: {} Was not found in TeamHeroInfoMap.",teamId);
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      LOG.debug("Team: {} Was found in TeamHeroInfoMap and is up to date.",teamId);
      return Optional.of(cachedObject.getCashedData());
    }

    LOG.debug("Team: {} Was found in TeamHeroInfoMap. Cache timed out.",teamId);
    return Optional.empty();
  }

  @Override
  public Optional<List<TeamMatchInfo>> getTeamMatchInfo(final int teamId) {
    TimedCachedObject<TeamMatchInfo> cachedObject = teamMatchInfoMap.get(teamId);

    if(cachedObject == null) {
      LOG.debug("Team: {} Was not found in TeamMatchInfoMap.",teamId);
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      LOG.debug("Team: {} Was found in TeamMatchInfoMap and is up to date.",teamId);
      return Optional.of(cachedObject.getCashedData());
    }

    LOG.debug("Team: {} Was found in TeamMatchInfoMap. Cache timed out.",teamId);
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
