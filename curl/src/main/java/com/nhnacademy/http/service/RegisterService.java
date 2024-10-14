package com.nhnacademy.http.service;

import java.io.IOException;
import java.io.PrintWriter;

import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.response.HttpResponse;
import com.nhnacademy.http.util.CounterUtils;
import com.nhnacademy.http.util.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegisterService implements HttpService {

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        // Body-설정
        String responseBody = null;

        try {
            responseBody = ResponseUtils.tryGetBodyFromFile(httpRequest.getRequestURI());
            // CounterUtils.increaseAndGet()를 이용해서 context에 있는 counter 값을 증가시키고, 반환되는 값을
            // index.html에 반영 합니다.
            // ${count} <-- counter 값을 치환 합니다.
            responseBody = responseBody.replace("{%count}", String.valueOf(CounterUtils.increaseAndGet()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Header-설정
        String responseHeader = ResponseUtils.createResponseHeader(200, "UTF-8", responseBody.length());

        // PrintWriter 응답
        try (PrintWriter bufferedWriter = httpResponse.getWriter();) {
            bufferedWriter.write(responseHeader);
            bufferedWriter.write(responseBody);
            bufferedWriter.flush();
            log.debug("body:{}", responseBody.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        String requestBody = null;

        try {
            requestBody = ResponseUtils.tryGetBodyFromFile("/index.html");
            StringBuilder sending = new StringBuilder();
            sending.append("HTTP/1.1 301 Moved Permanently" + System.lineSeparator());
            sending.append(
                    "Location: http://localhost:8080/index.html?userId=" + httpRequest.getAttribute("userId")
                            + System.lineSeparator());
            sending.append("Content-Length: 0" + System.lineSeparator());
            sending.append("Connection: Closed" + System.lineSeparator());
            String send = sending.toString();

            // PrintWriter를 try-with-resources로 사용하여 자동으로 닫히도록 합니다.
            try (PrintWriter printWriter = httpResponse.getWriter()) {
                printWriter.write(send);
                printWriter.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
