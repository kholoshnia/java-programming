package ru.lab.server.commands.availableCommands;

import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.storage.Editor;

/** Exit command class */
public final class ExitCommand implements Command {
  @Override
  public String getKey() {
    return "exit";
  }

  @Override
  public String getInfo() {
    return "завершить программу (без сохранения в файл)";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Stops program execution
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) {
    editor.setRunning(false);
    return new Reply("Завершение работы программы", Reply.Types.FINEST);
  }
}
