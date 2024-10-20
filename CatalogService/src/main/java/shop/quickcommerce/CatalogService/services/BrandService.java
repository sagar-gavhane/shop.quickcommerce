package shop.quickcommerce.CatalogService.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import shop.quickcommerce.CatalogService.entities.Brand;
import shop.quickcommerce.CatalogService.repositories.BrandRepository;
import shop.quickcommerce.Shared.dtos.BrandDto;

import java.util.List;

@Service
public class BrandService {
    private final BrandRepository brandRepository;
    private final ModelMapper modelMapper;

    public BrandService(BrandRepository brandRepository, ModelMapper modelMapper) {
        this.brandRepository = brandRepository;
        this.modelMapper = modelMapper;
    }

    public List<BrandDto> getBrands() {
        return brandRepository
                .findAll()
                .stream()
                .map(brand -> modelMapper.map(brand, BrandDto.class))
                .toList();
    }

    public BrandDto getBrandById(Long brandId) {
        Brand brand = brandRepository
                .findById(brandId)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found with id: " + brandId));

        return modelMapper.map(brand, BrandDto.class);
    }
}
