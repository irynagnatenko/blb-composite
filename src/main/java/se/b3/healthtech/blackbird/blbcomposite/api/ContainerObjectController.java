package se.b3.healthtech.blackbird.blbcomposite.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject;
import se.b3.healthtech.blackbird.blbcomposite.service.ContainerObjectService;

import java.util.List;

@Slf4j
@RequestMapping(value = "api-blackbird/containerobject")
@RestController
public class ContainerObjectController {

    private final ContainerObjectService containerObjectService;

    public ContainerObjectController(ContainerObjectService containerObjectService) {
        this.containerObjectService = containerObjectService;
    }

    @Operation(summary = "Create a list of containerObjects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a list of containerObjects", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})   })
    @PostMapping(value= "/all",
            params = {"key"})
    @ResponseStatus(value = HttpStatus.OK)
    public void createContainerObjects(@RequestParam("key") String partitionKey, @RequestBody List<ContainerObject> containerObjects) throws CloneNotSupportedException {
        log.info("in ContainerObjectController - createContainerObjects");
        containerObjectService.createContainerObjects(containerObjects, partitionKey);
    }

    @Operation(summary = "Get the list of latest containerObjects for a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Container Object List",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/all/",
            params = "key",
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ContainerObject> getLatestContainerObjects(@RequestParam("key") String key) {
        log.info("in ContainerObjectsController - getLatestControllerObjects List");
        return containerObjectService.getLatestContainerObjects(key);
    }

    @Operation(summary = "Create a new containerObject ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created a containerObject", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})   })
    @PostMapping(value= "/",
            params = {"key"},
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addContainerObject(@RequestParam("key") String publicationId, @RequestBody ContainerObject containerObject) throws CloneNotSupportedException {
        log.info("in ContainerObjectController - addContainerObject");
        containerObjectService.addContainerObject(publicationId, containerObject);
    }

    @Operation(summary = "Get the latest containerObject for a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Container Object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/",
            params = {"key","id"},
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public ContainerObject getLatestContainerObject(@RequestParam("key") String publicationId, @RequestParam("id") String containerObjectId) {
        log.info("in ContainerObjectsController - getLatestControllerObject - one object");
        return containerObjectService.getLatestContainerObject(publicationId, containerObjectId);
    }

    @Operation(summary = "Delete a list of containerObject")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted a containerObject", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})   })
    @PostMapping(value= "/",
            headers = "userName",
            params = "key",
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteContainerObjects(@RequestHeader("userName") String userName,
                                      @RequestParam("key") String publicationId,
                                      @RequestBody List<ContainerObject> containerObjectList) {
        log.info("in ContainerObjectController - deleteContainerObject");
        containerObjectService.deleteContainerObject(userName, publicationId, containerObjectList);
    }

    // for deleteContainer
    @Operation(summary = "Get Container Objects By Uuids for a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Container Objects",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/selected/",
            params = {"key", "uuids"},
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ContainerObject> getContainerObjectsByUuids(@RequestParam("key") String publicationId,
                                                            @RequestParam("uuids") List<String> uuids) { // not RequestBody???
        log.info("in ContainerObjectsController - getContainerObjectsByUuids");
        log.info("Lista " + uuids.size() + " " + uuids);
        return containerObjectService.getContainerObjectsByUuids(publicationId, uuids);
    }
}
