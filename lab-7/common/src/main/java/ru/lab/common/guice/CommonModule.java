package ru.lab.common.guice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.lab.common.ArgumentMediator;
import ru.lab.common.CommandMediator;
import ru.lab.common.serizliser.Serializer;
import ru.lab.common.serizliser.serializers.JsonSerializer;

public class CommonModule extends AbstractModule {
  private static final Logger logger = LogManager.getLogger(CommonModule.class);

  @Override
  protected void configure() {
    bind(JsonSerializer.class).in(Scopes.SINGLETON);
    bind(Serializer.class).to(JsonSerializer.class);
    logger.debug(() -> "Configured Serializer: JsonSerializer");

    bind(CommandMediator.class).in(Scopes.SINGLETON);
    bind(ArgumentMediator.class).in(Scopes.SINGLETON);
    logger.debug(() -> "Mediators were configured.");
  }

  @Provides
  @Singleton
  Gson provideGson() {
    Gson gson = new GsonBuilder().serializeNulls().create();

    logger.debug(() -> "Provided Gson.");
    return gson;
  }
}
