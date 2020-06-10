package com.lab.common.tools;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/** Random class with random generators */
public class Random {
  public int randomKey(Set<Integer> keySet) throws RandomException {
    int key;
    do {
      key = randomInt(0, 10000);
    } while (keySet.contains(key));
    return key;
  }

  /**
   * Returns random element from given enum
   *
   * @param c class
   * @param <T> enum
   * @return Element of enum
   */
  public <T extends Enum<?>> T randomEnum(Class<T> c) {
    int x = ThreadLocalRandom.current().nextInt(c.getEnumConstants().length);
    return c.getEnumConstants()[x];
  }

  /**
   * Returns random int
   *
   * @param from from bound
   * @param to to bound
   * @return Random int
   */
  public int randomInt(int from, int to) throws RandomException {
    try {
      return ThreadLocalRandom.current().nextInt(from, to);
    } catch (IllegalArgumentException e) {
      throw new RandomException("Верхний предел не может быть меньше, чем нижний", e);
    }
  }

  /**
   * Returns random double
   *
   * @param from from bound
   * @param to to bound
   * @return Random double
   */
  public double randomDouble(double from, double to) throws RandomException {
    try {
      return ThreadLocalRandom.current().nextDouble(from, to);
    } catch (IllegalArgumentException e) {
      throw new RandomException("Верхний предел не может быть меньше, чем нижний", e);
    }
  }

  /**
   * Returns random long
   *
   * @param from from bound
   * @param to to bound
   * @return Random Long
   */
  public long randomLong(long from, long to) throws RandomException {
    try {
      return ThreadLocalRandom.current().nextLong(from, to);
    } catch (IllegalArgumentException e) {
      throw new RandomException("Верхний предел не может быть меньше, чем нижний", e);
    }
  }

  /**
   * Returns random LocalDateTime
   *
   * @param from from bound
   * @param to to bound
   * @return Random LocalDateTime
   */
  public LocalDateTime randomDateTime(int from, int to) throws RandomException {
    LocalDateTime now = LocalDateTime.now();
    int year = 60 * 60 * 24 * 365;
    return now.plusSeconds(randomLong(from * year, to * year));
  }

  /**
   * Returns random Date
   *
   * @param from from bound
   * @param to to bound
   * @return Random Date
   */
  public Date randomDate(int from, int to) throws RandomException {
    int year = 60 * 60 * 24 * 365;
    return new Date(randomLong(from * year, to * year));
  }

  /**
   * Returns random String name
   *
   * @return Random String name
   */
  public String randomName() {
    String[] begin = {
      "Kr", "Ca", "Ra", "Mrok", "Cru", "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar",
      "Mjol", "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro", "Mar", "Luk"
    };
    String[] middle = {
      "air", "ir", "mi", "sor", "mee", "clo", "red", "cra", "ark", "arc", "miri", "lori", "cres",
      "mur", "zer", "marac", "zoir", "slamar", "salmar", "urak"
    };
    String[] end = {
      "d", "ed", "ark", "arc", "es", "er", "der", "tron", "med", "ure", "zur", "cred", "mur"
    };

    return begin[ThreadLocalRandom.current().nextInt(begin.length)]
        + middle[ThreadLocalRandom.current().nextInt(middle.length)]
        + end[ThreadLocalRandom.current().nextInt(end.length)];
  }
}
