package net.akaigo15.dotastat.opendota;

import java.util.List;

public interface DotaStatClient {

  List<PlayerHeroInfo> getHeroInfoList(final int steam32Id);

}
