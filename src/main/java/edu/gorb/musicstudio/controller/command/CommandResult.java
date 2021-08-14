package edu.gorb.musicstudio.controller.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Result of {@link Command#execute(HttpServletRequest)} containing routing type and page path
 */
public final class CommandResult {

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

    /**
     * Routing type: forward or redirect
     */
    public enum RoutingType {
        FORWARD, REDIRECT
    }
}
