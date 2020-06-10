package com.lab.runner;

import com.lab.commands.AvailableCommands;
import com.lab.commands.Command;
import com.lab.console.Input;
import com.lab.console.Output;

import java.io.File;

/** Runner class contains main methods that run others */
public final class Runner {
  private Input in;
  private Output out;
  private Editor editor;
  private AvailableCommands availableCommands;

  public Runner() {
    in = new Input();
    out = new Output();
    editor = new Editor();
    availableCommands = new AvailableCommands();
  }

  /** Load data from file */
  public void load() {
    Output out = new Output();
    Response response = editor.load();
    for (String el : response.getResponse()) {
      out.println(el, response.getType().getColor());
    }
  }

  /**
   * Returns editor
   *
   * @return Editor
   */
  public Editor getEditor() {
    return editor;
  }

  public void setEditor(Editor editor) {
    this.editor = editor;
  }

  /**
   * Executes command by its name
   *
   * @param command command name to be executed
   */
  public void executeCommand(String command) {
    editor.setValue(null);
    String commandName;
    if (command == null) {
      commandName = "";
      out.print(System.lineSeparator());
    } else {
      String[] commandAndValue = command.split(" ", 2);
      commandName = commandAndValue[0];
      if (commandAndValue.length == 2) {
        editor.setValue(commandAndValue[1]);
      }
    }
    Command com = availableCommands.getCommand(commandName);
    if (com == null) {
      out.println(
          "Неверная команда. Введите help для отображения списка доступных команд",
          Output.Colors.MAGENTA);
      return;
    }
    Response response = com.execute(editor);
    for (String el : response.getResponse()) {
      out.println(el, response.getType().getColor());
    }
    if (response.isCorrect()) {
      editor.getCommandHistory().addCommand(com);
    }
  }

  /** Main method that runs other algorithms */
  public void run() {
    while (editor.isRunning()) {
      out.print("Введите команду: ");
      executeCommand(in.readLine());
    }
  }

  /**
   * Returns true if file path is correct
   *
   * @param args system arguments
   * @return True if file path is correct
   */
  public boolean setArgs(String[] args) {
    if (args.length == 0) {
      out.println(
          "Необходим обязательный аргумент: Полное имя файла данных",
          Response.Types.MISSING.getColor());
      return false;
    }
    File file = new File(args[0]);
    if (file.isDirectory()) {
      out.println(
          "Необходим обязательный аргумент: Полное имя файла данных, не директория",
          Response.Types.FAIL.getColor());
      return false;
    }
    if (!file.exists()) {
      out.println("Файл не найден", Response.Types.ERROR.getColor());
      return false;
    }
    if (!file.canRead()) {
      out.println("Ошибка доступа на чтение", Response.Types.FAIL.getColor());
      return false;
    }
    if (!file.canWrite()) {
      out.println("Ошибка доступа на запись", Response.Types.FAIL.getColor());
      return false;
    }
    editor.setDataFilePath(args[0]);
    return true;
  }
}
