package com.lab.server.commands.availableCommands;

import com.lab.common.element.Worker;
import com.lab.common.io.Output;
import com.lab.common.reply.Reply;
import com.lab.server.commands.Command;
import com.lab.server.storage.Editor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/** Min by name command class */
public final class MinByNameCommand implements Command {
  @Override
  public String getKey() {
    return "min_by_name";
  }

  @Override
  public String getInfo() {
    return "вывести любой объект из коллекции, значение поля name которого является минимальным";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Shows an item with a minimum name value
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) {
    List<String> reply = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      Map.Entry<Integer, Worker> minByNameWorker =
          editor.getCollection().getEntrySet().stream()
              .min(Comparator.comparing(x -> x.getValue().getName()))
              .orElse(null);
      reply.add(
          "Элемент с миниальным именем: "
              + Output.Colors.BLUE.getColorCode()
              + Output.Decorators.BOLD.getDecoratorCode()
              + Output.Decorators.UNDERLINE.getDecoratorCode()
              + minByNameWorker.getKey().toString()
              + Output.Decorators.RESET.getDecoratorCode()
              + ": "
              + minByNameWorker.getValue().toString());
      return new Reply(reply, Reply.Types.INFO);
    }
    return new Reply("В коллекции нет элементов", Reply.Types.FINER);
  }
}
