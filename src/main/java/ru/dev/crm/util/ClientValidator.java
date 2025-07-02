package ru.dev.crm.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.dev.crm.models.Client;
import ru.dev.crm.repository.ClientRepository;
import ru.dev.crm.specification.ClientSpecification;

import java.util.Optional;

@Component
public class ClientValidator implements Validator {

    private final ClientRepository clientRepository;

    public ClientValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Client client = (Client) o;

        Specification<Client> spec = ClientSpecification.hasEmail(client.getEmail());
        Optional<Client> one = clientRepository.findOne(spec);
        Client clientByEmail = one.orElse(null);

        if (clientByEmail != null && !clientByEmail.getId().equals(client.getId())) {
            errors.rejectValue("email", "", "Клиент с таким email уже существует.");
        }
    }
}
