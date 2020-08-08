package ru.lab.server.commands.availableCommands;

import ru.lab.common.io.Output;
import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.storage.Editor;

import java.util.ArrayList;
import java.util.List;

/** History command class */
public final class HistoryCommand implements Command {
  @Override
  public String getKey() {
    return "history";
  }

  @Override
  public String getInfo() {
    return "вывести последние 9 команд (без их аргументов)";
  }

  @Override
  public String getParameters() {
    return null;
  }

  /**
   * Shows history for last 9 commands
   *
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) {
    List<String> reply = new ArrayList<>();
    if (editor.getCommandHistory().getCommands().size() > 0) {
      reply.add(
          Output.Decorators.BOLD.getDecoratorCode()
              + "История комманд: "
              + Output.Decorators.RESET.getDecoratorCode());
      editor.getCommandHistory().getCommands().stream()
          .skip(Math.max(0, editor.getCommandHistory().getCommands().size() - 9))
          .forEach(el -> reply.add(el.getKey()));
      return new Reply(reply, Reply.Types.INFO);
    }
    return new Reply("История комманд отсутствует", Reply.Types.FINER);
  }
}
