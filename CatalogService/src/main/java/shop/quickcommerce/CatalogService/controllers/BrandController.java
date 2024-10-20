package shop.quickcommerce.CatalogService.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.quickcommerce.CatalogService.services.BrandService;
import shop.quickcommerce.Shared.dtos.BrandDto;

import java.util.List;

@RestController
@RequestMapping("/catalogs/brand")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<BrandDto>> getBrands() {
        List<BrandDto> brands = brandService.getBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable("brandId") Long brandId) {
        BrandDto brand = brandService.getBrandById(brandId);
        return ResponseEntity.ok(brand);
    }
}
