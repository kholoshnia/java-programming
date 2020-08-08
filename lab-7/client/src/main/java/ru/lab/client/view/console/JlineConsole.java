package ru.lab.client.view.console;

import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.reader.Candidate;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import ru.lab.client.controller.localeManager.LocaleListener;
import ru.lab.client.view.console.exceptions.ConsoleException;
import ru.lab.common.CommandMediator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** Setups Jline line reader with the specified options. */
public final class JlineConsole implements LocaleListener {
  private static final Logger logger = LogManager.getLogger(JlineConsole.class);

  private final Terminal terminal;
  private final CommandMediator commandMediator;
  private final String dateTimePattern;

  private String executableCommandsGroup;
  private String datesGroup;
  private String statusesGroup;
  private String eyeColorsGroup;
  private String hairColorsGroup;

  private String executableCommandDescription;
  private String dateDescription;
  private String localDateTimeDescription;
  private String statusDescription;
  private String eyeColorDescription;
  private String hairColorDescription;

  public JlineConsole(
      Configuration configuration,
      InputStream inputStream,
      OutputStream outputStream,
      CommandMediator commandMediator)
      throws ConsoleException {
    terminal = initTerminal(inputStream, outputStream);
    this.commandMediator = commandMediator;
    dateTimePattern = configuration.getString("dateTimePattern");
  }

  @Override
  public void changeLocale(Locale locale) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.JlineConsole", locale);

    executableCommandsGroup = resourceBundle.getString("groups.executableCommand");
    datesGroup = resourceBundle.getString("groups.dates");
    statusesGroup = resourceBundle.getString("groups.statuses");
    eyeColorsGroup = resourceBundle.getString("groups.eyeColors");
    hairColorsGroup = resourceBundle.getString("groups.hairColors");

    executableCommandDescription = resourceBundle.getString("descriptions.executableCommand");
    dateDescription = resourceBundle.getString("descriptions.date");
    localDateTimeDescription = resourceBundle.getString("descriptions.localDateTime");
    statusDescription = resourceBundle.getString("descriptions.status");
    eyeColorDescription = resourceBundle.getString("descriptions.eyeColor");
    hairColorDescription = resourceBundle.getString("descriptions.hairColor");
  }

  private Terminal initTerminal(InputStream inputStream, OutputStream outputStream)
      throws ConsoleException {
    Terminal terminal;

    try {
      terminal = TerminalBuilder.builder().streams(inputStream, outputStream).system(true).build();
    } catch (IOException e) {
      throw new ConsoleException(e);
    }

    return terminal;
  }

  public LineReader getLineReader() {
    return LineReaderBuilder.builder()
        .terminal(terminal)
        .completer(
            (reader, line, candidates) ->
                candidates.addAll(
                    new ArrayList<Candidate>() {
                      {
                        addAll(getCommandCandidates());
                        add(getDateCandidate());
                        add(getLocalDateTimeCandidate());
                        addAll(getStatusCandidates());
                        addAll(getEyeColorCandidates());
                        addAll(getHairColorCandidates());
                      }
                    }))
        .build();
  }

  public PrintWriter getPrintWriter() {
    return terminal.writer();
  }

  private List<Candidate> getCommandCandidates() {
    logger.info("Forming command candidates.");
    List<Candidate> candidates = new ArrayList<>();

    commandMediator
        .getCommands()
        .forEach(
            command ->
                candidates.add(
                    new Candidate(
                        command,
                        command,
                        executableCommandsGroup,
                        executableCommandDescription,
                        null,
                        null,
                        true)));

    return candidates;
  }

  private Candidate getDateCandidate() {
    String date = new SimpleDateFormat(dateTimePattern).format(new Date());
    logger.info("Forming date candidate.");
    return new Candidate(date, date, datesGroup, dateDescription, null, null, true);
  }

  private Candidate getLocalDateTimeCandidate() {
    String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    logger.info("Forming local date time candidate.");
    return new Candidate(
        localDateTime, localDateTime, datesGroup, localDateTimeDescription, null, null, true);
  }

  private List<Candidate> getStatusCandidates() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.WorkerValidator");
    logger.info("Forming status candidates.");
    return new ArrayList<Candidate>() {
      {
        add(newStatusCandidate(resourceBundle.getString("statuses.fired")));
        add(newStatusCandidate(resourceBundle.getString("statuses.hired")));
        add(newStatusCandidate(resourceBundle.getString("statuses.promotion")));
      }
    };
  }

  private Candidate newStatusCandidate(String status) {
    return new Candidate(status, status, statusesGroup, statusDescription, null, null, true);
  }

  private List<Candidate> getEyeColorCandidates() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.PersonValidator");
    logger.info("Forming status candidates.");
    return new ArrayList<Candidate>() {
      {
        add(newEyeColorCandidate(resourceBundle.getString("eyeColors.black")));
        add(newEyeColorCandidate(resourceBundle.getString("eyeColors.yellow")));
        add(newEyeColorCandidate(resourceBundle.getString("eyeColors.orange")));
        add(newEyeColorCandidate(resourceBundle.getString("eyeColors.white")));
        add(newEyeColorCandidate(resourceBundle.getString("eyeColors.brown")));
      }
    };
  }

  private Candidate newEyeColorCandidate(String eyeColor) {
    return new Candidate(eyeColor, eyeColor, eyeColorsGroup, eyeColorDescription, null, null, true);
  }

  private List<Candidate> getHairColorCandidates() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("localized.PersonValidator");
    logger.info("Forming status candidates.");
    return new ArrayList<Candidate>() {
      {
        add(newHairColorCandidate(resourceBundle.getString("hairColors.black")));
        add(newHairColorCandidate(resourceBundle.getString("hairColors.blue")));
        add(newHairColorCandidate(resourceBundle.getString("hairColors.orange")));
        add(newHairColorCandidate(resourceBundle.getString("hairColors.white")));
      }
    };
  }

  private Candidate newHairColorCandidate(String hairColor) {
    return new Candidate(
        hairColor, hairColor, hairColorsGroup, hairColorDescription, null, null, true);
  }
}
