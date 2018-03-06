package net.akaigo15.dotastat.opendota;

import net.akaigo15.dotastat.opendota.cache.OpenDotaCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultOpenDotaStatClient implements OpenDotaStatClient {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultOpenDotaStatClient.class);

  private static final String URL_HEROES_FOR_PLAYER = "https://api.opendota.com/api/players/%s/heroes";
  private static final String ULR_HEROES_FOR_TEAM = " https://api.opendota.com/api/teams/%s/heroes";
  private static final String ULR_MATCHES_FOR_TEAM = " https://api.opendota.com/api/teams/%s/matches";


  private RestTemplate restTemplate;
  private OpenDotaCache openDotaCache;


  @Autowired
  public DefaultOpenDotaStatClient(RestTemplate restTemplate, OpenDotaCache openDotaCache) {
    this.restTemplate = restTemplate;
    this.openDotaCache = openDotaCache;
  }

  @Override
  public List<PlayerHeroInfo> getHeroInfoList(final int steam32Id, final int patch) {

    Optional<List<PlayerHeroInfo>> optionalList = openDotaCache.getPlayerHeroPatchInfo(steam32Id, patch);

    if(!optionalList.isPresent()) {
      String patchUrl = "?patch=" + patch;
      String url = String.format(URL_HEROES_FOR_PLAYER,steam32Id);
      url = url+patchUrl;
      PlayerHeroInfo[] infoArray = restTemplate.getForObject(url, PlayerHeroInfo[].class);
      List<PlayerHeroInfo> info = Arrays.asList(infoArray);
      LOG.debug("Call to OpenDota URL: {} returned {} items", url, info.size());

      return info;
    }
    return optionalList.get();
  }
  
  @Override
  public List<PlayerHeroInfo> getHeroInfoList(final int steam32Id) {
    Optional<List<PlayerHeroInfo>> optionalList = openDotaCache.getPlayerHeroInfo(steam32Id);

    if(!optionalList.isPresent()) {
      String url = String.format(URL_HEROES_FOR_PLAYER,steam32Id);
      PlayerHeroInfo[] infoArray = restTemplate.getForObject(url, PlayerHeroInfo[].class);
      List<PlayerHeroInfo> info = Arrays.asList(infoArray);
      LOG.debug("Call to OpenDota URL: {} returned {} items", url, info.size());
      openDotaCache.addPlayerHeroInfo(info, steam32Id);

      return info;
    }
    return optionalList.get();
  }

  @Override
  public List<TeamHeroInfo> getTeamHeroInfoList(final int teamId) {
    Optional<List<TeamHeroInfo>> optionalList = openDotaCache.getTeamHeroInfo(teamId);

    if(!optionalList.isPresent()) {
      String url = String.format(ULR_HEROES_FOR_TEAM,teamId);
      TeamHeroInfo[] infoArray = restTemplate.getForObject(url, TeamHeroInfo[].class);
      List<TeamHeroInfo> info = Arrays.asList(infoArray);
      LOG.debug("Call to OpenDota URL: {} returned {} items", url, info.size());
      openDotaCache.addTeamHeroInfo(info,teamId);

      return info;
    }
    return optionalList.get();

  }

  @Override
  public List<TeamMatchInfo> getTeamMatchInfoList(final int teamId) {
    Optional<List<TeamMatchInfo>> optionalList = openDotaCache.getTeamMatchInfo(teamId);

    if(!optionalList.isPresent()) {
      String url = String.format(ULR_MATCHES_FOR_TEAM,teamId);
      TeamMatchInfo[] infoArray = restTemplate.getForObject(url, TeamMatchInfo[].class);
      List<TeamMatchInfo> info = Arrays.asList(infoArray);
      LOG.debug("Call to OpenDota URL: {} returned {} items", url, info.size());
      openDotaCache.addTeamMatchInfo(info, teamId);

      return info;
    }

    return optionalList.get();
  }

}
