package com.nhnacademy.scurl.util;

import org.apache.commons.cli.*;

public class OptionParser {

    private CommandLine cmd;

    public void parse(String[] args) throws ParseException {
        Options options = new Options();

        options.addOption("X", "request", true, "요청 메서드 지정 (GET, POST 등)\"");
        options.addOption("d", "data", true, "POST || PUT 요청과 함께 전송할 데이터");
        options.addOption("H", "header", true, "서버에 전달할 커스텀 헤더");
        options.addOption("i", "include", true, "응답 헤더를 출력에 포함할지 여부");
        // Content-Type 기본 설정 하지 않았을 때 타입
        options.addOption("v", "verbose", false, "작업을 더 자세히 출력");

        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);
    }

    public String getRequestMethod() {
        return cmd.getOptionValue("X", "GET");
    }

    public String getData() {
        return cmd.getOptionValue("d");
    }

    public String[] getHeaders() {
        return cmd.getOptionValues("H");
    }

    public boolean isIncludeHeaders() {
        return cmd.hasOption("i");
    }

    public boolean isVerbose() {
        return cmd.hasOption("v");
    }

}
