package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping
public class InfoController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/port")
    public Integer getPort() {
        return port;
    }

    @GetMapping("/getIntValue")
    public Integer getIntValue() {
        long start = System.currentTimeMillis();
        Integer stream = Stream.iterate(1, a -> a + 1).parallel().limit(1_000_000).reduce(0, (a, b) -> a + b);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return stream;
    }

    @GetMapping("/getIntValue2")
    public Integer getIntValue2() {
        long start = System.currentTimeMillis();
        Integer stream = Stream.iterate(1, a -> a + 1).limit(1_000_000).reduce(0, (a, b) -> a + b);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        return stream;
    }
}
