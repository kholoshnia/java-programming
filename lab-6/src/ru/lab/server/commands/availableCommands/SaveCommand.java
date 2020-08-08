package ru.lab.server.commands.availableCommands;

import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.executor.ExecutorException;
import ru.lab.server.storage.Editor;
import ru.lab.server.storage.transfer.Saver;
import ru.lab.server.storage.transfer.SaverException;

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
