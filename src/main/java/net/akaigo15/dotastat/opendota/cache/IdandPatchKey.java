package net.akaigo15.dotastat.opendota.cache;

import java.util.Objects;

public class IdandPatchKey {
  private int steam32Id;
  private int patch;

  public IdandPatchKey(int steam32Id, int patch) {
    this.steam32Id = steam32Id;
    this.patch = patch;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    IdandPatchKey that = (IdandPatchKey) o;
    return (steam32Id == that.steam32Id) && (patch == that.patch);
  }

  @Override
  public int hashCode() {

    return Objects.hash(steam32Id, patch);
  }
}
