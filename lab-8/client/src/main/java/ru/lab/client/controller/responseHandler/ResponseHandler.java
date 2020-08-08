package ru.lab.client.controller.responseHandler;

import ru.lab.common.transfer.response.Response;
import ru.lab.common.transfer.response.Status;

public abstract class ResponseHandler {
  protected Status status;
  protected String answer;

  /**
   * Handles {@link Response} from server and returns information string.
   *
   * @param response response from server
   * @return string containing response information
   */
  public final String handle(Response response) {
    status = response.getStatus();
    answer = response.getAnswer();

    return process();
  }

  protected abstract String process();
}
