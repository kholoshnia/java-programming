package com.lab.server.commands.availableCommands;

import com.lab.common.reply.Reply;
import com.lab.server.commands.Command;
import com.lab.server.executor.ExecutorException;
import com.lab.server.storage.Editor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Count less than start date command class */
public final class CountLessThanStartDateCommand implements Command {
  @Override
  public String getKey() {
    return "count_less_than_start_date";
  }

  @Override
  public String getInfo() {
    return "вывести количество элементов, значение поля startDate которых меньше заданного";
  }

  @Override
  public String getParameters() {
    return "startDate";
  }

  /**
   * Returns the number of elements whose startDate value is less than the specified
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) throws ExecutorException {
    if (editor.getValue() == null) {
      return new Reply("Требуется параметр (dd-MM-yyyy HH:mm:ss): startDate", Reply.Types.CONFIG);
    }
    if (editor.getCollection().getSize() > 0) {
      try {
        Date newStartDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(editor.getValue());
        long k =
            editor.getCollection().values().stream()
                .filter(el -> (el.getStartDate().compareTo(newStartDate) < 0))
                .count();
        if (k == 0) {
          return new Reply(
              "Элементы, значение поля startDate которых меньше заданного, отсутствуют",
              Reply.Types.FINER);
        } else {
          return new Reply(
              "Количество элементов, значение поля startDate которых меньше заданного: " + k,
              Reply.Types.INFO);
        }
      } catch (ParseException e) {
        throw new ExecutorException("Неверный формат даты (dd-MM-yyyy HH:mm:ss)");
      }
    }
    return new Reply("В коллекции нет элементов", Reply.Types.FINER);
  }
}
