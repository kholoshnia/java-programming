package ru.storage.server.controller.services.hash;

import com.google.inject.Inject;
import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.storage.server.controller.services.hash.exceptions.HashGeneratorException;

import javax.annotation.Nonnull;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SHA1Generator extends HashGenerator {
  private static final Logger logger = LogManager.getLogger(SHA1Generator.class);

  @Inject
  public SHA1Generator(@Nonnull Configuration configuration) {
    super(configuration);
  }

  @Override
  protected String hash(@Nonnull String string) throws HashGeneratorException {
    String sha1;

    try {
      MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
      msdDigest.update(string.getBytes(StandardCharsets.UTF_8), 0, string.length());
      sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
    } catch (NoSuchAlgorithmException e) {
      logger.fatal(() -> "An exception was caught during the work of SHA-1 hash generator.", e);
      throw new HashGeneratorException(e);
    }

    logger.info(() -> "SHA-1 hash was generated.");
    return sha1;
  }
}
