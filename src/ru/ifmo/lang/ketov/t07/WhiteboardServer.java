package ru.ifmo.lang.ketov.t07;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class WhiteboardServer {
    public static void main(String[] args) throws IOException {

        try {
            final HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/get", new WhiteboardHandler());
            server.createContext("/post", new WhiteboardHandler());
            server.start();

        } catch (MalformedURLException e) {
            System.out.println("Error: " + e);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    private static String message = "";

    private static class WhiteboardHandler implements HttpHandler {

        public void handle(HttpExchange exchange) throws IOException {

//            проблемы с кодировкой
            if (exchange.getHttpContext().getPath().endsWith("/get")) {
                exchange.sendResponseHeaders(200, URLEncoder.encode(message, "UTF-8").length());
                final OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(URLEncoder.encode(message, "UTF-8").getBytes());
                responseBody.close();
            } else {
                final String query = exchange.getRequestURI().getQuery();
                message = URLDecoder.decode(query.substring(query.indexOf("=") + 1, query.length()), "Unicode");
                final String thisMessage = "Smth happens";
                exchange.sendResponseHeaders(200, thisMessage.length());
                final OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(thisMessage.getBytes());
                responseBody.close();
            }
        }
    }
}