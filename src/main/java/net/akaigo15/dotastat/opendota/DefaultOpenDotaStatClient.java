package net.akaigo15.dotastat.opendota;

import net.akaigo15.dotastat.opendota.cache.OpenDotaCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;

@Service
public class DefaultOpenDotaStatClient implements OpenDotaStatClient {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultOpenDotaStatClient.class);

  private static final String URL_HEROES_FOR_PLAYER = "https://api.opendota.com/api/players/%s/heroes";
  private static final String ULR_HEROES_FOR_TEAM = "https://api.opendota.com/api/teams/%s/heroes";
  private static final String ULR_MATCHES_FOR_TEAM = "https://api.opendota.com/api/teams/%s/matches";

  private static final int MAX_CALLERS_TO_OPENDOTA = 1;
  private static final int THREAD_SLEEP_TIME_MS = 1000;

  private RestTemplate restTemplate;
  private OpenDotaCache openDotaCache;
  private Semaphore semaphore;


  @Autowired
  public DefaultOpenDotaStatClient(RestTemplate restTemplate, OpenDotaCache openDotaCache) {
    this.restTemplate = restTemplate;
    this.openDotaCache = openDotaCache;
    this.semaphore = new Semaphore(MAX_CALLERS_TO_OPENDOTA, true);
  }

  @Override
  public List<PlayerHeroInfo> getHeroInfoList(final int steam32Id, final int patch) {

    Optional<List<PlayerHeroInfo>> optionalList = openDotaCache.getPlayerHeroPatchInfo(steam32Id, patch);

    if(!optionalList.isPresent()) {
      String patchUrl = "?patch=" + patch;
      String url = String.format(URL_HEROES_FOR_PLAYER,steam32Id);
      url = url+patchUrl;

      List<PlayerHeroInfo> info;

      try {
        semaphore.acquire();
        PlayerHeroInfo[] infoArray = restTemplate.getForObject(url, PlayerHeroInfo[].class);
        info = Arrays.asList(infoArray);
        LOG.debug("Call to OpenDota URL: {} returned {} items", url, info.size());
        openDotaCache.addPlayerHeroPatchInfo(info,steam32Id,patch);
      }
      catch(InterruptedException ie) {
        LOG.warn("Thread interrupted while waiting for getHeroInfoList");
        info = new ArrayList<>();
      }

      try {
        Thread.sleep(THREAD_SLEEP_TIME_MS);
      }
      catch (InterruptedException e) {
        LOG.warn("Thread interrupted while sleeping in getHeroInfoList");
      }
      
      semaphore.release();
      return info;
    }
    return optionalList.get();
  }
  
  @Override
  public List<PlayerHeroInfo> getHeroInfoList(final int steam32Id) {
    Optional<List<PlayerHeroInfo>> optionalList = openDotaCache.getPlayerHeroInfo(steam32Id);

    if(!optionalList.isPresent()) {
      String url = String.format(URL_HEROES_FOR_PLAYER,steam32Id);
      List<PlayerHeroInfo> info;

      try {
        semaphore.acquire();
        PlayerHeroInfo[] infoArray = restTemplate.getForObject(url, PlayerHeroInfo[].class);
        info = Arrays.asList(infoArray);
        LOG.debug("Call to OpenDota URL: {} returned {} items", url, info.size());
        openDotaCache.addPlayerHeroInfo(info, steam32Id);
      }
      catch(InterruptedException ie) {
        LOG.warn("Thread interrupted while waiting for getHeroInfoList");
        info = new ArrayList<>();
      }

      try {
        Thread.sleep(THREAD_SLEEP_TIME_MS);
      }
      catch (InterruptedException e) {
        LOG.warn("Thread interrupted while sleeping in getHeroInfoList");
      }

      semaphore.release();
      return info;
    }
    return optionalList.get();
  }

  @Override
  public List<TeamHeroInfo> getTeamHeroInfoList(final int teamId) {
    Optional<List<TeamHeroInfo>> optionalList = openDotaCache.getTeamHeroInfo(teamId);

    if(!optionalList.isPresent()) {
      String url = String.format(ULR_HEROES_FOR_TEAM,teamId);
      List<TeamHeroInfo> info;

      try {
        semaphore.acquire();
        TeamHeroInfo[] infoArray = restTemplate.getForObject(url, TeamHeroInfo[].class);
        info = Arrays.asList(infoArray);
        LOG.debug("Call to OpenDota URL: {} returned {} items", url, info.size());
        openDotaCache.addTeamHeroInfo(info,teamId);
      }
      catch(InterruptedException ie) {
        LOG.warn("Thread interrupted while waiting for getTeamHeroInfoList");
        info = new ArrayList<>();
      }

      try {
        Thread.sleep(THREAD_SLEEP_TIME_MS);
      }
      catch (InterruptedException e) {
        LOG.warn("Thread interrupted while sleeping in getTeamHeroInfoList");
      }

      semaphore.release();
      return info;
    }
    return optionalList.get();
  }

  @Override
  public List<TeamMatchInfo> getTeamMatchInfoList(final int teamId) {
    Optional<List<TeamMatchInfo>> optionalList = openDotaCache.getTeamMatchInfo(teamId);

    if(!optionalList.isPresent()) {
      String url = String.format(ULR_MATCHES_FOR_TEAM,teamId);
      List<TeamMatchInfo> info;

      try {
        semaphore.acquire();
        TeamMatchInfo[] infoArray = restTemplate.getForObject(url, TeamMatchInfo[].class);
        info = Arrays.asList(infoArray);
        LOG.debug("Call to OpenDota URL: {} returned {} items", url, info.size());
        openDotaCache.addTeamMatchInfo(info, teamId);
      }
      catch(InterruptedException ie) {
        LOG.warn("Thread interrupted while sleeping in getTeamMatchInfoList");
        info = new ArrayList<>();
      }

      try {
        Thread.sleep(THREAD_SLEEP_TIME_MS);
      }
      catch (InterruptedException e) {
        LOG.warn("Thread interrupted while sleeping in getTeamHeroInfoList");
      }

      semaphore.release();
      return info;
    }
    return optionalList.get();
  }

}
