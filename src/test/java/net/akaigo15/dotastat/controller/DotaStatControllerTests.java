package net.akaigo15.dotastat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.logic.DotaStatService;
import net.akaigo15.dotastat.logic.PlayerHeroStats;
import net.akaigo15.dotastat.logic.TeamHeroStats;
import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
public class DotaStatControllerTests {

  @MockBean
  DotaStatService dotaStatService;

  @Autowired
  private MockMvc mockMvc;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void getPlayerHeroStats_confirmMethodSignature() throws Exception {

    PlayerHeroParams playerHeroParams = new PlayerHeroParams();
    playerHeroParams.setHeroType(new ArrayList<>());
    playerHeroParams.setName("name");
    playerHeroParams.setSteam32Id(1234);

    Hero hero = new Hero(new ArrayList<Hero.Role>(),1,"name");
    PlayerHeroStats playerHeroStats = new PlayerHeroStats(new PlayerHeroInfo(), hero);
    List<PlayerHeroStats> list = new ArrayList<>();
    list.add(playerHeroStats);

    PlayerHeroExportInfo export = new PlayerHeroExportInfo("name",1234,list);

    String exportAsJson = objectMapper.writeValueAsString(export);

    when(dotaStatService.filterPlayerHeroInfo(Matchers.anyInt(),
        Matchers.anyInt(),
        Matchers.anyList(),
        Matchers.anyInt(),
        Matchers.anyInt())).thenReturn(list);

    String json = objectMapper.writeValueAsString(playerHeroParams);
    mockMvc.perform

        (post("/dotastat/playerstat")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))

        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(exportAsJson));
  }

  @Test
  public void getTeamHeroStats_confirmMethodSignature() throws Exception {
    TeamHeroParams teamHeroParams = new TeamHeroParams();
    teamHeroParams.setHeroType(new ArrayList<>());

    Hero hero = new Hero(new ArrayList<Hero.Role>(),1,"name");
    TeamHeroStats teamHeroStats = new TeamHeroStats(new TeamHeroInfo(),hero);
    List<TeamHeroStats> list = new ArrayList<>();
    list.add(teamHeroStats);

    String listAsJson = objectMapper.writeValueAsString(list);

    when(dotaStatService.filterTeamHeroInfo(Matchers.anyInt(),
        Matchers.anyList(),
        Matchers.anyInt(),
        Matchers.anyInt())).thenReturn(list);

    String json = objectMapper.writeValueAsString(teamHeroParams);
    mockMvc.perform

        (post("/dotastat/teamherostat")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))

        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(listAsJson));
  }

  @Test
  public void getTeamMatchStats_confirmMethodSignature() throws Exception {
    TeamMatchParams teamMatchParams = new TeamMatchParams();

    TeamMatchInfo teamMatchInfo = new TeamMatchInfo();
    List<TeamMatchInfo> list = new ArrayList<>();
    list.add(teamMatchInfo);

    String listAsJson = objectMapper.writeValueAsString(list);

    when(dotaStatService.filterTeamMatchInfo(Matchers.anyInt(),
        Matchers.anyInt(),
        Matchers.anyBoolean())).thenReturn(list);

    String json = objectMapper.writeValueAsString(teamMatchParams);
    mockMvc.perform

        (post("/dotastat/teammatchstat")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))

        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(content().json(listAsJson));
  }

}
