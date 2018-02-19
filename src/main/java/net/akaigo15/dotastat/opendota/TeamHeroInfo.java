package net.akaigo15.dotastat.opendota;

public class TeamHeroInfo {
  private int hero_id;
  private String name;
  private int games_played;
  private int wins;

  public int getHero_id() {
    return hero_id;
  }

  public void setHero_id(int hero_id) {
    this.hero_id = hero_id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getGames_played() {
    return games_played;
  }

  public void setGames_played(int games_played) {
    this.games_played = games_played;
  }

  public int getWins() {
    return wins;
  }

  public void setWins(int wins) {
    this.wins = wins;
  }
}
