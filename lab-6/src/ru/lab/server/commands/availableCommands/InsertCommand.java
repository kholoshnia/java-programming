package ru.lab.server.commands.availableCommands;

import ru.lab.common.element.Worker;
import ru.lab.common.generators.CreatorException;
import ru.lab.common.io.InputException;
import ru.lab.common.io.OutputException;
import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.executor.ExecutorException;
import ru.lab.server.storage.Editor;

/** Insert command class */
public final class InsertCommand implements Command {
  @Override
  public String getKey() {
    return "insert";
  }

  @Override
  public String getInfo() {
    return "добавить новый элемент с заданным ключом";
  }

  @Override
  public String getParameters() {
    return "key {element}";
  }

  /**
   * Inserts new element by key
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) throws ExecutorException {
    if (editor.getValue() == null) {
      return new Reply("Требуется параметр: ключ", Reply.Types.CONFIG);
    }
    int key;
    try {
      key = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException e) {
      throw new ExecutorException("Неверный формат ключа", e);
    }
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
    editor.getCollection().put(key, worker);
    return new Reply(
        "Добавление нового элемента с ключем " + key + " прошло успешно", Reply.Types.FINEST);
  }
}
