package ru.lab.client.app.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.client.app.Client;
import ru.lab.client.app.connection.ServerWorker;
import ru.lab.client.app.guice.exceptions.ProvidingException;
import ru.lab.client.controller.argumentFormer.ArgumentFormer;
import ru.lab.client.controller.argumentFormer.ArgumentValidator;
import ru.lab.client.controller.argumentFormer.FormerMediator;
import ru.lab.client.controller.argumentFormer.argumentFormers.*;
import ru.lab.client.controller.localeManager.LocaleListener;
import ru.lab.client.controller.localeManager.LocaleManager;
import ru.lab.client.controller.responseHandler.MessageMediator;
import ru.lab.client.controller.responseHandler.ResponseHandler;
import ru.lab.client.controller.responseHandler.formatter.Formatter;
import ru.lab.client.controller.responseHandler.formatter.StringFormatter;
import ru.lab.client.controller.responseHandler.responseHandlers.*;
import ru.lab.client.controller.validator.validators.*;
import ru.lab.client.view.console.Console;
import ru.lab.client.view.console.Terminal;
import ru.lab.client.view.console.exceptions.ConsoleException;
import ru.lab.common.ArgumentMediator;
import ru.lab.common.CommandMediator;
import ru.lab.common.chunker.ByteChunker;
import ru.lab.common.chunker.Chunker;
import ru.lab.common.exitManager.ExitListener;
import ru.lab.common.exitManager.ExitManager;
import ru.lab.common.guice.CommonModule;
import ru.lab.common.serizliser.Serializer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ClientModule extends AbstractModule {
  private static final Logger logger = LogManager.getLogger(ClientModule.class);

  private static final String CLIENT_CONFIG_PATH = "client.properties";

  public ClientModule(String[] args) {}

  @Override
  protected void configure() {
    install(new CommonModule());
    logger.debug(() -> "Common module was installed.");

    bind(DateFormer.class).in(Scopes.SINGLETON);
    bind(KeyFormer.class).in(Scopes.SINGLETON);
    bind(LoginFormer.class).in(Scopes.SINGLETON);
    bind(NewWorkerFormer.class).in(Scopes.SINGLETON);
    bind(NewWorkerIdFormer.class).in(Scopes.SINGLETON);
    bind(NewWorkerKeyFormer.class).in(Scopes.SINGLETON);
    bind(NoArgumentsFormer.class).in(Scopes.SINGLETON);
    bind(RegisterFormer.class).in(Scopes.SINGLETON);
    bind(ScriptFormer.class).in(Scopes.SINGLETON);
    logger.debug(() -> "Formers were configured.");

    bind(WorkerValidator.class).in(Scopes.SINGLETON);
    bind(CoordinatesValidator.class).in(Scopes.SINGLETON);
    bind(PersonValidator.class).in(Scopes.SINGLETON);
    bind(LocationValidator.class).in(Scopes.SINGLETON);
    bind(RegisterValidator.class).in(Scopes.SINGLETON);
    logger.debug(() -> "Validators were configured.");

    bind(StringFormatter.class).in(Scopes.SINGLETON);
    bind(Formatter.class).to(StringFormatter.class);
    bind(MessageMediator.class).in(Scopes.SINGLETON);
    bind(FormerMediator.class).in(Scopes.SINGLETON);
    logger.debug(() -> "Controller was configured.");

    bind(Console.class).to(Terminal.class);
    bind(Client.class).in(Scopes.SINGLETON);
    logger.debug(() -> "Client was configured.");
  }

  @Provides
  @Singleton
  Terminal provideConsole(
      Configuration configuration,
      ExitManager exitManager,
      ServerWorker serverWorker,
      CommandMediator commandMediator,
      LocaleManager localeManager,
      FormerMediator formerMediator,
      List<ResponseHandler> responseHandlers)
      throws ProvidingException {
    Terminal console;

    try {
      console =
          new Terminal(
              configuration,
              exitManager,
              System.in,
              System.out,
              serverWorker,
              commandMediator,
              localeManager,
              formerMediator,
              responseHandlers);
    } catch (ConsoleException e) {
      throw new ProvidingException(e);
    }

    logger.debug(() -> "Provided Console.");
    return console;
  }

  @Provides
  @Singleton
  ByteChunker provideChunker(Configuration configuration) {
    int bufferSize = configuration.getInt("server.bufferSize");
    String stopWord = configuration.getString("server.stopWord");

    ByteChunker chunker = new Chunker(bufferSize, stopWord);
    logger.debug(() -> "Provided ByteChunker.");
    return chunker;
  }

  @Provides
  @Singleton
  ServerWorker provideServerWorker(
      Configuration configuration, ByteChunker chunker, Serializer serializer)
      throws ProvidingException {
    ServerWorker serverWorker;

    try {
      InetAddress address = InetAddress.getByName(configuration.getString("server.address"));
      int bufferSize = configuration.getInt("server.bufferSize");
      int port = configuration.getInt("server.port");
      serverWorker = new ServerWorker(address, port, bufferSize, chunker, serializer);
    } catch (UnknownHostException e) {
      logger.fatal(() -> "Cannot provide Server.", e);
      throw new ProvidingException(e);
    }

    logger.debug(() -> "Provided ServerWorker.");
    return serverWorker;
  }

  @Provides
  @Singleton
  LocaleManager provideLocaleManager(
      MessageMediator messageMediator,
      WorkerValidator workerValidator,
      CoordinatesValidator coordinatesValidator,
      PersonValidator personValidator,
      LocationValidator locationValidator,
      RegisterValidator registerValidator,
      KeyFormer keyFormer,
      LoginFormer loginFormer,
      NewWorkerFormer newWorkerFormer,
      NewWorkerIdFormer newWorkerIdFormer,
      NewWorkerKeyFormer newWorkerKeyFormer,
      NoArgumentsFormer noArgumentsFormer,
      RegisterFormer registerFormer,
      ScriptFormer scriptFormer) {
    List<LocaleListener> entities =
        new ArrayList<LocaleListener>() {
          {
            add(messageMediator);
            add(workerValidator);
            add(coordinatesValidator);
            add(personValidator);
            add(locationValidator);
            add(registerValidator);
            add(keyFormer);
            add(loginFormer);
            add(newWorkerFormer);
            add(newWorkerIdFormer);
            add(newWorkerKeyFormer);
            add(noArgumentsFormer);
            add(registerFormer);
            add(scriptFormer);
          }
        };

    LocaleManager localeManager = new LocaleManager(entities);
    logger.debug(() -> "Provided LocaleManager.");
    return localeManager;
  }

  @Provides
  @Singleton
  Map<String, ArgumentFormer> provideArgumentFormerMap(
      CommandMediator commandMediator,
      DateFormer dateFormer,
      KeyFormer keyFormer,
      LoginFormer loginFormer,
      NewWorkerFormer newWorkerFormer,
      NewWorkerIdFormer newWorkerIdFormer,
      NewWorkerKeyFormer newWorkerKeyFormer,
      NoArgumentsFormer noArgumentsFormer,
      RegisterFormer registerFormer,
      ScriptFormer scriptFormer) {
    Map<String, ArgumentFormer> argumentFormerMap =
        new HashMap<String, ArgumentFormer>() {
          {
            put(commandMediator.login, loginFormer);
            put(commandMediator.logout, noArgumentsFormer);
            put(commandMediator.register, registerFormer);
            put(commandMediator.history, noArgumentsFormer);
            put(commandMediator.insert, newWorkerKeyFormer);
            put(commandMediator.update, newWorkerIdFormer);
            put(commandMediator.removeKey, keyFormer);
            put(commandMediator.clear, noArgumentsFormer);
            put(commandMediator.removeLower, newWorkerFormer);
            put(commandMediator.replaceIfLower, newWorkerKeyFormer);
            put(commandMediator.info, noArgumentsFormer);
            put(commandMediator.show, noArgumentsFormer);
            put(commandMediator.printAscending, noArgumentsFormer);
            put(commandMediator.minByName, noArgumentsFormer);
            put(commandMediator.countLessThanStartDate, dateFormer);
            put(commandMediator.help, noArgumentsFormer);
            put(commandMediator.save, noArgumentsFormer);
            put(commandMediator.executeScript, scriptFormer);
            put(commandMediator.exit, noArgumentsFormer);
          }
        };

    logger.debug(() -> "Provided argument former map.");
    return argumentFormerMap;
  }

  @Provides
  @Singleton
  Map<String, ArgumentValidator> provideArgumentValidatorMap(
      ArgumentMediator argumentMediator,
      WorkerValidator workerValidator,
      CoordinatesValidator coordinatesValidator,
      PersonValidator personValidator,
      LocationValidator locationValidator,
      RegisterValidator registerValidator) {
    Map<String, ArgumentValidator> argumentValidatorMap =
        new HashMap<String, ArgumentValidator>() {
          {
            put(argumentMediator.workerId, workerValidator::checkId);
            put(argumentMediator.workerKey, workerValidator::checkKey);
            put(argumentMediator.workerName, workerValidator::checkName);
            put(argumentMediator.workerSalary, workerValidator::checkSalary);
            put(argumentMediator.workerStartDate, workerValidator::checkStartDate);
            put(argumentMediator.workerEndDate, workerValidator::checkEndDate);
            put(argumentMediator.workerStatus, workerValidator::checkStatus);
            put(argumentMediator.coordinatesX, coordinatesValidator::checkX);
            put(argumentMediator.coordinatesY, coordinatesValidator::checkY);
            put(argumentMediator.personPassportId, personValidator::checkPassportId);
            put(argumentMediator.personEyeColor, personValidator::checkEyeColor);
            put(argumentMediator.personHairColor, personValidator::checkHairColor);
            put(argumentMediator.locationX, locationValidator::checkX);
            put(argumentMediator.locationY, locationValidator::checkY);
            put(argumentMediator.locationZ, locationValidator::checkZ);
            put(argumentMediator.locationName, locationValidator::checkName);
            put(argumentMediator.userName, registerValidator::checkName);
            put(argumentMediator.userLogin, registerValidator::checkLogin);
            put(argumentMediator.userPassword, registerValidator::checkPassword);
          }
        };

    logger.debug(() -> "Provided argument validator map.");
    return argumentValidatorMap;
  }

  @Provides
  @Singleton
  List<ResponseHandler> provideResponseHandlers(
      MessageMediator messageMediator, StringFormatter stringFormatter) {
    List<ResponseHandler> responseHandlers =
        new ArrayList<ResponseHandler>() {
          {
            add(new OkResponseHandler());
            add(new CreatedResponseHandler(stringFormatter));
            add(new NoContentResponseHandler(stringFormatter));
            add(new NotModifiedResponseHandler(stringFormatter));
            add(new BadRequestResponseHandler(stringFormatter, messageMediator));
            add(new UnauthorizedResponseHandler(stringFormatter, messageMediator));
            add(new NotFoundResponseHandler(stringFormatter));
            add(new ForbiddenResponseHandler(stringFormatter));
            add(new ConflictResponseHandler(stringFormatter, messageMediator));
            add(new InternalServerErrorResponseHandler(stringFormatter, messageMediator));
          }
        };

    logger.debug("Provided response handlers list.");
    return responseHandlers;
  }

  @Provides
  @Singleton
  Configuration provideConfiguration() throws ProvidingException {
    logger.debug("Providing configuration for file: {}.", () -> CLIENT_CONFIG_PATH);

    Parameters parameters = new Parameters();

    FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
        new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
            .configure(parameters.properties().setFileName(CLIENT_CONFIG_PATH));

    Configuration configuration;

    try {
      configuration = builder.getConfiguration();
    } catch (ConfigurationException e) {
      throw new ProvidingException(e);
    }

    logger.debug(() -> "Provided Configuration: FileBasedConfiguration.");
    return configuration;
  }

  @Provides
  @Singleton
  ExitManager provideExitManager() {
    List<ExitListener> entities = new ArrayList<>();

    ExitManager exitManager = new ExitManager(entities);
    logger.debug(() -> "Provided ExitManager.");
    return exitManager;
  }
}
