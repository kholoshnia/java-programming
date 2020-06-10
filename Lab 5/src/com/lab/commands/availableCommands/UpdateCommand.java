package com.lab.commands.availableCommands;

import com.lab.commands.Command;
import com.lab.element.Worker;
import com.lab.runner.Editor;
import com.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;
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
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getValue() == null) {
      response.add("Требуется параметр: id");
      return new Response(false, response, Response.Types.MISSING);
    }
    int id;
    try {
      id = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException ex) {
      response.add("Неверный формат id: " + editor.getValue());
      return new Response(false, response, Response.Types.ERROR);
    }
    for (Map.Entry<Integer, Worker> el : editor.getCollection().getEntrySet()) {
      if (el.getValue().getId() == id) {
        Worker worker = editor.create();
        if (worker == null) {
          return new Response(false, response, Response.Types.FAIL);
        }
        editor.getCollection().put(el.getKey(), worker);
        response.add("Элемент обновлен по id " + id);
        return new Response(true, response, Response.Types.CORRECT);
      }
    }
    response.add("Элемент с id: " + id + " не найден");
    return new Response(false, response, Response.Types.ERROR);
  }
}
