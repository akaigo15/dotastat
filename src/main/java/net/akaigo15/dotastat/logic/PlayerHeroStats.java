package net.akaigo15.dotastat.logic;

import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.opendota.PlayerHeroInfo;

public class PlayerHeroStats {
  private PlayerHeroInfo playerHeroInfo;
  private Hero hero;

  public PlayerHeroStats(PlayerHeroInfo playerHeroInfo, Hero hero) {
    this.playerHeroInfo = playerHeroInfo;
    this.hero = hero;
  }

  public PlayerHeroInfo getPlayerHeroInfo() {
    return playerHeroInfo;
  }

  public Hero getHero() {
    return hero;
  }

}
