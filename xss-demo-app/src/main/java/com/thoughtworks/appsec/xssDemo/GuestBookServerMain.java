package com.thoughtworks.appsec.xssDemo;

public class GuestBookServerMain {


    public static void main(String[] args) {
        GuestBookServer server = new GuestBookServer(8080);
        server.start();
    }
}
