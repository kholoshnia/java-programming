package ru.lab.server.commands.availableCommands;

import ru.lab.common.element.Worker;
import ru.lab.common.generators.CreatorException;
import ru.lab.common.io.InputException;
import ru.lab.common.io.OutputException;
import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.executor.ExecutorException;
import ru.lab.server.storage.Editor;

import java.util.Map;

/** Update command class */
public final class UpdateCommand implements Command {
  @Override
  public String getKey() {
    return "update";
  }

  @Override
  public String getInfo() {
    return "обновить значение элемента коллекции, id которого равен заданному";
  }

  @Override
  public String getParameters() {
    return "id {element}";
  }

  /**
   * Updates the value of a collection element whose id is equal to the specified
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) throws ExecutorException {
    if (editor.getValue() == null) {
      return new Reply("Требуется параметр: id", Reply.Types.CONFIG);
    }
    int id;
    try {
      id = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException e) {
      throw new ExecutorException("Неверный формат id: " + editor.getValue(), e);
    }
    Map.Entry<Integer, Worker> entry =
        editor.getCollection().getEntrySet().stream()
            .filter(el -> el.getValue().getId() == id)
            .findFirst()
            .orElse(null);
    if (entry == null) {
      throw new ExecutorException("Элемент с id: " + id + " не найден");
    } else {
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
      editor.getCollection().put(entry.getKey(), worker);
      return new Reply("Элемент обновлен по id " + id, Reply.Types.SEVERE);
    }
  }
}
