package ua.pt.deti.b;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingRESTController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greetingREST")
    public GreetingREST greetingREST(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new GreetingREST(counter.incrementAndGet(), String.format(template, name));
    }
}
