package ru.lab.commands.availableCommands;

import ru.lab.commands.Command;
import ru.lab.console.Input;
import ru.lab.runner.Editor;
import ru.lab.runner.Response;
import ru.lab.runner.Runner;

import java.io.*;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/** Execute script command class */
public final class ExecuteScriptCommand implements Command {
  @Override
  public String getKey() {
    return "execute_script";
  }

  @Override
  public String getInfo() {
    return "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
  }

  @Override
  public String getParameters() {
    return "file_name";
  }

  /**
   * Execute script from file (local or net)
   *
   * @return Response and correctness
   */
  @Override
  public Response execute(Editor editor) {
    List<String> response = new ArrayList<>();
    if (editor.getValue() == null) {
      response.add("Требуется параметр: имя файла");
      return new Response(false, response, Response.Types.MISSING);
    }
    Input in = new Input();
    if (Pattern.matches(
        "^(https?|s?ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
        editor.getValue())) {
      editor.getOut().println("Выполнение скрипта из файла (сеть): ");
      try {
        in.setReader(
            new BufferedReader(
                new InputStreamReader(
                    new URL(editor.getValue().replaceAll("\\\\", "")).openStream())));
      } catch (AccessDeniedException ex) {
        response.add("Доступ к файлу ограничен");
        return new Response(false, response, Response.Types.ERROR);
      } catch (FileNotFoundException ex) {
        response.add("Файл не найден");
        return new Response(false, response, Response.Types.ERROR);
      } catch (Exception ex) {
        response.add("Ошибка чтения файла");
        return new Response(false, response, Response.Types.ERROR);
      }
    } else if (Pattern.matches("(\\\\?([^/]*[/])*)([^/]+)", editor.getValue())) {
      editor.getOut().println("Выполнение скрипта из файла (локально): ");
      try {
        in.setReader(
            new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(editor.getValue().replaceAll("\\\\", "")))));
      } catch (Exception ex) {
        response.add("Ошибка чтения файла");
        return new Response(false, response, Response.Types.ERROR);
      }
    } else {
      response.add("Неверное имя файла");
    }
    File file = new File(editor.getValue());
    if (editor.getFilesHashes().contains(file.getName().hashCode())) {
      response.add(
          "Скрипт уже исполнялся. Введите clear_files_history для очистки истории исполненных файлов со скриптами");
      return new Response(false, response, Response.Types.FAIL);
    } else {
      editor.getFilesHashes().add(file.getName().hashCode());
    }
    Runner runner = new Runner();
    editor.setIn(in);
    runner.setEditor(editor);
    editor.setFromFile(true);
    for (String command = in.readLine(); command != null; command = in.readLine()) {
      editor.setFromFile(true);
      runner.executeCommand(command);
    }
    editor.setFromFile(false);
    return new Response(true, response, Response.Types.CORRECT);
  }
}
