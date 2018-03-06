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
    LOG.debug("Put steam user: " + steam32Id + " playerHeroInfoList into playerHeroInfoMap");
  }

  @Override
  public void addPlayerHeroPatchInfo(final List<PlayerHeroInfo> playerHeroInfoList, final int steam32Id, final int patch) {
    playerHeroInfoPatchMap.put(new IdandPatchKey(steam32Id, patch), new TimedCachedObject<>(playerHeroInfoList));
    LOG.debug("Put steam user: " + steam32Id + " playerHeroInfoList and patch : " + patch + " into playerHeroPatchInfoMap");
  }

  @Override
  public void addTeamHeroInfo(final List<TeamHeroInfo> teamHeroInfoList, final int teamId) {
    teamHeroInfoMap.put(teamId, new TimedCachedObject<>(teamHeroInfoList));
    LOG.debug("Put team: " + teamId + " teamHeroInfoList into teamHeroInfoMap");
  }

  @Override
  public void addTeamMatchInfo(final List<TeamMatchInfo> teamMatchInfoList, final int teamId) {
    teamMatchInfoMap.put(teamId, new TimedCachedObject<>(teamMatchInfoList));
    LOG.debug("Put team: " + teamId + " teamMatchInfoList into teamMatchInfoMap");
  }

  @Override
  public Optional<List<PlayerHeroInfo>> getPlayerHeroInfo(final int steam32Id) {
    TimedCachedObject<PlayerHeroInfo> cachedObject = playerHeroInfoMap.get(steam32Id);

    if(cachedObject == null) {
      LOG.debug("Steam user: " + steam32Id + " Was not found in playerHeroInfoMap.");
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      LOG.debug("Steam user: " + steam32Id + "Was found in playerHeroInfoMap and up to date.");
      return Optional.of(cachedObject.getCashedData());
    }

    LOG.debug("Steam user: " + steam32Id + " Was found in playerHeroInfoMap. Cache timed out.");
    return Optional.empty();
  }

  @Override
  public Optional<List<PlayerHeroInfo>> getPlayerHeroPatchInfo(final int steam32Id, final int patch) {
    TimedCachedObject<PlayerHeroInfo> cachedObject = playerHeroInfoPatchMap.get(new IdandPatchKey(steam32Id, patch));

    if(cachedObject == null) {
      LOG.debug("Steam user: " + steam32Id + " with patch: " + patch + " Was not found in playerHeroPatchInfoMap.");
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      LOG.debug("Steam user: " + steam32Id + "with patch: " + patch + " Was found in playerHeroPatchInfoMap and is up to date.");
      return Optional.of(cachedObject.getCashedData());
    }

    LOG.debug("Steam user: " + steam32Id + "with patch: " + patch + " Was found in playerHeroPatchInfoMap. Cache timed out.");
    return Optional.empty();
  }

  @Override
  public Optional<List<TeamHeroInfo>> getTeamHeroInfo(final int teamId) {
    TimedCachedObject<TeamHeroInfo> cachedObject = teamHeroInfoMap.get(teamId);

    if(cachedObject == null) {
      LOG.debug("Team: " + teamId + " Was not found in TeamHeroInfoMap.");
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      LOG.debug("Team: " + teamId + " Was found in TeamHeroInfoMap and is up to date.");
      return Optional.of(cachedObject.getCashedData());
    }

    LOG.debug("Team: " + teamId + " Was found in TeamHeroInfoMap. Cache timed out.");
    return Optional.empty();
  }

  @Override
  public Optional<List<TeamMatchInfo>> getTeamMatchInfo(final int teamId) {
    TimedCachedObject<TeamMatchInfo> cachedObject = teamMatchInfoMap.get(teamId);

    if(cachedObject == null) {
      LOG.debug("Team: " + teamId + " Was not found in TeamMatchInfoMap.");
      return Optional.empty();
    }

    if(timeCheck(cachedObject.getTimeSubmitted())) {
      LOG.debug("Team: " + teamId + " Was found in TeamMatchInfoMap and is up to date.");
      return Optional.of(cachedObject.getCashedData());
    }

    LOG.debug("Team: " + teamId + " Was found in TeamMatchInfoMap. Cache timed out.");
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
