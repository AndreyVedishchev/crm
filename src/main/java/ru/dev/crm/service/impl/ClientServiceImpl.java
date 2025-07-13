package ru.dev.crm.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;
import ru.dev.crm.controllers.dto.ClientDto;
import ru.dev.crm.exceptions.ClientValidationException;
import ru.dev.crm.mapper.ClientMapper;
import ru.dev.crm.models.Client;
import ru.dev.crm.repository.ClientRepository;
import ru.dev.crm.repository.OrderRepository;
import ru.dev.crm.service.ClientService;
import ru.dev.crm.specification.ClientSpecification;
import ru.dev.crm.util.ClientValidator;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientValidator validator;

    public ClientServiceImpl(OrderRepository orderRepository, ClientRepository clientRepository, ClientMapper clientMapper, ClientValidator validator) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.validator = validator;
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        Client client = clientMapper.toClient(clientDto);

        DataBinder binder = new DataBinder(client);
        binder.setValidator(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            List<String> messages = binder.getBindingResult().getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new ClientValidationException(messages);
        }
        Client save = clientRepository.save(client);
        return clientMapper.toClientDto(save);
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        Client client = clientMapper.toClient(clientDto);

        DataBinder binder = new DataBinder(client);
        binder.setValidator(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            List<String> messages = binder.getBindingResult().getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            throw new ClientValidationException(messages);
        }
        Client clientById = clientRepository.findById(client.getId())
                .orElseThrow(() -> new EntityNotFoundException("Не найден клиент с id " + client.getId()));
        clientById.setName(client.getName());
        clientById.setSurname(client.getSurname());
        clientById.setEmail(client.getEmail());
        clientById.setPhone(client.getPhone());
        clientById.setOrders(client.getOrders());
        Client save = clientRepository.save(clientById);
        return clientMapper.toClientDto(save);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Не найден клиент с id " + id));
        orderRepository.clearClientRef(id);
        clientRepository.delete(client);
    }

    @Override
    public ClientDto get(Integer id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Не найден клиент с id " + id));
        return clientMapper.toClientDto(client);
    }

    @Override
    public Page<ClientDto> search(String name, String surname, String email, String phone, int page, int size, boolean asc) {
        Specification<Client> spec = ClientSpecification.hasName(name)
                                            .and(ClientSpecification.hasSurname(surname))
                                            .and(ClientSpecification.hasEmail(email))
                                            .and(ClientSpecification.hasPhone(phone));
        PageRequest req;
        if (asc) {
            Sort sort = Sort.by("name").ascending();
            req = PageRequest.of(page, size, sort);
        } else {
            req = PageRequest.of(page, size);
        }
        Page<Client> allClients = clientRepository.findAll(spec, req);
        return allClients.map(clientMapper::toClientDto);
    }
}
