package net.akaigo15.dotastat.logic;

import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;

public class TeamHeroStats {
  private Hero hero;
  private TeamHeroInfo teamHeroInfo;

  public TeamHeroStats(TeamHeroInfo teamHeroInfo, Hero hero) {
    this.hero = hero;
    this.teamHeroInfo = teamHeroInfo;
  }

  public Hero getHero() {
    return hero;
  }

  public TeamHeroInfo getTeamHeroInfo() {
    return teamHeroInfo;
  }

}
