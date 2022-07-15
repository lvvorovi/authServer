package trackerAuth.client.service;

import trackerAuth.client.dto.ClientDtoResponse;
import trackerAuth.client.dto.ClientDtoCreateRequest;
import trackerAuth.client.dto.ClientDtoUpdateRequest;

public interface ClientService {

    ClientDtoResponse findById(String clientId);

    ClientDtoResponse save(ClientDtoCreateRequest dto);

    ClientDtoResponse update(ClientDtoUpdateRequest dto);

    void deleteById(String id);

    ClientDtoResponse findByClientId(String name);
}
