package ru.dev.crm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.dev.crm.controllers.dto.OrderDto;
import ru.dev.crm.models.Client;
import ru.dev.crm.models.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "client.id", target = "clientId")
    OrderDto toOrderDto(Order order);

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
}
