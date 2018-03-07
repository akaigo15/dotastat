package net.akaigo15.dotastat.controller;

import net.akaigo15.dotastat.hero.Hero;

import java.util.List;

public class TeamHeroParams {
  private int teamId;
  private List<String> heroType;
  private int minimumGamesPlayed;
  private double minimumWinRate;

  public int getTeamId() {
    return teamId;
  }

  public void setTeamId(int teamId) {
    this.teamId = teamId;
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
    return "TeamHeroParams{" +
        "teamId=" + teamId +
        ", heroType=" + heroType +
        ", minimumGamesPlayed=" + minimumGamesPlayed +
        ", minimumWinRate=" + minimumWinRate +
        '}';
  }
}
