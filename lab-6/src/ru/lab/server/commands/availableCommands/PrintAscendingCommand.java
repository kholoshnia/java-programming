package ru.lab.server.commands.availableCommands;

import ru.lab.common.element.Worker;
import ru.lab.common.io.Output;
import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.storage.Editor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Print ascending command class */
public final class PrintAscendingCommand implements Command {
  @Override
  public String getKey() {
    return "print_ascending";
  }

  @Override
  public String getInfo() {
    return "вывести элементы коллекции в порядке возрастания";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Print collection elements in ascending order
   *
   * @return Reply and correctness
   */
  @Override // TODO: If number is big
  public Reply execute(Editor editor) {
    List<String> reply = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      reply.add(
          Output.Colors.CYAN.getColorCode()
              + "Элементы коллекции по возрастанию: "
              + Output.Colors.RESET.getColorCode());
      LinkedHashMap<Integer, Worker> sortedMap = new LinkedHashMap<>();
      editor.getCollection().getEntrySet().stream()
          .sorted(Map.Entry.comparingByKey())
          .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
      sortedMap.forEach(
          (key, value) ->
              reply.add(
                  Output.Colors.BLUE.getColorCode()
                      + Output.Decorators.BOLD.getDecoratorCode()
                      + Output.Decorators.UNDERLINE.getDecoratorCode()
                      + key
                      + Output.Decorators.RESET.getDecoratorCode()
                      + ": "
                      + value));
      return new Reply(reply, Reply.Types.INFO);
    }
    return new Reply("В коллекции нет элементов", Reply.Types.FINER);
  }
}
