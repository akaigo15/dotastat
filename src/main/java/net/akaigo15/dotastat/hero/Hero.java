package net.akaigo15.dotastat.hero;

import java.util.List;

public class Hero {

  public enum Role {
    CORE_1,
    CORE_2,
    CORE_3,
    SUPPORT_4,
    SUPPORT_5,
    ;
  }

  private List<Role> role;
  private int heroId;
  private String name;

  public Hero(List<Role> role, int heroId, String name) {
    this.role = role;
    this.heroId = heroId;
    this.name = name;

  }

  public List<Role> getRole() {
    return role;
  }

  public int getHeroId() {
    return heroId;
  }

  public String getName() {
    return name;
  }

}
