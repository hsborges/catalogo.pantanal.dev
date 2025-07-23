package dev.pantanal.catalogo;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        final Map<String, Object> response = Map.of(
                "status", "UP",
                "service", "catalogo-cinema",
                "timestamp", LocalDateTime.now(),
                "message", "Cinema Catalogo API is running");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    public ResponseEntity<Void> index() {
        return ResponseEntity.status(302)
                .location(URI.create("/health"))
                .build();
    }
}
