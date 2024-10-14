package com.nhnacademy.scurl;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.nhnacademy.scurl.http.HttpMethod;
import com.nhnacademy.scurl.http.HttpRequest;
import com.nhnacademy.scurl.http.HttpResponse;
import com.nhnacademy.scurl.util.OptionParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ScurlMain {

    public static void main(String[] args) throws Exception {
        OptionParser optionParser = new OptionParser();

        optionParser.parse(args);

        // Http 요청 설정
        HttpRequest request = new HttpRequest();
        request.setMethod(HttpMethod.valueOf(optionParser.getRequestMethod()));
        request.setUrl("http://localhost:8080"); // 테스트용 URL

        String[] headers = optionParser.getHeaders();
        if (headers != null) {
            for (String header : headers) {
                String[] headerKeyValue = header.split(": ");
                request.setHeaders(headerKeyValue[0], headerKeyValue[1]);

            }
        }

        // POST, PUT일 경우 전송할 데이터 설정
        if (request.getMethod() == HttpMethod.POST || request.getMethod() == HttpMethod.PUT) {
            String data = optionParser.getData();
            if (data != null) {
                request.setBody(data);
            } else {
                request.setBody(""); // 기본값 설정
            }
        }

        // 요청 전송
        URI uri = new URI(request.getURL());
        URL url = uri.toURL();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(request.getMethod().name());

        // 헤더 추가
        for (String key : request.getHeaders().keySet()) {
            connection.setRequestProperty(key, request.getHeaders().get(key));
        }

        // PUT, POST 요청 바디 설정
        if (request.getMethod() == HttpMethod.POST || request.getMethod() == HttpMethod.PUT) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = request.getBody().getBytes("utf-8");
                os.write(input, 0, input.length);
            }
        }

        int responseCode = connection.getResponseCode();
        System.out.println("ResponseCode : " + responseCode);

        StringBuilder response = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }

        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatusCode(responseCode);
        httpResponse.setBody(response.toString());

        System.out.println("ReseponseBody : " + httpResponse.getBody());

    }
}
