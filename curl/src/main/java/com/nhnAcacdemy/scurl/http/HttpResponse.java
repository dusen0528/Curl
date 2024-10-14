package com.nhnAcacdemy.scurl.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private int statusCode;
    private Map<String, String> headers = new HashMap<>();
    private String body;

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public boolean isContentTypeAllowed() {
        String contentType = headers.get("Content-Type");
        return contentType != null && (contentType.startsWith("text/") || contentType.equals("application/json"));
    }

    // 응답을 출력하는 메서드 (Content-Type 필터링 포함)
    public String printResponse() {
        if (isContentTypeAllowed()) {
            return body;
        } else {
            return "Unsupported Content-Type for output.";
        }
    }

}
