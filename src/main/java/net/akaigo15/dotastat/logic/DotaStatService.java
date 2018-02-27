package net.akaigo15.dotastat.logic;

import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;

import java.util.List;

public interface DotaStatService {

  List<PlayerHeroInfo> filterPlayerHeroInfo(int steam32Id, Integer patch, Integer heroType, int minimumGamesPlayed, int minimumWinRate);
  List<TeamHeroInfo> filterTeamHeroInfo(int teamId, int heroType, int minimumGamesPlayed, double minimumWinRate);
  List<TeamMatchInfo> filterTeamMatchInfo(int teamId, Integer leagueId, boolean win);
}
