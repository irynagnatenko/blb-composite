package se.b3.healthtech.blackbird.blbcomposite.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.b3.healthtech.blackbird.blbcomposite.domain.Container;
import se.b3.healthtech.blackbird.blbcomposite.service.ContainerService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api-birdspecies/container")
public class ContainerController {

    private final ContainerService containerService;

    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @Operation(summary = "Create a new container list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a container list", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})   })
    @PostMapping(value= "/all/",
            params = {"key"})
    @ResponseStatus(value = HttpStatus.OK)
    public void createContainers(@RequestParam("key") String partitionKey, @RequestBody List<Container> containers) throws CloneNotSupportedException {
        log.info("in CompositionController - createContainers");
        containerService.createContainers(containers, partitionKey);
    }

    @Operation(summary = "Get the list of latest containers for a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found containers list",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcomposite.domain.Container.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/all/",
            params = "key",
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<Container> getLatestContainers(@RequestParam("key") String key) {
        log.info("in ContainerController - getLatestContainers");
        return containerService.getLatestContainers(key);
    }

    @Operation(summary = "Add a new container")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added a container", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})   })
    @PostMapping(value= "/",
            params = "key",
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addContainer(@RequestBody Container request, @RequestParam String key) throws CloneNotSupportedException {
        log.info("in ContainerController - createContainer");
        containerService.addContainer(key, request);
    }

    // For addContent
    @Operation(summary = "Get the latest container for a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found container",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcomposite.domain.Container.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/",
            params = {"key", "id"},
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public Container getLatestContainer(@RequestParam("key") String key, @RequestParam("id") String containerId) {
        log.info("in ContainerController - getLatestContainer");
        return containerService.getLatestContainer(key, containerId);
    }

    @Operation(summary = "Delete a list of containers with a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted containers", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})   })
    @PostMapping(value= "/delete/",
            headers = "userName",
            params = "key",
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteContainers(@RequestHeader("userName") String userName,
                                @RequestParam("key") String publicationId,
                                @RequestBody List<Container> containersList)  {
        log.info("in ContainerController - deleteContainer");
        containerService.deleteContainers(userName, publicationId, containersList);
    }

}
