package com.nhnacademy.scurl;

import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 8888; // 서버 포트

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("서버가 시작되었습니다. 포트: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // 클라이언트 연결 수신
                System.out.println("클라이언트가 연결되었습니다: " + clientSocket.getInetAddress());
                handleClient(clientSocket); // 클라이언트 처리
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String requestLine = in.readLine(); // 클라이언트 요청 읽기
            System.out.println("요청: " + requestLine);

            // 요청 메서드 분석
            String responseBody;
            if (requestLine.startsWith("POST")) {
                responseBody = "<html><body><h1>POST 요청을 받았습니다!</h1></body></html>";
            } else if (requestLine.startsWith("GET")) {
                responseBody = "<html><body><h1>GET 요청을 받았습니다!</h1></body></html>";
            } else {
                responseBody = "<html><body><h1>지원하지 않는 요청입니다!</h1></body></html>";
            }

            // 간단한 HTTP 응답 생성
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("Custom-Header: CustomValue"); // 예시로 커스텀 헤더 추가
            out.println();
            out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close(); // 클라이언트 소켓 닫기
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
