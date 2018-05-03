package net.akaigo15.dotastat.controller;

import net.akaigo15.dotastat.logic.PlayerHeroStats;

import java.util.List;

public class PlayerHeroExportInfo {
  private String name;
  private int id;
  private List<PlayerHeroStats> playerHeroStatsList;



  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public List<PlayerHeroStats> getPlayerHeroStatsList() {
    return playerHeroStatsList;
  }

  @Override
  public String toString() {
    return "PlayerHeroExportInfo{" +
        "name='" + name + '\'' +
        ", id=" + id +
        ", playerHeroInfoList=" + playerHeroStatsList +
        '}';
  }

  public PlayerHeroExportInfo(String name, int id, List<PlayerHeroStats> playerHeroStatsList) {
    this.name = name;
    this.id = id;
    this.playerHeroStatsList = playerHeroStatsList;
  }
}
