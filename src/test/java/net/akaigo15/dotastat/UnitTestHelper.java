package net.akaigo15.dotastat;

import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;

public class UnitTestHelper {

  public static PlayerHeroInfo makePlayerHeroInfo(int heroId) {
    PlayerHeroInfo info = new PlayerHeroInfo();

    info.setGames(1);
    info.setAgainst_games(2);
    info.setAgainst_win(3);
    info.setLast_played(4);
    info.setWin(5);
    info.setWith_games(6);
    info.setWith_win(7);
    info.setHero_id(heroId);

    return info;

  }

  public static PlayerHeroInfo makePlayerHeroInfo(int heroId, int games) {
    PlayerHeroInfo info = new PlayerHeroInfo();

    info.setGames(games);
    info.setAgainst_games(2);
    info.setAgainst_win(3);
    info.setLast_played(4);
    info.setWin(5);
    info.setWith_games(6);
    info.setWith_win(7);
    info.setHero_id(heroId);

    return info;

  }

  public static TeamHeroInfo makeTeamHeroInfo(int heroId) {
    TeamHeroInfo info = new TeamHeroInfo();

    info.setHero_id(heroId);
    info.setName("test");
    info.setGames_played(1);
    info.setWins(2);

    return info;
  }

  public static TeamHeroInfo makeTeamHeroInfo(int heroId, int games_played) {
    TeamHeroInfo info = new TeamHeroInfo();

    info.setHero_id(heroId);
    info.setName("test");
    info.setGames_played(games_played);
    info.setWins(2);

    return info;
  }

  public static TeamMatchInfo makeTeamMatchInfo(int match_id) {
    TeamMatchInfo info = new TeamMatchInfo();

    info.setMatch_id(match_id);
    info.setDuration(1);
    info.setStart_time(2);
    info.setRadiant_team_id(3);
    info.setRadiant_name("Team1");
    info.setDire_team_id(4);
    info.setDire_name("Team2");
    info.setLeagueid(5);
    info.setLeague_name("League1");
    info.setSeries_id(6);
    info.setSeries_type(7);
    info.setRadiant_score(8);
    info.setDire_score(9);
    info.setRadiant_win(false);
    info.setRadiant(true);

    return info;
  }

  public static TeamMatchInfo makeTeamMatchInfo(int match_id, boolean team) {
    TeamMatchInfo info = new TeamMatchInfo();

    info.setMatch_id(match_id);
    info.setDuration(1);
    info.setStart_time(2);
    info.setRadiant_team_id(3);
    info.setRadiant_name("Team1");
    info.setDire_team_id(4);
    info.setDire_name("Team2");
    info.setLeagueid(5);
    info.setLeague_name("League1");
    info.setSeries_id(6);
    info.setSeries_type(7);
    info.setRadiant_score(8);
    info.setDire_score(9);
    info.setRadiant_win(false);
    info.setRadiant(team);

    return info;
  }
}
