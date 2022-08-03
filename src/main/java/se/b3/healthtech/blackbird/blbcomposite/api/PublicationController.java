package se.b3.healthtech.blackbird.blbcomposite.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.b3.healthtech.blackbird.blbcomposite.domain.Publication;
import se.b3.healthtech.blackbird.blbcomposite.service.PublicationService;

import java.util.List;

@Slf4j
@Data
@RestController
@RequestMapping(value = "/api-birdspecies/publication")
public class PublicationController {

    private final PublicationService publicationService;

    @Operation(summary = "Create a new publication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created publication", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @PostMapping(value = "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void createPublication(@RequestBody Publication publication) throws CloneNotSupportedException {
        log.info("in CompositionController - createPublication");
        publicationService.createPublication(publication);
    }

    @Operation(summary = "Get latest publication for a specific partition key")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully found publication", content = {@Content}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @GetMapping(value = "/",
            params = "key",
            produces = {"application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public Publication getLatestPublication(@RequestParam("key") String key) {
        log.info("in PublicationController - getLatestPublication");
        return publicationService.getLatestPublication(key);
    }

    @Operation(summary = "Delete a publication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted publication",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "404", description = "Object not found", content = {@Content}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})})
    @PostMapping(path = "/delete/",
            headers = "userName",
            params = "key",
            produces = "application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePublication(@RequestHeader("userName") String userName,
                                  @RequestParam("key") String publicationId,
                                  @RequestBody List<Publication> publicationList) {
        log.info("in PublicationController - deletePublication");
        publicationService.deletePublication(userName, publicationId, publicationList);

    }
}
