package se.b3.healthtech.blackbird.blbcomposite.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.b3.healthtech.blackbird.blbcomposite.api.request.CreatePublicationRequest;
import se.b3.healthtech.blackbird.blbcomposite.service.PublicationService;

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
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {@Content})   })
    @PostMapping(value= "/")
    @ResponseStatus(value = HttpStatus.OK)
    public void createPublication(@RequestBody CreatePublicationRequest request){
        log.info("in controller class");
        publicationService.createPublication(request);
    }
}
