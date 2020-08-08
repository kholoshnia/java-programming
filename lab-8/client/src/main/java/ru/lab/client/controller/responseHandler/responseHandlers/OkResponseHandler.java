package ru.lab.client.controller.responseHandler.responseHandlers;

import ru.lab.client.controller.responseHandler.ResponseHandler;
import ru.lab.common.transfer.response.Status;

public final class OkResponseHandler extends ResponseHandler {
  @Override
  protected String process() {
    if (!status.equals(Status.OK)) {
      return null;
    }

    return answer;
  }
}
