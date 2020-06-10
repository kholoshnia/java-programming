package com.lab.server.commands.availableCommands;

import com.lab.common.element.Worker;
import com.lab.common.generators.CreatorException;
import com.lab.common.io.InputException;
import com.lab.common.io.OutputException;
import com.lab.common.reply.Reply;
import com.lab.server.commands.Command;
import com.lab.server.executor.ExecutorException;
import com.lab.server.storage.Editor;

/** Replace if lower command class */
public final class ReplaceIfLowerCommand implements Command {
  @Override
  public String getKey() {
    return "replace_if_lower";
  }

  @Override
  public String getInfo() {
    return "заменить значение по ключу, если новое значение меньше старого";
  }

  @Override
  public String getParameters() {
    return "key {element}";
  }

  /**
   * Replaces the value by key, if the new value is less than the old
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) throws ExecutorException {
    if (editor.getValue() == null) {
      return new Reply("Требуется параметр: ключ", Reply.Types.CONFIG);
    }
    if (editor.getCollection().getSize() > 0) {
      try {
        int key = Integer.parseInt(editor.getValue());
        if (editor.getCollection().containsKey(key)) {
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
          if (editor.getCollection().get(key).compareTo(worker) > 0) {
            editor.getCollection().replace(key, worker);
            return new Reply("Элемент заменен по ключу " + key, Reply.Types.FINEST);
          } else {
            return new Reply(
                "Значение элемента больше или равен элементу по ключу "
                    + key
                    + " или значение зарплаты не установлено у одного из элементов",
                Reply.Types.FINEST);
          }
        } else {
          throw new ExecutorException("Элемент с ключем " + key + " не найден");
        }
      } catch (NumberFormatException e) {
        throw new ExecutorException("Неверый формат ключа: " + editor.getValue(), e);
      }
    }
    return new Reply("В коллекции нет элементов", Reply.Types.FINER);
  }
}
