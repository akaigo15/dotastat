package net.akaigo15.dotastat.logic;


import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.hero.HeroData;
import net.akaigo15.dotastat.opendota.OpenDotaStatClient;
import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static net.akaigo15.dotastat.UnitTestHelper.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;


@RunWith(MockitoJUnitRunner.class)
public class DefaultDotaStatServiceTests {

  @Mock private OpenDotaStatClient openDotaStatClient;
  @Mock private HeroData heroData;

  @InjectMocks
  @Spy
  private DefaultDotaStatService defaultDotaStatService;


  @Test
  public void filterPlayerHeroInfo_withPatchWithGamesWithRoleList() {
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1,5)); //structured as: (HeroId,GamesPlayed)
    list.add(makePlayerHeroInfo(2,6));
    list.add(makePlayerHeroInfo(3,7));
    list.add(makePlayerHeroInfo(4,8));
    list.add(makePlayerHeroInfo(5,9));
    list.add(makePlayerHeroInfo(6,10));

    List<Hero> heroList = makeSampleHeros();

    List<Hero.Role> roleList = new ArrayList<>();
    roleList.add(Hero.Role.CORE_1);

    Mockito.when(
        openDotaStatClient.getHeroInfoList(anyInt(), anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(heroList.get(0)) //core 1 and 2
    .thenReturn(heroList.get(1)) //core 2 and 3
    .thenReturn(heroList.get(2)) //core 1 and 3
    .thenReturn(heroList.get(3)) //support 4 and core 3
    .thenReturn(heroList.get(4)) //support 4 and 5
    .thenReturn(heroList.get(5)); //support 5


    List<PlayerHeroStats> outputList = defaultDotaStatService.filterPlayerHeroInfo(1234,707,roleList,6,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(1);
  }

  @Test
  public void filterPlayerHeroInfo_withPatchWithGamesNoRoleList() {
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1,5));
    list.add(makePlayerHeroInfo(2,6));
    list.add(makePlayerHeroInfo(3,7));
    list.add(makePlayerHeroInfo(4,8));
    list.add(makePlayerHeroInfo(5,9));
    list.add(makePlayerHeroInfo(6,10));

    List<Hero> heroList = makeSampleHeros();

    List<Hero.Role> roleList = new ArrayList<>();

    Mockito.when(
        openDotaStatClient.getHeroInfoList(anyInt(), anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(heroList.get(0))
        .thenReturn(heroList.get(1))
        .thenReturn(heroList.get(2))
        .thenReturn(heroList.get(3))
        .thenReturn(heroList.get(4))
        .thenReturn(heroList.get(5));



    List<PlayerHeroStats> outputList = defaultDotaStatService.filterPlayerHeroInfo(1234,707,roleList,6,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(5);
  }

  @Test
  public void filterPlayerHeroInfo_withPatchNoGamesWithRoleList() {
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1,5));
    list.add(makePlayerHeroInfo(2,6));
    list.add(makePlayerHeroInfo(3,7));
    list.add(makePlayerHeroInfo(4,8));
    list.add(makePlayerHeroInfo(5,9));
    list.add(makePlayerHeroInfo(6,10));

    List<Hero> heroList = makeSampleHeros();

    List<Hero.Role> roleList = new ArrayList<>();
    roleList.add(Hero.Role.CORE_1);

    Mockito.when(
        openDotaStatClient.getHeroInfoList(anyInt(), anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(heroList.get(0))
        .thenReturn(heroList.get(1))
        .thenReturn(heroList.get(2))
        .thenReturn(heroList.get(3))
        .thenReturn(heroList.get(4))
        .thenReturn(heroList.get(5));


    List<PlayerHeroStats> outputList = defaultDotaStatService.filterPlayerHeroInfo(1234,707,roleList,0,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(2);
  }

  @Test
  public void filterPlayerHeroInfo_noPatchWithGamesWithRoleList() {
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1,5));
    list.add(makePlayerHeroInfo(2,6));
    list.add(makePlayerHeroInfo(3,7));
    list.add(makePlayerHeroInfo(4,8));
    list.add(makePlayerHeroInfo(5,9));
    list.add(makePlayerHeroInfo(6,10));


    List<Hero> heroList = makeSampleHeros();

    List<Hero.Role> roleList = new ArrayList<>();
    roleList.add(Hero.Role.CORE_1);

    Mockito.when(
        openDotaStatClient.getHeroInfoList(anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(heroList.get(0))
        .thenReturn(heroList.get(1))
        .thenReturn(heroList.get(2))
        .thenReturn(heroList.get(3))
        .thenReturn(heroList.get(4))
        .thenReturn(heroList.get(5));

    List<PlayerHeroStats> outputList = defaultDotaStatService.filterPlayerHeroInfo(1234,null,roleList,6,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(1);
  }

  @Test
  public void filterPlayerHeroInfo_noPatchWithGamesNoRoleList() {
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1,5));
    list.add(makePlayerHeroInfo(2,6));
    list.add(makePlayerHeroInfo(3,7));
    list.add(makePlayerHeroInfo(4,8));
    list.add(makePlayerHeroInfo(5,9));
    list.add(makePlayerHeroInfo(6,10));

    List<Hero> heroList = makeSampleHeros();

    List<Hero.Role> roleList = new ArrayList<>();

    Mockito.when(
        openDotaStatClient.getHeroInfoList(anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(heroList.get(0))
        .thenReturn(heroList.get(1))
        .thenReturn(heroList.get(2))
        .thenReturn(heroList.get(3))
        .thenReturn(heroList.get(4))
        .thenReturn(heroList.get(5));

    List<PlayerHeroStats> outputList = defaultDotaStatService.filterPlayerHeroInfo(1234,null,roleList,6,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(5);
  }

  @Test
  public void filterPlayerHeroInfo_noPatchNoGamesWithRoleList() {
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1,5));
    list.add(makePlayerHeroInfo(2,6));
    list.add(makePlayerHeroInfo(3,7));
    list.add(makePlayerHeroInfo(4,8));
    list.add(makePlayerHeroInfo(5,9));
    list.add(makePlayerHeroInfo(6,10));

    List<Hero> heroList = makeSampleHeros();

    List<Hero.Role> roleList = new ArrayList<>();
    roleList.add(Hero.Role.SUPPORT_5);

    Mockito.when(
        openDotaStatClient.getHeroInfoList(anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(heroList.get(0))
        .thenReturn(heroList.get(1))
        .thenReturn(heroList.get(2))
        .thenReturn(heroList.get(3))
        .thenReturn(heroList.get(4))
        .thenReturn(heroList.get(5));

    List<PlayerHeroStats> outputList = defaultDotaStatService.filterPlayerHeroInfo(1234,null,roleList,0,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(2);
  }

  @Test
  public void filterTeamHeroInfo_withGamesWithRoleList() {
    List<TeamHeroInfo> list = new ArrayList<>();
    list.add(makeTeamHeroInfo(1,5));
    list.add(makeTeamHeroInfo(2,6));
    list.add(makeTeamHeroInfo(3,7));
    list.add(makeTeamHeroInfo(4,8));
    list.add(makeTeamHeroInfo(5,9));
    list.add(makeTeamHeroInfo(6,10));

    List<Hero> heroList = makeSampleHeros();

    List<Hero.Role> roleList = new ArrayList<>();
    roleList.add(Hero.Role.CORE_3);

    Mockito.when(
        openDotaStatClient.getTeamHeroInfoList(anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(heroList.get(0))
        .thenReturn(heroList.get(1))
        .thenReturn(heroList.get(2))
        .thenReturn(heroList.get(3))
        .thenReturn(heroList.get(4))
        .thenReturn(heroList.get(5));

    List<TeamHeroStats> outputList = defaultDotaStatService.filterTeamHeroInfo(1234,roleList,6,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(2);
  }

  @Test
  public void filterTeamHeroInfo_withGamesNoRoleList() {
    List<TeamHeroInfo> list = new ArrayList<>();
    list.add(makeTeamHeroInfo(1,5));
    list.add(makeTeamHeroInfo(2,6));
    list.add(makeTeamHeroInfo(3,7));
    list.add(makeTeamHeroInfo(4,8));
    list.add(makeTeamHeroInfo(5,9));
    list.add(makeTeamHeroInfo(6,10));

    List<Hero> heroList = makeSampleHeros();

    List<Hero.Role> roleList = new ArrayList<>();

    Mockito.when(
        openDotaStatClient.getTeamHeroInfoList(anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(heroList.get(0))
        .thenReturn(heroList.get(1))
        .thenReturn(heroList.get(2))
        .thenReturn(heroList.get(3))
        .thenReturn(heroList.get(4))
        .thenReturn(heroList.get(5));

    List<TeamHeroStats> outputList = defaultDotaStatService.filterTeamHeroInfo(1234,roleList,6,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(5);
  }

  @Test
  public void filterTeamHeroInfo_noGamesWithRoleList() {
    List<TeamHeroInfo> list = new ArrayList<>();
    list.add(makeTeamHeroInfo(1,5));
    list.add(makeTeamHeroInfo(2,6));
    list.add(makeTeamHeroInfo(3,7));
    list.add(makeTeamHeroInfo(4,8));
    list.add(makeTeamHeroInfo(5,9));
    list.add(makeTeamHeroInfo(6,10));

    List<Hero> heroList = makeSampleHeros();

    List<Hero.Role> roleList = new ArrayList<>();
    roleList.add(Hero.Role.CORE_3);

    Mockito.when(
        openDotaStatClient.getTeamHeroInfoList(anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(heroList.get(0))
        .thenReturn(heroList.get(1))
        .thenReturn(heroList.get(2))
        .thenReturn(heroList.get(3))
        .thenReturn(heroList.get(4))
        .thenReturn(heroList.get(5));

    List<TeamHeroStats> outputList = defaultDotaStatService.filterTeamHeroInfo(1234,roleList,0,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(2);
  }

  @Test
  public void filterTeamMatchInfo_withLeague() {
    List<TeamMatchInfo> list = new ArrayList<>();
    list.add(makeTeamMatchInfo(1, 9, 11));
    list.add(makeTeamMatchInfo(2, 9, 11));
    list.add(makeTeamMatchInfo(3, 9, 12));
    list.add(makeTeamMatchInfo(4, 9, 12));
    list.add(makeTeamMatchInfo(5, 9, 13));
    list.add(makeTeamMatchInfo(6, 9, 13));

    Mockito.when(
        openDotaStatClient.getTeamMatchInfoList(anyInt())
    ).thenReturn(list);

    List<TeamMatchInfo> outputList = defaultDotaStatService.filterTeamMatchInfo(9,11,true);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(2);
  }

  @Test
  public void filterTeamMatchInfo_noLeague() {
    List<TeamMatchInfo> list = new ArrayList<>();
    list.add(makeTeamMatchInfo(1, 9, 11));
    list.add(makeTeamMatchInfo(2, 9, 11));
    list.add(makeTeamMatchInfo(3, 9, 12));
    list.add(makeTeamMatchInfo(4, 9, 12));
    list.add(makeTeamMatchInfo(5, 9, 13));
    list.add(makeTeamMatchInfo(6, 9, 13));

    Mockito.when(
        openDotaStatClient.getTeamMatchInfoList(anyInt())
    ).thenReturn(list);

    List<TeamMatchInfo> outputList = defaultDotaStatService.filterTeamMatchInfo(3,null,false);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(0);
  }




}
