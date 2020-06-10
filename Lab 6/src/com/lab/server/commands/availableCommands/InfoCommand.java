package com.lab.server.commands.availableCommands;

import com.lab.common.reply.Reply;
import com.lab.server.commands.Command;
import com.lab.server.storage.Editor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/** Info command class */
public final class InfoCommand implements Command {
  @Override
  public String getKey() {
    return "info";
  }

  @Override
  public String getInfo() {
    return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Shows info about collection
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) {
    List<String> reply = new ArrayList<>();
    reply.add("Тип коллекции: " + editor.getCollection().getCollectionType());
    reply.add(
        "Дата инициализации коллекции: "
            + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                .format((editor.getCollection().getInitTime())));
    reply.add("Количество элементов коллекции: " + editor.getCollection().getSize());
    return new Reply(reply, Reply.Types.INFO);
  }
}
