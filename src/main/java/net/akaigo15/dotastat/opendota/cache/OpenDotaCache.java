package net.akaigo15.dotastat.opendota.cache;

import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;

import java.util.List;
import java.util.Optional;

public interface OpenDotaCache {
  void addPlayerHeroInfo(final List<PlayerHeroInfo> playerHeroInfoList, final int steam32Id);
  void addPlayerHeroPatchInfo(final List<PlayerHeroInfo> playerHeroInfoList, final int steam32Id, int patch);
  void addTeamHeroInfo(final List<TeamHeroInfo> teamHeroInfoList, final int teamId);
  void addTeamMatchInfo(final List<TeamMatchInfo> teamMatchInfoList, final int teamId);

  Optional<List<PlayerHeroInfo>> getPlayerHeroInfo(final int steam32Id);
  Optional<List<PlayerHeroInfo>> getPlayerHeroPatchInfo(final int steam32Id, final int patch);
  Optional<List<TeamHeroInfo>> getTeamHeroInfo(final int teamId);
  Optional<List<TeamMatchInfo>> getTeamMatchInfo(final int teamId);
}
