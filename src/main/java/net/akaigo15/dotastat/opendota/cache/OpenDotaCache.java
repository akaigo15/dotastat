package net.akaigo15.dotastat.opendota.cache;

import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;

import java.util.List;
import java.util.Optional;

public interface OpenDotaCache {
  void addPlayerHeroInfo(List<PlayerHeroInfo> playerHeroInfoList, int steam32Id);
  void addPlayerHeroPatchInfo(List<PlayerHeroInfo> playerHeroInfoList, int steam32Id, int patch);
  void addTeamHeroInfo(List<TeamHeroInfo> teamHeroInfoList, int teamId);
  void addTeamMatchInfo(List<TeamMatchInfo> teamMatchInfoList, int teamId);

  Optional<List<PlayerHeroInfo>> getPlayerHeroInfo(int steam32Id);
  Optional<List<PlayerHeroInfo>> getPlayerHeroPatchInfo(int steam32Id, int patch);
  Optional<List<TeamHeroInfo>> getTeamHeroInfo(int teamId);
  Optional<List<TeamMatchInfo>> getTeamMatchInfo(int teamId);
}
