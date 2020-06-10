package com.lab.commands.availableCommands;

import com.lab.commands.Command;
import com.lab.element.Worker;
import com.lab.runner.Editor;
import com.lab.runner.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getValue() == null) {
      response.add("Требуется параметр (dd-MM-yyyy HH:mm:ss): startDate");
      return new Response(false, response, Response.Types.MISSING);
    }
    int k = 0;
    if (editor.getCollection().getSize() > 0) {
      try {
        Date newStartDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(editor.getValue());
        for (Map.Entry<Integer, Worker> el : editor.getCollection().getEntrySet()) {
          if (el.getValue().getStartDate().compareTo(newStartDate) < 0) {
            k++;
          }
        }
        if (k == 0) {
          response.add("Элементы, значение поля startDate которых меньше заданного, отсутствуют");
          return new Response(true, response, Response.Types.UNNECESSARY);
        } else {
          response.add(
              "Количество элементов, значение поля startDate которых меньше заданного: " + k);
          return new Response(true, response, Response.Types.TEXT);
        }
      } catch (ParseException ex) {
        response.add("Неверный формат даты (dd-MM-yyyy HH:mm:ss)");
      }
    } else {
      response.add("В коллекции нет элементов");
    }
    return new Response(false, response, Response.Types.UNNECESSARY);
  }
}
