package com.example.pruebatecnica.report;

import com.example.pruebatecnica.report.dto.KardexResponse;

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

    // consultar el kardex de todos los productos o de un producto espefico
    @GetMapping("/kardex")
    public List<KardexResponse> kardex(@RequestParam(required = false) Long productId) {
        return kardexService.findAll(productId);
    }
}
