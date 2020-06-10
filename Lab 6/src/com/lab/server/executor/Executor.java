package com.lab.server.executor;

import com.lab.common.exchange.Request;
import com.lab.common.io.Input;
import com.lab.common.io.Output;
import com.lab.common.io.OutputException;
import com.lab.common.reply.Reply;
import com.lab.common.tools.Files;
import com.lab.common.tools.FilesException;
import com.lab.server.commands.AvailableCommands;
import com.lab.server.commands.Command;
import com.lab.server.storage.Editor;

import java.util.ArrayList;
import java.util.List;

/** Runner class contains main methods that run others */
public final class Executor {
  private Input in;
  private Output out;
  private Editor editor;
  private AvailableCommands availableCommands;

  public Executor() {
    in = new Input();
    out = new Output();
    editor = new Editor();
    availableCommands = new AvailableCommands();
  }

  public Input getIn() {
    return in;
  }

  public void setIn(Input in) {
    this.in = in;
  }

  public Output getOut() {
    return out;
  }

  public void setOut(Output out) {
    this.out = out;
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
   * @return Reply
   */
  public Reply executeCommand(String command) throws OutputException {
    editor.setValue(null);
    String commandName;
    if (command == null) {
      commandName = "";
      out.write(System.lineSeparator());
    } else {
      String[] commandAndValue = command.split(" ", 2);
      commandName = commandAndValue[0];
      if (commandAndValue.length == 2) {
        editor.setValue(commandAndValue[1]);
      }
    }
    Command com = availableCommands.getCommand(commandName);
    return executeCom(com);
  }

  /**
   * Executes command
   *
   * @param request command to be executed
   * @return Reply
   */
  public Reply executeCommand(Request request) {
    editor.setValue(request.getCommandParameter());
    Command com = availableCommands.getCommand(request.getCommandName());
    return executeCom(com);
  }

  /**
   * Execute command if it is not null
   *
   * @param com command to execute
   * @return Reply
   */
  private Reply executeCom(Command com) {
    if (com == null) {
      List<String> reply = new ArrayList<>();
      reply.add("Неверная команда. Введите help для отображения списка доступных команд");
      return new Reply(false, reply, Reply.Types.WARNING);
    }
    Reply reply = null;
    try {
      reply = com.execute(editor);
      editor.getCommandHistory().addCommand(com);
    } catch (ExecutorException e) {
      reply = new Reply(e.getMessage(), Reply.Types.SEVERE);
    }
    return reply;
  }

  public boolean setArgs(String[] args) throws OutputException {
    if (args.length == 0) {
      System.err.println("Необходим обязательный аргумент: Полное имя файла данных");
      return false;
    }
    try {
      new Files().checkFile(args[0]);
      editor.setDataFilePath(args[0]);
    } catch (FilesException e) {
      out.writeln(e.getMessage(), Output.Colors.RED);
    }
    return true;
  }
}
