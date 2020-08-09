package ru.lab.server.controller.services.script;

import ru.lab.server.model.domain.entity.entities.user.User;

import java.util.List;
import java.util.Locale;

/**
 * Script class containing necessary data for commands execution.
 *
 * <p>Script syntax:
 *
 * <ul>
 *   <li>each command must start on a new line;
 *   <li>if the command requires additional arguments, the following lines must contain them in the
 *       form name:value, name:"value" or name:'value' in case of spaces.
 * </ul>
 *
 * <p>List of available arguments:
 *
 * <ul>
 *   <li>worker
 *   <li>workerName
 *   <li>workerSalary
 *   <li>workerStartDate
 *   <li>workerEndDate
 *   <li>workerStatus
 *   <li>coordinates
 *   <li>coordinatesX
 *   <li>coordinatesY
 *   <li>person
 *   <li>personPassportId
 *   <li>personEyeColor
 *   <li>personHairColor
 *   <li>location
 *   <li>locationX
 *   <li>locationY
 *   <li>locationZ
 *   <li>locationName
 *   <li>include
 * </ul>
 */
public final class Script {
  private final Locale locale;
  private final User user;
  private final List<String> lines;

  private int current;

  public Script(Locale locale, User user, List<String> lines) {
    this.locale = locale;
    this.user = user;
    this.lines = lines;
    current = -1;
  }

  public Locale getLocale() {
    return locale;
  }

  public User getUser() {
    return user;
  }

  public int getCurrent() {
    return current;
  }

  public boolean hasNext() {
    return current < lines.size();
  }

  public String nextLine() {
    current++;

    if (current < lines.size()) {
      return lines.get(current);
    }

    return null;
  }

  public void back() {
    if (current > -1) {
      current--;
    }
  }

  @Override
  public int hashCode() {
    return lines.stream().mapToInt(String::hashCode).sum();
  }
}
