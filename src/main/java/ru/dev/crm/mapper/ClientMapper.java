package ru.dev.crm.mapper;

import org.mapstruct.Mapper;
import ru.dev.crm.controllers.dto.ClientDto;
import ru.dev.crm.models.Client;

//componentModel = "spring" — MapStruct создаст Spring Bean, который будет можно внедрять
//uses = {OrderMapper.class} — если внутри ClientDto или Client есть поля, связанные с
// заказами (Orders), то для их преобразования будет использоваться указанный маппер OrderMapper.
@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface ClientMapper {
    Client toClient(ClientDto clientDto);
    ClientDto toClientDto(Client client);
}
