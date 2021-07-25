package edu.gorb.musicstudio.conroller.command;

public class CommandResult {

    private RoutingType routingType;
    private String page;
    public CommandResult(String page, RoutingType routingType) {
        this.routingType = routingType;
        this.page = page;
    }

    public RoutingType getRoutingType() {
        return routingType;
    }

    public String getPage() {
        return page;
    }

    public enum RoutingType {
        FORWARD, REDIRECT
    }
}
