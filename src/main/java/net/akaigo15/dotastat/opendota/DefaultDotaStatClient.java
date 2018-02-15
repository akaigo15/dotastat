package net.akaigo15.dotastat.opendota;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
public class DefaultDotaStatClient implements DotaStatClient {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultDotaStatClient.class);

  private static final String URL_HEROS_FOR_PLAYER = "https://api.opendota.com/api/players/%s/heroes";

  private RestTemplate restTemplate;


  @Autowired
  public DefaultDotaStatClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<PlayerHeroInfo> getHeroInfoList(int steam32Id) {
    String url = String.format(URL_HEROS_FOR_PLAYER,steam32Id);
    PlayerHeroInfo[] infoArray = restTemplate.getForObject(url, PlayerHeroInfo[].class);
    List<PlayerHeroInfo> info = Arrays.asList(infoArray);
    LOG.debug("Call to OpenDota URL: {} returned {} items", url, info.size());

    return info;
  }
}
