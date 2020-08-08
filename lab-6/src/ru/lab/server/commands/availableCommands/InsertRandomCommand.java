package ru.lab.server.commands.availableCommands;

import ru.lab.common.element.Worker;
import ru.lab.common.generators.Randomizer;
import ru.lab.common.generators.RandomizerException;
import ru.lab.common.reply.Reply;
import ru.lab.common.tools.Random;
import ru.lab.common.tools.RandomException;
import ru.lab.server.commands.Command;
import ru.lab.server.executor.ExecutorException;
import ru.lab.server.storage.Editor;

/** Insert command class */
public final class InsertRandomCommand implements Command {
  @Override
  public String getKey() {
    return "insert_random";
  }

  @Override
  public String getInfo() {
    return "добавить случайно сгенерированные эелементы";
  }

  @Override
  public String getParameters() {
    return "number_of_elements";
  }

  /**
   * Inserts new element by key
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) throws ExecutorException {
    if (editor.getValue() == null) {
      return new Reply("Требуется параметр: количество элементов", Reply.Types.CONFIG);
    }
    int number;
    try {
      number = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException e) {
      throw new ExecutorException("Неверное количество элементов", e);
    }
    if (number <= 0 || number >= 10000) {
      throw new ExecutorException("Количество элементов должно быть больше 0 и меньше 10000");
    }
    for (int i = 0; i < number; i++) {
      Worker worker;
      try {
        worker = new Randomizer().random();
      } catch (RandomizerException e) {
        throw new ExecutorException("Ошибка при случайногой генерации нового элемента", e);
      }
      worker.generateId();
      worker.generateCreationDate();
      try {
        editor.getCollection().put(new Random().randomKey(editor.getCollection().keySet()), worker);
      } catch (RandomException e) {
        throw new ExecutorException("Ошибка при генерации случайного ключа", e);
      }
    }
    return new Reply(
        "Добавление " + number + " случайно сгенерированных эелементов прошло успешно",
        Reply.Types.FINEST);
  }
}
