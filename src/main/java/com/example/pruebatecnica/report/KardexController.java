package com.example.pruebatecnica.report;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class KardexController {

    private final KardexService kardexService;

    public KardexController(KardexService kardexService) {
        this.kardexService = kardexService;
    }

    /**
     * GET /api/reports/kardex
     *
     * Returns a kardex report combining product data with their stock movement history.
     *
     * Optional filters:
     *  - ?productId=1   → report for a single product
     *  - ?active=true   → only active/inactive products
     *
     * Example response item:
     * {
     *   "productId": 1, "code": "PROD-001", "name": "Teclado", "price": 25.50,
     *   "currentStock": 48, "active": true,
     *   "movements": [
     *     { "movementId": 2, "type": "OUT", "quantity": 2, "createdAt": "...", "stockEffect": -2 },
     *     { "movementId": 1, "type": "IN",  "quantity": 50, "createdAt": "...", "stockEffect": 50 }
     *   ]
     * }
     */
    @GetMapping("/kardex")
    public List<KardexItem> kardex(
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Boolean active) {
        return kardexService.buildKardex(productId, active);
    }
}

