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
import org.springframework.web.bind.annotation.*;
import sun.swing.BakedArrayList;

import java.util.ArrayList;
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
  public List<TeamHeroStats> getTeamHeroStats() {
    return null;
  }

  @RequestMapping(method = RequestMethod.POST,
      path = "/teammatchstat")
  @ResponseBody
  public List<TeamMatchInfo> getTeamMatchStats() {
    return null;
  }

}
