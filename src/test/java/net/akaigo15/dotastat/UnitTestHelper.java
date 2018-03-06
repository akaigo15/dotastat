package net.akaigo15.dotastat;

import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UnitTestHelper {
  private static final Logger LOG = LoggerFactory.getLogger(UnitTestHelper.class);

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

  public static PlayerHeroInfo makePlayerHeroInfo(int heroId, int games, int wins) {
    PlayerHeroInfo info = new PlayerHeroInfo();

    info.setGames(games);
    info.setAgainst_games(2);
    info.setAgainst_win(3);
    info.setLast_played(4);
    info.setWin(wins);
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
    info.setWins(5);

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

  public static TeamMatchInfo makeTeamMatchInfo(int match_id, int teamId, int leagueId) {

    TeamMatchInfo info = new TeamMatchInfo();

    info.setMatch_id(match_id);
    info.setDuration(1);
    info.setStart_time(2);
    info.setRadiant_team_id(3);
    info.setRadiant_name("Team1");
    info.setDire_team_id(teamId);
    info.setDire_name("Team2");
    info.setLeagueid(leagueId);
    info.setLeague_name("League1");
    info.setSeries_id(6);
    info.setSeries_type(7);
    info.setRadiant_score(8);
    info.setDire_score(9);
    info.setRadiant_win(false);
    info.setRadiant(true);

    return info;
  }

  public static List<Hero> makeSampleHeros() {
    List<Hero> list = new ArrayList<>();
    List<Hero.Role> roleList = new ArrayList<>();

    for(int i = 0; i < 6; i++ ) {

      switch(i) {
        case 0:
          List<Hero.Role> roleList0 = new ArrayList<>();
          roleList0.add(Hero.Role.CORE_1);
          roleList0.add(Hero.Role.CORE_2);

          Hero hero0 = new Hero(roleList0,i+1,"TestCore 1");
          list.add(hero0);
          break;

        case 1:
          List<Hero.Role> roleList1 = new ArrayList<>();
          roleList1.add(Hero.Role.CORE_2);
          roleList1.add(Hero.Role.CORE_3);

          Hero hero1 = new Hero(roleList1,i+1,"TestCore 2");
          list.add(hero1);
          break;

        case 2:
          List<Hero.Role> roleList2 = new ArrayList<>();
          roleList2.add(Hero.Role.CORE_1);
          roleList2.add(Hero.Role.CORE_3);

          Hero hero2 = new Hero(roleList2,i+1,"TestCore 3");
          list.add(hero2);
          break;

        case 3:
          List<Hero.Role> roleList3 = new ArrayList<>();
          roleList3.add(Hero.Role.SUPPORT_4);
          roleList3.add(Hero.Role.CORE_3);

          Hero hero3 = new Hero(roleList3,i+1,"TestSupport 1");
          list.add(hero3);
          break;

        case 4:
          List<Hero.Role> roleList4 = new ArrayList<>();
          roleList4.add(Hero.Role.SUPPORT_4);
          roleList4.add(Hero.Role.SUPPORT_5);

          Hero hero4 = new Hero(roleList4,i+1,"TestSupport 2");
          list.add(hero4);
          break;

        case 5:
          List<Hero.Role> roleList5 = new ArrayList<>();
          roleList5.add(Hero.Role.SUPPORT_5);

          Hero hero5 = new Hero(roleList5,i+1,"TestSupport 3");
          list.add(hero5);
          break;
      }
    }
    return list;
  }

}
