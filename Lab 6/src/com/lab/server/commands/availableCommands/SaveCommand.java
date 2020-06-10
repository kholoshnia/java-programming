package com.lab.server.commands.availableCommands;

import com.lab.common.reply.Reply;
import com.lab.server.commands.Command;
import com.lab.server.executor.ExecutorException;
import com.lab.server.storage.Editor;
import com.lab.server.storage.transfer.Saver;
import com.lab.server.storage.transfer.SaverException;

/** Save command class */
public final class SaveCommand implements Command {
  @Override
  public String getKey() {
    return "save";
  }

  @Override
  public String getInfo() {
    return "сохранить коллекцию в файл";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Saves collection to file
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) throws ExecutorException {
    try {
      new Saver().saveXML(editor.getCollection(), editor.getDataFilePath());
    } catch (SaverException e) {
      throw new ExecutorException("Ошибка при сохранении коллекции в XML файл", e);
    }
    return new Reply("Коллекция сохранена в XML файл", Reply.Types.FINEST);
  }
}
