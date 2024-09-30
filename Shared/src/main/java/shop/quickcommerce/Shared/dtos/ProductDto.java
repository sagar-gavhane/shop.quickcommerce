package shop.quickcommerce.Shared.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String name;
    private String description;
    private Float marketPrice;
    private Float salePrice;
    private String imageUrl;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long categoryId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CategoryDto category;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long brandId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BrandDto brand;
}
