package com.lab.commands.availableCommands;

import com.lab.commands.Command;
import com.lab.runner.Editor;
import com.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;

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
          editor.getCollection().remove(key);
          response.add("Элемент удален по ключу " + key);
          return new Response(true, response, Response.Types.CORRECT);
        } else {
          response.add("Элемент с ключем " + key + " не найден");
        }
      } catch (NumberFormatException ex) {
        response.add("Неверый формат ключа: " + editor.getValue());
      }
    } else {
      response.add("В коллекции нет элементов");
      return new Response(false, response, Response.Types.UNNECESSARY);
    }
    return new Response(false, response, Response.Types.ERROR);
  }
}
