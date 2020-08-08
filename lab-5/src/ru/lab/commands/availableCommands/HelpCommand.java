package ru.lab.commands.availableCommands;

import ru.lab.commands.AvailableCommands;
import ru.lab.commands.Command;
import ru.lab.console.Output;
import ru.lab.runner.Editor;
import ru.lab.runner.Response;

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
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    for (Command el : new AvailableCommands().getCommands().values()) {
      if (el.getParameters() != null) {
        response.add(
            Output.Colors.RED.getColorCode()
                + Output.Decorators.BOLD.getDecoratorCode()
                + el.getKey()
                + Output.Decorators.RESET.getDecoratorCode()
                + " "
                + el.getParameters()
                + " : "
                + el.getInfo());
      } else {
        response.add(
            Output.Colors.RED.getColorCode()
                + Output.Decorators.BOLD.getDecoratorCode()
                + el.getKey()
                + Output.Decorators.RESET.getDecoratorCode()
                + " : "
                + el.getInfo());
      }
    }
    return new Response(true, response, Response.Types.TEXT);
  }
}
