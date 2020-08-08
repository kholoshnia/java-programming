package ru.lab.client.controller.responseHandler.responseHandlers;

import ru.lab.client.controller.responseHandler.ResponseHandler;
import ru.lab.client.controller.responseHandler.formatter.Formatter;
import ru.lab.common.transfer.response.Status;

public final class NoContentResponseHandler extends ResponseHandler {
  private final Formatter stringFormatter;

  public NoContentResponseHandler(Formatter stringFormatter) {
    this.stringFormatter = stringFormatter;
  }

  @Override
  protected String process() {
    if (!status.equals(Status.NO_CONTENT)) {
      return null;
    }

    return stringFormatter.makeCyan(answer);
  }
}
