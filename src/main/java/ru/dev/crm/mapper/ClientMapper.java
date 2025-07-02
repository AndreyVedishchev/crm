package ru.dev.crm.mapper;

import org.mapstruct.Mapper;
import ru.dev.crm.controllers.dto.ClientDto;
import ru.dev.crm.models.Client;

@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface ClientMapper {
    Client toClient(ClientDto clientDto);
    ClientDto toClientDto(Client client);
}
