package com.lab.commands.availableCommands;

import com.lab.commands.Command;
import com.lab.element.Worker;
import com.lab.runner.Editor;
import com.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;

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
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getValue() == null) {
      response.add("Требуется параметр: ключ");
      return new Response(false, response, Response.Types.MISSING);
    }
    int key;
    try {
      key = Integer.parseInt(editor.getValue());
    } catch (NumberFormatException ex) {
      response.add("Неверный формат ключа");
      return new Response(false, response, Response.Types.ERROR);
    }
    Worker worker = editor.create();
    if (worker == null) {
      return new Response(false, response, Response.Types.FAIL);
    }
    editor.getCollection().put(key, worker);
    response.add("Добавление нового элемента с ключем " + key + " прошло успешно");
    return new Response(true, response, Response.Types.CORRECT);
  }
}
