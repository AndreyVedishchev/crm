package ru.dev.crm.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dev.crm.models.Product;
import ru.dev.crm.repository.ProductRepository;
import ru.dev.crm.specification.ProductSpecification;

import java.util.Optional;

@Component
public class ProductValidator implements Validator {

    private final ProductRepository productRepository;

    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Product productFromReq = (Product) o;

        Specification<Product> spec = ProductSpecification.hasName(productFromReq.getName());
        Optional<Product> optional = productRepository.findOne(spec);
        Product productByName = optional.orElse(null);

        if (productByName != null && !productByName.getId().equals(productFromReq.getId())) {
            System.out.println("validator");
            errors.rejectValue("name", "", "Продукт с таким названием уже существует.");
        }
    }
}
