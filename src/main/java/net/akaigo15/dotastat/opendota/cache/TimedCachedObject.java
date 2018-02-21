package net.akaigo15.dotastat.opendota.cache;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimedCachedObject<T> {
  private Date timeSubmitted;
  private List<T> cachedData;

  public TimedCachedObject(List<T> list) {
    timeSubmitted = Date.from(Instant.now());
    cachedData = new ArrayList<>();
    cachedData.addAll(list);
  }

  public Date getTimeSubmitted() {
    return timeSubmitted;
  }

  public List<T> getCashedData() {
    return cachedData;
  }
}
