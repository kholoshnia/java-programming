package ru.lab.server.commands.availableCommands;

import ru.lab.common.io.Input;
import ru.lab.common.io.InputException;
import ru.lab.common.io.OutputException;
import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.executor.Executor;
import ru.lab.server.executor.ExecutorException;
import ru.lab.server.storage.Editor;

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
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) throws ExecutorException {
    List<String> reply = new ArrayList<>();
    if (editor.getValue() == null) {
      return new Reply("Требуется параметр: имя файла", Reply.Types.CONFIG);
    }
    Input in;
    if (Pattern.matches(
        "^(https?|s?ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
        editor.getValue())) {
      reply.add("Выполнение скрипта из файла (сеть): ");
      try {
        in =
            new Input(
                new BufferedReader(
                    new InputStreamReader(
                        new URL(editor.getValue().replaceAll("\\\\", "")).openStream())));
      } catch (AccessDeniedException e) {
        throw new ExecutorException("Доступ к файлу ограничен");
      } catch (FileNotFoundException e) {
        throw new ExecutorException("Файл не найден");
      } catch (Exception e) {
        throw new ExecutorException("Ошибка чтения файла");
      }
    } else if (Pattern.matches("(\\\\?([^/]*[/])*)([^/]+)", editor.getValue())) {
      reply.add("Выполнение скрипта из файла (локально): ");
      try {
        in =
            new Input(
                new BufferedReader(
                    new InputStreamReader(
                        new FileInputStream(editor.getValue().replaceAll("\\\\", "")))));
      } catch (Exception e) {
        throw new ExecutorException("Ошибка чтения файла");
      }
    } else {
      throw new ExecutorException("Неверное имя файла");
    }
    File file = new File(editor.getValue());
    if (editor.getFilesHashes().contains(file.getName().hashCode())) {
      throw new ExecutorException(
          "Скрипт уже исполнялся. Введите clear_files_history для очистки истории исполненных файлов со скриптами");
    } else {
      editor.getFilesHashes().add(file.getName().hashCode());
    }
    Executor executor = new Executor();
    editor.setIn(in);
    executor.setEditor(editor);
    editor.setFromFile(true);
    try {
      for (String command = in.readLine(); command != null; command = in.readLine()) {
        editor.setFromFile(true);
        reply.addAll(executor.executeCommand(command).getReply());
      }
    } catch (InputException e) {
      throw new ExecutorException("Ошибка чтения", e);
    } catch (OutputException e) {
      throw new ExecutorException("Ошибка записи", e);
    }
    editor.setFromFile(false);
    return new Reply(reply, Reply.Types.FINEST);
  }
}
