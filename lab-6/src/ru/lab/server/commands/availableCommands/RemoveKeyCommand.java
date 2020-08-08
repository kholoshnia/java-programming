package ru.lab.server.commands.availableCommands;

import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.executor.ExecutorException;
import ru.lab.server.storage.Editor;

/** Remove key command class */
public final class RemoveKeyCommand implements Command {
  @Override
  public String getKey() {
    return "remove_key";
  }

  @Override
  public String getInfo() {
    return "удалить элемент из коллекции по его ключу";
  }

  @Override
  public String getParameters() {
    return "key";
  }

  /**
   * Removes an item from the collection by key
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
          editor.getCollection().remove(key);
          return new Reply("Элемент удален по ключу " + key, Reply.Types.FINEST);
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
