package ru.lab.server.commands.availableCommands;

import ru.lab.common.reply.Reply;
import ru.lab.server.commands.Command;
import ru.lab.server.executor.ExecutorException;
import ru.lab.server.storage.Editor;

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
   * @return Reply and correctness
   */
  @Override
  public Reply execute(Editor editor) throws ExecutorException {
    if (!editor.getFromFile()) {
      if (editor.getFilesHashes().size() == 0) {
        return new Reply("История исполненных файлов со скриптами отсутствует", Reply.Types.FINER);
      }
      editor.clearFilesHashes();
      return new Reply("История исполненных файлов со скриптами очищена", Reply.Types.FINEST);
    }
    throw new ExecutorException(
        "История исполненных файлов со скриптами не может быть очищена с помощью скрипта из файла");
  }
}
