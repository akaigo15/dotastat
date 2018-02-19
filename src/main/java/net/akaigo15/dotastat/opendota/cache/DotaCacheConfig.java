package net.akaigo15.dotastat.opendota.cache;

public class DotaCacheConfig {
  private int timeToLiveSeconds;

  public DotaCacheConfig(int timeToLive) {
    this.timeToLiveSeconds = timeToLive;
  }

  public int getTimeToLiveSeconds() {
    return timeToLiveSeconds;
  }
}
