package ru.dev.crm.service;

import org.springframework.data.domain.Page;
import ru.dev.crm.controllers.dto.ClientDto;

public interface ClientService {
    ClientDto create(ClientDto clientDto);
    ClientDto update(ClientDto clientDto);
    void delete(Integer id);
    ClientDto get(Integer id);
    Page<ClientDto> search(String name, String surname, String email, String phone, int page, int size, boolean asc);
}
