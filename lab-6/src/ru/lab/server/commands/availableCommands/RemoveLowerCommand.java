package ru.lab.server.commands.availableCommands;

import ru.lab.common.element.Worker;
import ru.lab.common.generators.CreatorException;
import ru.lab.common.io.InputException;
import ru.lab.common.io.OutputException;
import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.executor.ExecutorException;
import ru.lab.server.storage.Editor;

/** Remove lower command class */
public final class RemoveLowerCommand implements Command {
  @Override
  public String getKey() {
    return "remove_lower";
  }

  @Override
  public String getInfo() {
    return "удалить из коллекции все элементы, меньшие, чем заданный";
  }

  @Override
  public String getParameters() {
    return "{element}";
  }

  /**
   * Removes all elements smaller than the specified from the collection
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) throws ExecutorException {
    if (editor.getCollection().getSize() > 0) {
      Worker worker;
      try {
        worker = editor.getWorker();
      } catch (CreatorException e) {
        throw new ExecutorException("Ошибка при создании нового элемента", e);
      } catch (OutputException e) {
        throw new ExecutorException("Ошибка записи", e);
      } catch (InputException e) {
        throw new ExecutorException("Ошибка чтения", e);
      }
      if (worker == null) {
        return new Reply(Reply.Types.FINEST);
      }
      editor
          .getCollection()
          .getEntrySet()
          .removeIf(entry -> entry.getValue().compareTo(worker) < 0);
      return new Reply("Элементы коллекции меньше, чем заданный удалены", Reply.Types.FINEST);
    }
    return new Reply("В коллекции нет элементов", Reply.Types.FINER);
  }
}
