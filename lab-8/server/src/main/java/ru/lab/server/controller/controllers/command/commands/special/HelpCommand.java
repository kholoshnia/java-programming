package ru.lab.server.controller.controllers.command.commands.special;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.common.ArgumentMediator;
import ru.lab.common.CommandMediator;
import ru.lab.common.exitManager.ExitManager;
import ru.lab.common.transfer.response.Response;
import ru.lab.common.transfer.response.Status;
import ru.lab.server.controller.services.script.scriptExecutor.ScriptExecutor;
import ru.lab.server.model.domain.entity.entities.user.User;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class HelpCommand extends SpecialCommand {
  private static final Logger logger = LogManager.getLogger(HelpCommand.class);

  private static final String INFO_PATTERN = "%s : %s";
  private static final String ARGUMENT_PATTERN = "%s %s";
  private static final String ADDITIONAL_PATTERN = "%s {%s}";

  private final String helpPrefix;

  private final String entryCommandsPrefix;
  private final String historyCommandsPrefix;
  private final String modificationCommandsPrefix;
  private final String specialCommandsPrefix;
  private final String viewCommandsPrefix;

  private final String loginInfo;
  private final String logoutInfo;
  private final String registerInfo;

  private final String historyInfo;

  private final String insertInfo;
  private final String updateInfo;
  private final String removeKeyInfo;
  private final String clearInfo;
  private final String removeLowerInfo;
  private final String replaceIfLowerInfo;

  private final String infoInfo;
  private final String showInfo;
  private final String printAscendingInfo;
  private final String minByNameInfo;
  private final String countLessThanStartDateInfo;

  private final String helpInfo;
  private final String saveInfo;
  private final String executeScriptInfo;
  private final String exitInfo;

  private final String loginArgument;
  private final String passwordArgument;
  private final String userArgument;
  private final String workerArgument;
  private final String keyArgument;
  private final String idArgument;
  private final String startDateArgument;
  private final String fileNameArgument;

  public HelpCommand(
      Configuration configuration,
      CommandMediator commandMediator,
      ArgumentMediator argumentMediator,
      Map<String, String> arguments,
      User user,
      Locale locale,
      ExitManager exitManager,
      ScriptExecutor scriptExecutor) {
    super(
        configuration,
        commandMediator,
        argumentMediator,
        arguments,
        user,
        locale,
        exitManager,
        scriptExecutor);
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.HelpCommand");

    helpPrefix = resourceBundle.getString("prefixes.help");

    entryCommandsPrefix = resourceBundle.getString("prefixes.entryCommands");
    historyCommandsPrefix = resourceBundle.getString("prefixes.historyCommands");
    modificationCommandsPrefix = resourceBundle.getString("prefixes.modificationCommands");
    specialCommandsPrefix = resourceBundle.getString("prefixes.specialCommands");
    viewCommandsPrefix = resourceBundle.getString("prefixes.viewCommands");

    loginInfo = resourceBundle.getString("infos.login");
    logoutInfo = resourceBundle.getString("infos.logout");
    registerInfo = resourceBundle.getString("infos.register");

    historyInfo = resourceBundle.getString("infos.history");

    insertInfo = resourceBundle.getString("infos.insert");
    updateInfo = resourceBundle.getString("infos.update");
    removeKeyInfo = resourceBundle.getString("infos.removeKey");
    clearInfo = resourceBundle.getString("infos.clear");
    removeLowerInfo = resourceBundle.getString("infos.removeLower");
    replaceIfLowerInfo = resourceBundle.getString("infos.replaceIfLower");

    infoInfo = resourceBundle.getString("infos.info");
    showInfo = resourceBundle.getString("infos.show");
    printAscendingInfo = resourceBundle.getString("infos.printAscending");
    minByNameInfo = resourceBundle.getString("infos.minByName");
    countLessThanStartDateInfo = resourceBundle.getString("infos.countLessThanStartDate");

    helpInfo = resourceBundle.getString("infos.help");
    saveInfo = resourceBundle.getString("infos.save");
    executeScriptInfo = resourceBundle.getString("infos.executeScript");
    exitInfo = resourceBundle.getString("infos.exit");

    loginArgument = resourceBundle.getString("arguments.login");
    passwordArgument = resourceBundle.getString("arguments.password");
    userArgument = resourceBundle.getString("arguments.user");
    workerArgument = resourceBundle.getString("arguments.worker");
    keyArgument = resourceBundle.getString("arguments.key");
    idArgument = resourceBundle.getString("arguments.id");
    startDateArgument = resourceBundle.getString("arguments.startDate");
    fileNameArgument = resourceBundle.getString("arguments.fileName");
  }

  private String formEntryCommandsInfo() {
    return entryCommandsPrefix
        + System.lineSeparator()
        + String.format(
            INFO_PATTERN,
            String.format(
                ADDITIONAL_PATTERN,
                String.format(ARGUMENT_PATTERN, commandMediator.login, loginArgument),
                passwordArgument),
            loginInfo)
        + System.lineSeparator()
        + String.format(INFO_PATTERN, commandMediator.logout, logoutInfo)
        + System.lineSeparator()
        + String.format(
            INFO_PATTERN,
            String.format(ADDITIONAL_PATTERN, commandMediator.register, userArgument),
            registerInfo);
  }

  private String formHistoryCommandsInfo() {
    return historyCommandsPrefix
        + System.lineSeparator()
        + String.format(INFO_PATTERN, commandMediator.history, historyInfo);
  }

  private String formModificationCommandsInfo() {
    return modificationCommandsPrefix
        + System.lineSeparator()
        + String.format(
            INFO_PATTERN,
            String.format(
                ADDITIONAL_PATTERN,
                String.format(ARGUMENT_PATTERN, commandMediator.insert, keyArgument),
                workerArgument),
            insertInfo)
        + System.lineSeparator()
        + String.format(
            INFO_PATTERN,
            String.format(
                ADDITIONAL_PATTERN,
                String.format(ARGUMENT_PATTERN, commandMediator.update, idArgument),
                workerArgument),
            updateInfo)
        + System.lineSeparator()
        + String.format(
            INFO_PATTERN,
            String.format(ARGUMENT_PATTERN, commandMediator.removeKey, keyArgument),
            removeKeyInfo)
        + System.lineSeparator()
        + String.format(ARGUMENT_PATTERN, commandMediator.clear, clearInfo)
        + System.lineSeparator()
        + String.format(
            INFO_PATTERN,
            String.format(ADDITIONAL_PATTERN, commandMediator.removeLower, workerArgument),
            removeLowerInfo)
        + System.lineSeparator()
        + String.format(
            INFO_PATTERN,
            String.format(
                ADDITIONAL_PATTERN,
                String.format(ARGUMENT_PATTERN, commandMediator.replaceIfLower, workerArgument),
                keyArgument),
            replaceIfLowerInfo);
  }

  private String formViewCommandsInfo() {
    return viewCommandsPrefix
        + System.lineSeparator()
        + String.format(INFO_PATTERN, commandMediator.info, infoInfo)
        + System.lineSeparator()
        + String.format(INFO_PATTERN, commandMediator.show, showInfo)
        + System.lineSeparator()
        + String.format(INFO_PATTERN, commandMediator.printAscending, printAscendingInfo)
        + System.lineSeparator()
        + String.format(INFO_PATTERN, commandMediator.minByName, minByNameInfo)
        + System.lineSeparator()
        + String.format(
            INFO_PATTERN,
            String.format(
                ARGUMENT_PATTERN, commandMediator.countLessThanStartDate, startDateArgument),
            countLessThanStartDateInfo);
  }

  private String formSpecialCommandsInfo() {
    return specialCommandsPrefix
        + System.lineSeparator()
        + String.format(INFO_PATTERN, commandMediator.exit, exitInfo)
        + System.lineSeparator()
        + String.format(INFO_PATTERN, commandMediator.save, saveInfo)
        + System.lineSeparator()
        + String.format(
            INFO_PATTERN,
            String.format(ARGUMENT_PATTERN, commandMediator.executeScript, fileNameArgument),
            executeScriptInfo)
        + System.lineSeparator()
        + String.format(INFO_PATTERN, commandMediator.help, helpInfo);
  }

  @Override
  public Response executeCommand() {
    String result =
        helpPrefix
            + System.lineSeparator()
            + System.lineSeparator()
            + formEntryCommandsInfo()
            + System.lineSeparator()
            + System.lineSeparator()
            + formHistoryCommandsInfo()
            + System.lineSeparator()
            + System.lineSeparator()
            + formModificationCommandsInfo()
            + System.lineSeparator()
            + System.lineSeparator()
            + formViewCommandsInfo()
            + System.lineSeparator()
            + System.lineSeparator()
            + formSpecialCommandsInfo();

    logger.info(() -> "Information about commands formed.");
    return new Response(Status.OK, result);
  }
}
