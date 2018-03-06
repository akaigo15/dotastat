package net.akaigo15.dotastat.hero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileBasedHeroData implements HeroData {
  private static final Logger LOG = LoggerFactory.getLogger(FileBasedHeroData.class);

  private static final String FILENAME = "herodata.csv";

  private Map<Integer, Hero> heroMap;

  public FileBasedHeroData() {
    heroMap = new HashMap<>();
    loadFromFile();
  }

  @Override
  public Hero getHero(final int heroId) {
    return heroMap.get(heroId);
  }

  @Override
  public String getHeroName(final int heroId) {
    return null;
  }

  private void loadFromFile() {
    List<String> heroList = new ArrayList<>();
    LOG.debug("Loading hero file: {}", FILENAME);
    try (Stream<String> stream = Files.lines(Paths.get(FILENAME))) {

      heroList = stream
          .map(String::trim)
          .collect(Collectors.toList());

    } catch (IOException e) {
      LOG.error("Can't find or load the script file: {}", FILENAME, e);
      System.out.println("Unable to load file: " + FILENAME);
    }

    if(heroList == null || heroList.size() == 0) {
      LOG.error("Hero file: {} is missing or empty", FILENAME);
      throw new RuntimeException("Hero file: " + FILENAME + " is missing or empty");
    }

    heroList.stream()
        .map(this::heroFromLine)
        .forEach(h -> heroMap.put(h.getHeroId(), h));
  }

  private Hero heroFromLine(String line) {
    String[] heroArray = line.split(",");

    if(heroArray == null || heroArray.length < 3 || heroArray.length > 7) {
      LOG.error("Hero file: {} contains invalid line: {}", FILENAME, line);
      throw new RuntimeException("Hero file: " + FILENAME + " contains invalid line: " + line);
    }

    int id = Integer.parseInt(heroArray[0].trim());
    String name = heroArray[1].trim();

    List<Hero.Role> role = new ArrayList<>();

    for(int i = 2; i < heroArray.length; i++) {
      role.add(Hero.Role.valueOf(heroArray[i]));
    }
    LOG.debug("Hero loaded: {}", name);

    return new Hero(role, id, name);
  }
}
