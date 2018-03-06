package net.akaigo15.dotastat.controller;

import net.akaigo15.dotastat.hero.Hero;

import java.util.List;

public class PlayerHeroParams {
  private int steam32Id;
  private Integer patch;
  private List<String> heroType;
  private int minimumGamesPlayed;
  private double minimumWinRate;

  public int getSteam32Id() {
    return steam32Id;
  }

  public void setSteam32Id(int steam32Id) {
    this.steam32Id = steam32Id;
  }

  public Integer getPatch() {
    return patch;
  }

  public void setPatch(Integer patch) {
    this.patch = patch;
  }

  public List<String> getHeroType() {
    return heroType;
  }

  public void setHeroType(List<String> heroType) {
    this.heroType = heroType;
  }

  public int getMinimumGamesPlayed() {
    return minimumGamesPlayed;
  }

  public void setMinimumGamesPlayed(int minimumGamesPlayed) {
    this.minimumGamesPlayed = minimumGamesPlayed;
  }

  public double getMinimumWinRate() {
    return minimumWinRate;
  }

  public void setMinimumWinRate(double minimumWinRate) {
    this.minimumWinRate = minimumWinRate;
  }

  @Override
  public String toString() {
    return "PlayerHeroParams{" +
        "steam32Id=" + steam32Id +
        ", patch=" + patch +
        ", heroType=" + heroType +
        ", minimumGamesPlayed=" + minimumGamesPlayed +
        ", minimumWinRate=" + minimumWinRate +
        '}';
  }
}
