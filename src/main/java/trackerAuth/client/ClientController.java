package trackerAuth.client;

import trackerAuth.client.dto.ClientDtoCreateRequest;
import trackerAuth.client.dto.ClientDtoUpdateRequest;
import trackerAuth.client.service.ClientService;
import trackerAuth.client.dto.ClientDtoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDtoResponse> findById(@PathVariable String id) {
        ClientDtoResponse responseDto = service.findById(id);
        addSelfLink(responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<ClientDtoResponse> save(@Valid @RequestBody ClientDtoCreateRequest dto) {
        ClientDtoResponse responseDto = service.save(dto);
        addSelfLink(responseDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClientDtoResponse> update(@Valid @RequestBody ClientDtoUpdateRequest dto) {
        ClientDtoResponse responseDto = service.update(dto);
        addSelfLink(responseDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void addSelfLink(ClientDtoResponse dto) {
        dto.add(linkTo(methodOn(ClientController.class).findById(dto.getId())).withSelfRel());
    }

}
