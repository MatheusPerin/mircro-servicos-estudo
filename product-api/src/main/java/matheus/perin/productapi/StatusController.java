package matheus.perin.productapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StatusController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> map = new HashMap<>();

        map.put("service", "product-api");
        map.put("status", "UP");
        map.put("httpStatus", HttpStatus.OK.value());

        return ResponseEntity.ok(map);
    }

}
