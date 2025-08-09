package dev.pantanal.catalogo;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/")
@Tag(name = "Health", description = "Verificação de saúde e status do serviço")
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Status de saúde da API", description = "Retorna informações básicas de disponibilidade e metadados do serviço.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Serviço disponível", content = @Content(mediaType = "application/json", schema = @Schema(implementation = java.util.Map.class)))
    })
    public ResponseEntity<Map<String, Object>> healthCheck() {
        final Map<String, Object> response = Map.of(
                "status", "UP",
                "service", "catalogo-cinema",
                "timestamp", LocalDateTime.now(),
                "message", "Cinema Catalogo API is running");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/")
    @Operation(summary = "Página inicial", description = "Redireciona para o endpoint de saúde (/health).")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Redirecionado para /health", content = @Content)
    })
    public ResponseEntity<Void> index() {
        return ResponseEntity.status(302)
                .location(URI.create("/health"))
                .build();
    }
}
