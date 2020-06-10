package com.lab.server.commands.availableCommands;

import com.lab.common.io.Output;
import com.lab.common.reply.Reply;
import com.lab.server.commands.AvailableCommands;
import com.lab.server.commands.Command;
import com.lab.server.storage.Editor;

import java.util.ArrayList;
import java.util.List;

/** Help command class */
public final class HelpCommand implements Command {
  @Override
  public String getKey() {
    return "help";
  }

  @Override
  public String getInfo() {
    return "вывести справку по доступным командам";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Shows information about all available commands
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) {
    List<String> reply = new ArrayList<>();
    new AvailableCommands()
        .getCommands()
        .values()
        .forEach(
            el -> {
              if (el.getParameters() != null) {
                reply.add(
                    Output.Colors.RED.getColorCode()
                        + Output.Decorators.BOLD.getDecoratorCode()
                        + el.getKey()
                        + Output.Decorators.RESET.getDecoratorCode()
                        + " "
                        + el.getParameters()
                        + " : "
                        + el.getInfo());
              } else {
                reply.add(
                    Output.Colors.RED.getColorCode()
                        + Output.Decorators.BOLD.getDecoratorCode()
                        + el.getKey()
                        + Output.Decorators.RESET.getDecoratorCode()
                        + " : "
                        + el.getInfo());
              }
            });
    return new Reply(reply, Reply.Types.INFO);
  }
}
