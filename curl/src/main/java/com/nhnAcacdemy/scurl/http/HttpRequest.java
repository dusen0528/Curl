package com.nhnAcacdemy.scurl.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpMethod method;
    private String url;
    private Map<String, String> headers = new HashMap<>();
    private String body;

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setHeaders(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // Contetn-Type 기본 설정 포함(Option Parser)
    public String buildRequest() {
        StringBuilder request = new StringBuilder();

        request.append(method).append(" ").append(url).append(" HTTP/1.1\r\n");

        // POST나 PUT일 때 기본 Content-Type 설정
        if ((method == HttpMethod.POST || method == HttpMethod.PUT) && !headers.containsKey("Content-Type")) {
            headers.put("Content-Type", "application/x-www-form-urlencoded");
        }
        for (Map.Entry<String, String> header : headers.entrySet()) {
            request.append(header.getKey()).append(": ").append(header.getValue()).append("\r\n");
        }

        if (body != null || !body.isEmpty()) {
            request.append("\r\n").append(body);
        }

        return request.toString();
    }

}