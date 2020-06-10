package com.lab.server.commands.availableCommands;

import com.lab.common.reply.Reply;
import com.lab.server.commands.Command;
import com.lab.server.storage.Editor;

import java.util.ArrayList;
import java.util.List;

/** Clear command class */
public final class ClearCommand implements Command {
  @Override
  public String getKey() {
    return "clear";
  }

  @Override
  public String getInfo() {
    return "очистить коллекцию";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Clears the collection
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) {
    List<String> reply = new ArrayList<>();
    if (editor.getCollection().getSize() > 0) {
      editor.getCollection().clear();
      reply.add("Коллекция очищена");
      return new Reply(reply, Reply.Types.FINEST);
    } else {
      reply.add("В коллекции нет элементов");
    }
    return new Reply(reply, Reply.Types.FINER);
  }
}
