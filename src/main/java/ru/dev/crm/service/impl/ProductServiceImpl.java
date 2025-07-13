package ru.dev.crm.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;
import ru.dev.crm.controllers.dto.ProductDto;
import ru.dev.crm.exceptions.ProductValidationException;
import ru.dev.crm.mapper.ProductMapper;
import ru.dev.crm.models.Product;
import ru.dev.crm.repository.ProductRepository;
import ru.dev.crm.service.ProductService;
import ru.dev.crm.specification.ProductSpecification;
import ru.dev.crm.util.ProductValidator;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidator validator;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, ProductValidator validator) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.validator = validator;
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);

        DataBinder binder = new DataBinder(product);
        binder.setValidator(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            List<String> messages = binder.getBindingResult().getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new ProductValidationException(messages);
        }
        Product save = productRepository.save(product);
        return productMapper.toProductDto(save);
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);

        DataBinder binder = new DataBinder(product);
        binder.setValidator(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            List<String> messages = binder.getBindingResult().getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new ProductValidationException(messages);
        }

        Product pr = productRepository.findById(product.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Не найден товар с id " + product.getId()));
        pr.setName(product.getName());
        pr.setDescription(product.getDescription());
        pr.setPrice(product.getPrice());
        Product save = productRepository.save(pr);
        return productMapper.toProductDto(save);
    }

    @Override
    public void delete(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Не найден товар с id " + id));
        productRepository.delete(product);
    }

    @Override
    public ProductDto get(Integer id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Не найден товар с id " + id));
        return productMapper.toProductDto(product);
    }

    @Override
    public Page<ProductDto> search(String name, Double price, int page, int size, boolean srt) {
        Specification<Product> spec = ProductSpecification.hasName(name).and(ProductSpecification.hasPrice(price));
        PageRequest req;
        if (srt) {
            Sort sort = Sort.by("name").ascending().and(Sort.by("price").ascending());
            req = PageRequest.of(page, size, sort);
        } else {
            req = PageRequest.of(page, size);
        }
        Page<Product> allProducts = productRepository.findAll(spec, req);
        return allProducts.map(productMapper::toProductDto);
    }
}
