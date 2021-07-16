package edu.gorb.musicstudio.command;

public class CommandResult {

    public enum RoutingType {
        FORWARD, REDIRECT
    }

    private RoutingType routingType;
    private String page;

    public CommandResult(String page, RoutingType routingType) {
        this.routingType = routingType;
        this.page = page;
    }

    public boolean isRedirect() {
        return routingType == RoutingType.REDIRECT;
    }

    public boolean isForward() {
        return routingType == RoutingType.FORWARD;
    }

    public RoutingType getRoutingType() {
        return routingType;
    }

    public String getPage() {
        return page;
    }
}
