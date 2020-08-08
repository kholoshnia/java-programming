package ru.lab.commands.availableCommands;

import ru.lab.commands.Command;
import ru.lab.runner.Editor;
import ru.lab.runner.Response;

import java.util.ArrayList;
import java.util.List;

/** Clear files history command class */
public final class ClearFilesHistoryCommand implements Command {
  @Override
  public String getKey() {
    return "clear_files_history";
  }

  @Override
  public String getInfo() {
    return "очистить историю исполненных файлов со скриптами";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Clears history of executes files with scripts
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (!editor.getFromFile()) {
      if (editor.getFilesHashes().size() == 0) {
        response.add("История исполненных файлов со скриптами отсутствует");
        return new Response(false, response, Response.Types.UNNECESSARY);
      }
      editor.clearFilesHashes();
      response.add("История исполненных файлов со скриптами очищена");
      return new Response(true, response, Response.Types.CORRECT);
    } else {
      response.add(
          "История исполненных файлов со скриптами не может быть очищена с помощью скрипта из файла");
    }
    return new Response(false, response, Response.Types.ERROR);
  }
}
