package ru.storage.common;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/** Command mediator class contains all command names. */
public final class CommandMediator {
  public final String login;
  public final String logout;
  public final String register;

  public final String history;

  public final String insert;
  public final String update;
  public final String removeKey;
  public final String clear;
  public final String removeLower;
  public final String replaceIfLower;

  public final String info;
  public final String show;
  public final String printAscending;
  public final String minByName;
  public final String countLessThanStartDate;

  public final String help;
  public final String save;
  public final String executeScript;
  public final String exit;

  private final List<String> commands;

  @Inject
  public CommandMediator() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("CommandMediator");

    login = resourceBundle.getString("commands.login");
    logout = resourceBundle.getString("commands.logout");
    register = resourceBundle.getString("commands.register");

    history = resourceBundle.getString("commands.history");

    insert = resourceBundle.getString("commands.insert");
    update = resourceBundle.getString("commands.update");
    removeKey = resourceBundle.getString("commands.removeKey");
    clear = resourceBundle.getString("commands.clear");
    removeLower = resourceBundle.getString("commands.removeLower");
    replaceIfLower = resourceBundle.getString("commands.replaceIfLower");

    info = resourceBundle.getString("commands.info");
    show = resourceBundle.getString("commands.show");
    printAscending = resourceBundle.getString("commands.printAscending");
    minByName = resourceBundle.getString("commands.minByName");
    countLessThanStartDate = resourceBundle.getString("commands.countLessThanStartDate");

    help = resourceBundle.getString("commands.help");
    save = resourceBundle.getString("commands.save");
    executeScript = resourceBundle.getString("commands.executeScript");
    exit = resourceBundle.getString("commands.exit");

    commands = initCommandList();
  }

  private List<String> initCommandList() {
    return new ArrayList<String>() {
      {
        if (login != null) {
          add(login);
        }
        if (logout != null) {
          add(logout);
        }
        if (register != null) {
          add(register);
        }

        if (history != null) {
          add(history);
        }

        if (insert != null) {
          add(insert);
        }
        if (update != null) {
          add(update);
        }
        if (removeKey != null) {
          add(removeKey);
        }
        if (clear != null) {
          add(clear);
        }
        if (removeLower != null) {
          add(removeLower);
        }
        if (replaceIfLower != null) {
          add(replaceIfLower);
        }

        if (info != null) {
          add(info);
        }
        if (show != null) {
          add(show);
        }
        if (printAscending != null) {
          add(printAscending);
        }
        if (minByName != null) {
          add(minByName);
        }
        if (countLessThanStartDate != null) {
          add(countLessThanStartDate);
        }

        if (help != null) {
          add(help);
        }
        if (save != null) {
          add(save);
        }
        if (executeScript != null) {
          add(executeScript);
        }
        if (exit != null) {
          add(exit);
        }
      }
    };
  }

  public List<String> getCommands() {
    return new ArrayList<>(commands);
  }

  public boolean contains(String command) {
    return commands.contains(command);
  }
}
