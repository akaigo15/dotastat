package net.akaigo15.dotastat.logic;

import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;

import java.util.List;

public interface DotaStatService {

  List<PlayerHeroStats> filterPlayerHeroInfo(int steam32Id, Integer patch, List<Hero.Role> heroType, int minimumGamesPlayed, double minimumWinRate);
  List<TeamHeroStats> filterTeamHeroInfo(int teamId, List<Hero.Role> heroType, int minimumGamesPlayed, double minimumWinRate);
  List<TeamMatchInfo> filterTeamMatchInfo(int teamId, Integer leagueId, boolean win);
}
