package net.akaigo15.dotastat.opendota;

import net.akaigo15.dotastat.UnitTestHelper;
import net.akaigo15.dotastat.opendota.cache.OpenDotaCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static net.akaigo15.dotastat.UnitTestHelper.makePlayerHeroInfo;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDotaStatClientTests {

  @Mock RestTemplate restTemplate;
  @Mock OpenDotaCache openDotaCache;

  @InjectMocks
  @Spy
  DefaultDotaStatClient defaultDotaStatClient;

  @Captor ArgumentCaptor<Integer> integerCaptor1;
  @Captor ArgumentCaptor<Integer> integerCaptor2;

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
  public void getHeroList_shouldNotReturnCachedData() {

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



}
