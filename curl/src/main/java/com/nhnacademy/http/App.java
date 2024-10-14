package com.nhnacademy.http;

import com.nhnacademy.http.SimpleHttpServer;

public class App {
    public static void main(String[] args) {
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer();
        simpleHttpServer.start();
    }
}
