package ru.lab.client.controller.responseHandler.responseHandlers;

import ru.lab.client.controller.responseHandler.ResponseHandler;
import ru.lab.client.controller.responseHandler.formatter.Formatter;
import ru.lab.common.transfer.response.Status;

public final class NotFoundResponseHandler extends ResponseHandler {
  private final Formatter stringFormatter;

  public NotFoundResponseHandler(Formatter stringFormatter) {
    this.stringFormatter = stringFormatter;
  }

  @Override
  protected String process() {
    if (!status.equals(Status.NOT_FOUND)) {
      return null;
    }

    return String.format("%s:", stringFormatter.makeCyan(status.toString()))
        + System.lineSeparator()
        + answer;
  }
}
