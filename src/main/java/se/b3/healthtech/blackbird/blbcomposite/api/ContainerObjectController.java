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

    private ContainerObjectService containerObjectService;

    public ContainerObjectController(ContainerObjectService containerObjectService) {
        this.containerObjectService = containerObjectService;
    }

    @Operation(summary = "Get the list of latest containerObjects for a specific partitionKey")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Container Object",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = se.b3.healthtech.blackbird.blbcomposite.domain.ContainerObject.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/latest/all/",
            params = "key",
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<ContainerObject> getLatestContainerObjects(@RequestParam("key") String key) {
        log.info("in ContainerObjectsController - getLatestControllerObjects");
        return containerObjectService.getLatestContainerObjects(key);
    }
}
