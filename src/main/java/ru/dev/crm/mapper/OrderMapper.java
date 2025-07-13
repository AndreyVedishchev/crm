package ru.dev.crm.mapper;

import org.mapstruct.*;
import ru.dev.crm.controllers.dto.OrderDto;
import ru.dev.crm.controllers.dto.ProductDto;
import ru.dev.crm.models.Client;
import ru.dev.crm.models.Order;
import ru.dev.crm.models.OrderProduct;
import ru.dev.crm.models.Product;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

//    Говорит MapStruct взять поле id из вложенного объекта client (в Order), и установить его в поле clientId у OrderDto.

//    target = "products" — это поле назначения (куда будет делаться маппинг), то есть поле products внутри класса OrderDto.
//    ignore = true — означает «игнорировать это поле, не маппить его автоматически». вы заполните его вручную позже (например, в @AfterMapping).
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(target = "products", ignore = true)
    OrderDto toOrderDto(Order order);

//    Говорит MapStruct взять поле clientId из OrderDto, и создать по нему объект типа Client через специальный метод и установить как поле client у Order.
    @Mapping(target = "client", source = "clientId", qualifiedByName = "clientFromId")
    Order toOrder(OrderDto orderDto);

    @Named("clientFromId")
    default Client clientFromId(Integer id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }

//    MapStruct, преобразуя Order в OrderDto, игнорирует поле products. После основного маппинга MapStruct автоматически вызывает метод,
//    помеченный @AfterMapping, чтобы заполнить те поля, которые требуют особой или сложной логики.

//    Аннотация @MappingTarget используется в MapStruct для указания того, в какой объект нужно «вливать» результат маппинга.
    @AfterMapping
    default void fillProducts(Order order, @MappingTarget OrderDto dto) {
        List<ProductDto> products = new ArrayList<>();
        if (order.getOrderProducts() != null) {
            for (OrderProduct op : order.getOrderProducts()) {
                Product product = op.getProduct();
                if (product == null) continue;
                ProductDto productDto = new ProductDto();
                productDto.setId(product.getId());
                productDto.setName(product.getName());
                productDto.setDescription(product.getDescription());
                productDto.setPrice(product.getPrice());
                productDto.setQuantity(op.getQuantity());
                products.add(productDto);
            }
        }
        dto.setProducts(products);
    }
}
