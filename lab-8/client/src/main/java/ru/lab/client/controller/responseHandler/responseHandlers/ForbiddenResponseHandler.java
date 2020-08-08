package ru.lab.client.controller.responseHandler.responseHandlers;

import ru.lab.client.controller.responseHandler.ResponseHandler;
import ru.lab.client.controller.responseHandler.formatter.Formatter;
import ru.lab.common.transfer.response.Status;

public final class ForbiddenResponseHandler extends ResponseHandler {
  private final Formatter stringFormatter;

  public ForbiddenResponseHandler(Formatter stringFormatter) {
    this.stringFormatter = stringFormatter;
  }

  @Override
  protected String process() {
    if (!status.equals(Status.FORBIDDEN)) {
      return null;
    }

    return String.format("%s:", stringFormatter.makeMagenta(status.toString()))
        + System.lineSeparator()
        + answer;
  }
}
