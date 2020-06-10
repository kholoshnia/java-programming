package com.lab.server.commands.availableCommands;

import com.lab.common.io.Output;
import com.lab.common.reply.Reply;
import com.lab.server.commands.Command;
import com.lab.server.storage.Editor;

import java.util.ArrayList;
import java.util.List;

/** Show command class */
public final class ShowCommand implements Command {
  @Override
  public String getKey() {
    return "show";
  }

  @Override
  public String getInfo() {
    return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Outputs to the standard output stream all the elements of the collection in a string
   * representation
   *
   * @return Reply and correctness
   */
  @Override // TODO: If number is big
  public Reply execute(Editor editor) {
    List<String> reply = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      reply.add(
          Output.Colors.CYAN.getColorCode()
              + "Элементы коллекции в строковом представлении: "
              + Output.Colors.RESET.getColorCode());
      editor
          .getCollection()
          .getEntrySet()
          .forEach(
              el ->
                  reply.add(
                      Output.Colors.BLUE.getColorCode()
                          + Output.Decorators.BOLD.getDecoratorCode()
                          + Output.Decorators.UNDERLINE.getDecoratorCode()
                          + el.getKey()
                          + Output.Decorators.RESET.getDecoratorCode()
                          + ": "
                          + el.getValue()));
      return new Reply(reply, Reply.Types.INFO);
    }
    return new Reply("В коллекции нет элементов", Reply.Types.FINE);
  }
}
