package com.thoughtworks.appsec.xssDemo;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class GuestBookServer {

    private Server server;

    public GuestBookServer(final int port) {
        server = new Server(port);
        server.setStopAtShutdown(true);
        server.setHandler(getHandlers());
    }

    private HandlerList getHandlers() {
        HandlerList handlers = new HandlerList();
        ContextHandler pingHandler = new ContextHandler("/ping");
        pingHandler.setHandler(new AbstractHandler() {
            @Override
            public void handle(final String target, final Request baseRequest, final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
                baseRequest.setHandled(true);
                try (PrintWriter writer = response.getWriter()) {
                    writer.println("{\"message\"=\"pong\"}");
                }
            }
        });
        handlers.addHandler(pingHandler);
        return handlers;
    }

    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Server failed to start.");
        }
    }

}
