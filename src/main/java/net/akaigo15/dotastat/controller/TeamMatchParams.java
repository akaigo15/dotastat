package net.akaigo15.dotastat.controller;

public class TeamMatchParams {
  private int teamId;
  private Integer leagueId;
  private boolean win;

  public int getTeamId() {
    return teamId;
  }

  public void setTeamId(int teamId) {
    this.teamId = teamId;
  }

  public Integer getLeagueId() {
    return leagueId;
  }

  public void setLeagueId(Integer leagueId) {
    this.leagueId = leagueId;
  }

  public boolean isWin() {
    return win;
  }

  public void setWin(boolean win) {
    this.win = win;
  }

  @Override
  public String toString() {
    return "TeamMatchParams{" +
        "teamId=" + teamId +
        ", leagueId=" + leagueId +
        ", win=" + win +
        '}';
  }
}
