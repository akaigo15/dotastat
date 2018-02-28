package net.akaigo15.dotastat.logic;


import net.akaigo15.dotastat.hero.Hero;
import net.akaigo15.dotastat.hero.HeroData;
import net.akaigo15.dotastat.opendota.OpenDotaStatClient;
import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static net.akaigo15.dotastat.UnitTestHelper.makePlayerHeroInfo;
import static org.mockito.Matchers.anyInt;


@RunWith(MockitoJUnitRunner.class)
public class DefaultDotaStatServiceTests {

  @Mock OpenDotaStatClient openDotaStatClient;
  @Mock HeroData heroData;

  @InjectMocks
  @Spy
  DefaultDotaStatService defaultDotaStatService;


  @Test
  public void filterPlayerHeroInfo_noPatchWithGamesWithRoleList() {
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1,5));
    list.add(makePlayerHeroInfo(2,6));
    list.add(makePlayerHeroInfo(3,7));
    list.add(makePlayerHeroInfo(4,8));

    List<Hero.Role> roleList = new ArrayList<>();
    roleList.add(Hero.Role.CORE_1);
    Hero hero = new Hero(roleList,1,"Test Hero 1");

    Mockito.when(
        openDotaStatClient.getHeroInfoList(anyInt())
    ).thenReturn(list);

    Mockito.when(
        heroData.getHero(anyInt())
    ).thenReturn(hero);


    List<PlayerHeroStats> outputList = defaultDotaStatService.filterPlayerHeroInfo(1234,null,roleList,6,0);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(3);
  }
}
