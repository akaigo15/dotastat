package net.akaigo15.dotastat.controller;

import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.logic.DotaStatService;
import net.akaigo15.dotastat.logic.PlayerHeroStats;
import net.akaigo15.dotastat.logic.TeamHeroStats;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dotastat")
class DotaStatController {
  private static final Logger LOG = LoggerFactory.getLogger(DotaStatController.class);

  @Autowired
  DotaStatService dotaStatService;

  @RequestMapping(method = RequestMethod.POST,
  path = "/playerstat")
  @ResponseBody
  public List<PlayerHeroStats> getPlayerHeroStats(@RequestBody PlayerHeroParams playerHeroParams) {
      LOG.debug("getPlayerHeroStats called with: {}",playerHeroParams);

      List<Hero.Role> roleList = playerHeroParams.getHeroType().stream()
          .map(Hero.Role::valueOf)
          .collect(Collectors.toList());

      return dotaStatService.filterPlayerHeroInfo(playerHeroParams.getSteam32Id(),
          playerHeroParams.getPatch(),
          roleList,
          playerHeroParams.getMinimumGamesPlayed(),
          playerHeroParams.getMinimumWinRate());
  }

  @RequestMapping(method = RequestMethod.POST,
      path = "/teamherostat")
  @ResponseBody
  public List<TeamHeroStats> getTeamHeroStats(@RequestBody TeamHeroParams teamHeroParams) {
    LOG.debug("getTeamHeroStats called with: {}",teamHeroParams);

    List<Hero.Role> roleList = teamHeroParams.getHeroType().stream()
        .map(Hero.Role::valueOf)
        .collect(Collectors.toList());

    return dotaStatService.filterTeamHeroInfo(teamHeroParams.getTeamId(),
        roleList,
        teamHeroParams.getMinimumGamesPlayed(),
        teamHeroParams.getMinimumWinRate());
  }

  @RequestMapping(method = RequestMethod.POST,
      path = "/teammatchstat")
  @ResponseBody
  public List<TeamMatchInfo> getTeamMatchStats(@RequestBody TeamMatchParams teamMatchParams) {
    LOG.debug("getTeamMatchStats called with: {}",teamMatchParams);

    return dotaStatService.filterTeamMatchInfo(teamMatchParams.getTeamId(),
        teamMatchParams.getLeagueId(),
        teamMatchParams.isWin());
  }

}
