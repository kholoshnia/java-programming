package ru.lab.common.exchange;

import java.io.Serializable;

public class Request implements Serializable {
  private String commandName;
  private String commandParameter;

  public Request(String command) {
    if (command == null) {
      commandName = null;
      commandParameter = null;
    } else {
      String[] values = command.split(" ", 2);
      commandName = values[0];
      if (values.length == 2) commandParameter = values[1];
    }
  }

  public String getCommandName() {
    return commandName;
  }

  public void setCommandName(String commandName) {
    this.commandName = commandName;
  }

  public String getCommandParameter() {
    return commandParameter;
  }

  public void setCommandParameter(String commandParameter) {
    this.commandParameter = commandParameter;
  }
}
