package net.akaigo15.dotastat.opendota;

import net.akaigo15.dotastat.opendota.cache.OpenDotaCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static net.akaigo15.dotastat.UnitTestHelper.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultOpenDotaStatClientTests {

  @Mock RestTemplate restTemplate;
  @Mock OpenDotaCache openDotaCache;

  @InjectMocks
  @Spy
  DefaultOpenDotaStatClient defaultDotaStatClient;

  @Captor private ArgumentCaptor<Integer> integerCaptor1;
  @Captor private ArgumentCaptor<Integer> integerCaptor2;

  @Test
  public void getHeroInfoListWithPatch_shouldReturnCachedData() {

    //Prepare mocks
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1));
    list.add(makePlayerHeroInfo(2));
    list.add(makePlayerHeroInfo(3));
    list.add(makePlayerHeroInfo(4));

    Optional<List<PlayerHeroInfo>> optionalList = Optional.of(list);

    Mockito.when(
        openDotaCache.getPlayerHeroPatchInfo(integerCaptor1.capture(), integerCaptor2.capture())
    ).thenReturn(optionalList);

    //Call the method under test
    List<PlayerHeroInfo> outputList = defaultDotaStatClient.getHeroInfoList(1234,5678);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(4);

    //Verify the mock
    verify(openDotaCache).getPlayerHeroPatchInfo(anyInt(), anyInt());
    assertThat(integerCaptor1.getValue()).isEqualTo(1234);
    assertThat(integerCaptor2.getValue()).isEqualTo(5678);
  }

  @Test
  public void getHeroListWithPatch_shouldNotReturnCachedData() {

    //Prepare mocks
    Optional<List<PlayerHeroInfo>> optionalList = Optional.empty();
    Mockito.when(
        openDotaCache.getPlayerHeroPatchInfo(integerCaptor1.capture(), integerCaptor2.capture())
    ).thenReturn(optionalList);

    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1));
    list.add(makePlayerHeroInfo(2));
    list.add(makePlayerHeroInfo(3));
    list.add(makePlayerHeroInfo(4));
    PlayerHeroInfo[] array = list.toArray(new PlayerHeroInfo[0]);
    Mockito.when(
        restTemplate.getForObject(anyString(), Matchers.any(Class.class))
    ).thenReturn(array);

    //Call the method under test
    List<PlayerHeroInfo> outputList = defaultDotaStatClient.getHeroInfoList(1234,5678);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(4);
  }

  @Test
  public void getHeroInfoList_shouldReturnCachedData() {
    //Prepare mocks
    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1));
    list.add(makePlayerHeroInfo(2));
    list.add(makePlayerHeroInfo(3));
    list.add(makePlayerHeroInfo(4));
    Optional<List<PlayerHeroInfo>> optionalList = Optional.of(list);
    Mockito.when(
        openDotaCache.getPlayerHeroInfo(integerCaptor1.capture())
    ).thenReturn(optionalList);


    //Calling the method
    List<PlayerHeroInfo> outputList = defaultDotaStatClient.getHeroInfoList(1234);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(4);
  }

  @Test
  public void getHeroInfoList_shouldNotReturnCachedData() {

    //Prepare mocks
    Optional<List<PlayerHeroInfo>> optionalList = Optional.empty();
    Mockito.when(
        openDotaCache.getPlayerHeroInfo(integerCaptor1.capture())
    ).thenReturn(optionalList);

    List<PlayerHeroInfo> list = new ArrayList<>();
    list.add(makePlayerHeroInfo(1));
    list.add(makePlayerHeroInfo(2));
    list.add(makePlayerHeroInfo(3));
    list.add(makePlayerHeroInfo(4));
    PlayerHeroInfo[] array = list.toArray(new PlayerHeroInfo[0]);
    Mockito.when(
        restTemplate.getForObject(anyString(), Matchers.any(Class.class))
    ).thenReturn(array);

    //Call the method under test
    List<PlayerHeroInfo> outputList = defaultDotaStatClient.getHeroInfoList(1234);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(4);
  }

  @Test
  public void getTeamHeroInbfoList_shouldReturnCachedData() {

    //Prepare mocks
    List<TeamHeroInfo> list = new ArrayList<>();
    list.add(makeTeamHeroInfo(1));
    list.add(makeTeamHeroInfo(2));
    list.add(makeTeamHeroInfo(3));
    list.add(makeTeamHeroInfo(4));
    Optional<List<TeamHeroInfo>> optionalList = Optional.of(list);
    Mockito.when(
        openDotaCache.getTeamHeroInfo(anyInt())
    ).thenReturn(optionalList);

    //Call the method
    List<TeamHeroInfo> outputList = defaultDotaStatClient.getTeamHeroInfoList(1234);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(4);
  }

  @Test
  public void getTeamHerInfoList_shouldNotReturnCachedData() {
    //prepare
    Optional<List<TeamHeroInfo>> optionalList = Optional.empty();
    List<TeamHeroInfo> list = new ArrayList<>();
    list.add(makeTeamHeroInfo(1));
    list.add(makeTeamHeroInfo(2));
    list.add(makeTeamHeroInfo(3));
    list.add(makeTeamHeroInfo(4));
    TeamHeroInfo[] array = list.toArray(new TeamHeroInfo[0]);
    Mockito.when(
        openDotaCache.getTeamHeroInfo(anyInt())
    ).thenReturn(optionalList);
    Mockito.when(
        restTemplate.getForObject(anyString(), Matchers.any(Class.class))
    ).thenReturn(array);

    //Call
    List<TeamHeroInfo> outputList = defaultDotaStatClient.getTeamHeroInfoList(1234);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(4);
  }

  @Test
  public void getTeamMatchInfoList_shouldReturnCache() {
    List<TeamMatchInfo> list = new ArrayList<>();
    list.add(makeTeamMatchInfo(1));
    list.add(makeTeamMatchInfo(2));
    list.add(makeTeamMatchInfo(3));
    list.add(makeTeamMatchInfo(4));
    Optional<List<TeamMatchInfo>> optionalList = Optional.of(list);
    Mockito.when(
        openDotaCache.getTeamMatchInfo(anyInt())
    ).thenReturn(optionalList);

    List<TeamMatchInfo> outputList = defaultDotaStatClient.getTeamMatchInfoList(1234);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(4);
  }

  @Test
  public void getTeamMatchInfoList_shouldNotReturnCache() {
    Optional<List<TeamMatchInfo>> optionalList = Optional.empty();
    List<TeamMatchInfo> list = new ArrayList<>();
    list.add(makeTeamMatchInfo(1));
    list.add(makeTeamMatchInfo(2));
    list.add(makeTeamMatchInfo(3));
    list.add(makeTeamMatchInfo(4));
    TeamMatchInfo[] array = list.toArray(new TeamMatchInfo[0]);
    Mockito.when(
        openDotaCache.getTeamMatchInfo(anyInt())
    ).thenReturn(optionalList);
    Mockito.when(
        restTemplate.getForObject(anyString(), Matchers.any(Class.class))
    ).thenReturn(array);

    List<TeamMatchInfo> outputList = defaultDotaStatClient.getTeamMatchInfoList(1234);
    assertThat(outputList).isNotNull();
    assertThat(outputList.size()).isEqualTo(4);
  }



}
