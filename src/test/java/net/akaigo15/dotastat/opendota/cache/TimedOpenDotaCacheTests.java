package net.akaigo15.dotastat.opendota.cache;


import net.akaigo15.dotastat.opendota.PlayerHeroInfo;
import net.akaigo15.dotastat.opendota.TeamHeroInfo;
import net.akaigo15.dotastat.opendota.TeamMatchInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static net.akaigo15.dotastat.UnitTestHelper.makePlayerHeroInfo;
import static net.akaigo15.dotastat.UnitTestHelper.makeTeamHeroInfo;
import static net.akaigo15.dotastat.UnitTestHelper.makeTeamMatchInfo;

public class TimedOpenDotaCacheTests {



  @Test
  public void getPlayerHeroInfo_shouldReturnInTimeOk() {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(500));
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1));
    list.add(makePlayerHeroInfo(2));
    list.add(makePlayerHeroInfo(3));
    list.add(makePlayerHeroInfo(4));
    cache.addPlayerHeroInfo(list,1234);

    Optional<List<PlayerHeroInfo>> option = cache.getPlayerHeroInfo(1234);
    assertThat(option.isPresent()).isTrue();
    List<PlayerHeroInfo> values = option.get();
    assertThat(values.size()).isEqualTo(4);
  }

  @Test
  public void getPlayerHeroInfo_shouldNotReturnInTime() throws InterruptedException {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(1));
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1));
    list.add(makePlayerHeroInfo(2));
    list.add(makePlayerHeroInfo(3));
    list.add(makePlayerHeroInfo(4));
    cache.addPlayerHeroInfo(list,1234);

    Thread.sleep(1100);
    Optional<List<PlayerHeroInfo>> option = cache.getPlayerHeroInfo(1234);
    assertThat(option.isPresent()).isFalse();
  }

  @Test
  public void getPlayerHeroPatchInfo_shouldReturnInTimeOk() {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(500));
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1));
    list.add(makePlayerHeroInfo(2));
    list.add(makePlayerHeroInfo(3));
    list.add(makePlayerHeroInfo(4));
    cache.addPlayerHeroPatchInfo(list,1234,5678);

    Optional<List<PlayerHeroInfo>> option = cache.getPlayerHeroPatchInfo(1234,5678);
    assertThat(option.isPresent()).isTrue();
    List<PlayerHeroInfo> values = option.get();
    assertThat(values.size()).isEqualTo(4);
  }

  @Test
  public void getPlayerHeroPatchInfo_shouldNotReturnInTime() throws InterruptedException {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(1));
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1));
    list.add(makePlayerHeroInfo(2));
    list.add(makePlayerHeroInfo(3));
    list.add(makePlayerHeroInfo(4));
    cache.addPlayerHeroPatchInfo(list,1234,5678);

    Thread.sleep(1100);
    Optional<List<PlayerHeroInfo>> option = cache.getPlayerHeroPatchInfo(1234,5678);
    assertThat(option.isPresent()).isFalse();
  }

  @Test
  public void getPlayerHeroPatchInfo_shouldNotReturnAnything() {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(500));
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1));
    list.add(makePlayerHeroInfo(2));
    list.add(makePlayerHeroInfo(3));
    list.add(makePlayerHeroInfo(4));
    cache.addPlayerHeroPatchInfo(list,1234,56787);

    Optional<List<PlayerHeroInfo>> option = cache.getPlayerHeroPatchInfo(1234,567);
    assertThat(option.isPresent()).isFalse();
  }

  @Test
  public void getTeamHeroInfo_ShouldReturnInTimeOk() {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(500));
    List<TeamHeroInfo> list = new ArrayList<>();
    list.add(makeTeamHeroInfo(1));
    list.add(makeTeamHeroInfo(2));
    list.add(makeTeamHeroInfo(3));
    list.add(makeTeamHeroInfo(4));
    cache.addTeamHeroInfo(list,1234);

    Optional<List<TeamHeroInfo>> option = cache.getTeamHeroInfo(1234);
    assertThat(option.isPresent()).isTrue();
    List<TeamHeroInfo> values = option.get();
    assertThat(values.size()).isEqualTo(4);
  }

  @Test
  public void getTeamHeroInfo_shouldNotReturnInTime() throws InterruptedException {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(1));
    List<TeamHeroInfo> list = new ArrayList<>();
    list.add(makeTeamHeroInfo(1));
    list.add(makeTeamHeroInfo(2));
    list.add(makeTeamHeroInfo(3));
    list.add(makeTeamHeroInfo(4));
    cache.addTeamHeroInfo(list,1234);

    Thread.sleep(1100);
    Optional<List<TeamHeroInfo>> option = cache.getTeamHeroInfo(1234);
    assertThat(option.isPresent()).isFalse();
  }

  @Test
  public void getTeamMatchInfo_ShouldReturnInTimeOk() {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(500));
    List<TeamMatchInfo> list = new ArrayList<>();
    list.add(makeTeamMatchInfo(1));
    list.add(makeTeamMatchInfo(2));
    list.add(makeTeamMatchInfo(3));
    list.add(makeTeamMatchInfo(4));
    cache.addTeamMatchInfo(list,1234);

    Optional<List<TeamMatchInfo>> option = cache.getTeamMatchInfo(1234);
    assertThat(option.isPresent()).isTrue();
    List<TeamMatchInfo> values = option.get();
    assertThat(values.size()).isEqualTo(4);
  }

  @Test
  public void getTeamMatchInfo_ShouldNotReturnInTime() throws InterruptedException {
    TimedOpenDotaCache cache = new TimedOpenDotaCache(new DotaCacheConfig(1));
    List<TeamMatchInfo> list = new ArrayList<>();
    list.add(makeTeamMatchInfo(1));
    list.add(makeTeamMatchInfo(2));
    list.add(makeTeamMatchInfo(3));
    list.add(makeTeamMatchInfo(4));
    cache.addTeamMatchInfo(list,1234);

    Thread.sleep(1100);
    Optional<List<TeamMatchInfo>> option = cache.getTeamMatchInfo(1234);
    assertThat(option.isPresent()).isFalse();
  }
}
