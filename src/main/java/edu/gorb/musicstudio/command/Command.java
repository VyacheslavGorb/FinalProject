package edu.gorb.musicstudio.command;

import java.net.http.HttpRequest;

public interface Command {
    String execute(HttpRequest request);
}
