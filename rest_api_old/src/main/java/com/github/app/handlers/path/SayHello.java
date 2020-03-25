package com.github.app.handlers.path;

import java.util.List;
import java.util.Map;

public class SayHello extends ApplicationContext {

    @Override
    public String GET(Map<String, List<String>> params) {
        String noNameText = "Anonymous";
        String name = params.getOrDefault("name", List.of(noNameText)).stream().findFirst().orElse(noNameText);

        return "Hello " + name;
    }

    @Override
    public String POST(Map<String, List<String>> params) {
        String noNameText = "Anonymous";
        String name = params.getOrDefault("name", List.of(noNameText)).stream().findFirst().orElse(noNameText);

        return "BYE " + name;
    }
}