package net.akaigo15.dotastat.opendota;

import java.util.List;

public interface OpenDotaStatClient {

  List<PlayerHeroInfo> getHeroInfoList(final int steam32Id, final int patch);
  List<PlayerHeroInfo> getHeroInfoList(final int steam32Id);

  List<TeamHeroInfo> getTeamHeroInfoList(final int teamId);

  List<TeamMatchInfo> getTeamMatchInfoList(final int teamId);

  

}
