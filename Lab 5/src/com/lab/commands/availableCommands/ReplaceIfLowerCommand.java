package com.lab.commands.availableCommands;

import com.lab.commands.Command;
import com.lab.element.Worker;
import com.lab.runner.Editor;
import com.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;

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
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getValue() == null) {
      response.add("Требуется параметр: ключ");
      return new Response(false, response, Response.Types.MISSING);
    }
    if (editor.getCollection().getSize() > 0) {
      try {
        int key = Integer.parseInt(editor.getValue());

        if (editor.getCollection().containsKey(key)) {
          Worker worker = editor.create();
          if (worker == null) {
            return new Response(false, response, Response.Types.FAIL);
          }
          if (editor.getCollection().get(key).compareTo(worker) > 0) {
            editor.getCollection().replace(key, worker);
            response.add("Элемент заменен по ключу " + key);
            return new Response(true, response, Response.Types.CORRECT);
          } else {
            response.add(
                "Значение элемента больше или равен элементу по ключу "
                    + key
                    + " или значение зарплаты не установлено у одного из элементов");
          }
        } else {
          response.add("Элемент с ключем " + key + " не найден");
          return new Response(false, response, Response.Types.FAIL);
        }
      } catch (NumberFormatException ex) {
        response.add("Неверый формат ключа: " + editor.getValue());
      }
    } else {
      System.out.println("В коллекции нет элементов");
      return new Response(false, response, Response.Types.UNNECESSARY);
    }
    return new Response(false, response, Response.Types.ERROR);
  }
}
