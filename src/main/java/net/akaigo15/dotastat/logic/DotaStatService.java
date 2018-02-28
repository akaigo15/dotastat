package net.akaigo15.dotastat.logic;

import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;

import java.util.List;

public interface DotaStatService {

  List<PlayerHeroStats> filterPlayerHeroInfo(final int steam32Id, final Integer patch, final List<Hero.Role> heroType, final int minimumGamesPlayed, final double minimumWinRate);
  List<TeamHeroStats> filterTeamHeroInfo(final int teamId, List<Hero.Role> heroType, final int minimumGamesPlayed, final double minimumWinRate);
  List<TeamMatchInfo> filterTeamMatchInfo(final int teamId, final Integer leagueId, final boolean win);
}
