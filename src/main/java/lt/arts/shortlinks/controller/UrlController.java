package lt.arts.shortlinks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import lt.arts.shortlinks.dto.UrlDto;
import lt.arts.shortlinks.exception.InvalidUrlException;
import lt.arts.shortlinks.exception.UrlNotFoundException;
import lt.arts.shortlinks.model.Url;
import lt.arts.shortlinks.service.UrlService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static lt.arts.shortlinks.utils.ErrorCodes.*;

/**
 * Controller class for handling URL shortening requests.
 * This class provides endpoints for creating and retrieving shortened URLs.
 */

@RestController
@RequestMapping("/api/shortenUrl")
@Slf4j
public class UrlController {

    private final UrlService urlService;
    private final ModelMapper modelMapper;

    @Autowired
    public UrlController(UrlService urlService, ModelMapper modelMapper) {
        this.urlService = urlService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create a short url for provided original url")
    @PostMapping("/short")
    ResponseEntity<UrlDto> createShortenedUrl(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Url to create", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Url.class),
                    examples = @ExampleObject(value = "{ \"originalUrl\": \"https://example.com\" }")))
                                           @Valid @RequestBody UrlDto originalUrlDto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            for (FieldError error : allErrors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new InvalidUrlException(errorMsg.toString());
        }
        Url shortenedUrl = urlService.saveShortenedUrl(originalUrlDto.getOriginalUrl());

        log.info("Shortened Url: " + shortenedUrl);
        return new ResponseEntity<>(convertToDto(shortenedUrl), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a short url for provided original url")
    @GetMapping("/short")
    public ResponseEntity<UrlDto> getShortenedUrl(@Parameter(description = "Original url to be searched")
                                               @RequestParam String originalUrl) {

        Optional<Url> url = urlService.getShortenedUrl(originalUrl);
        if (url.isPresent()) {
            log.info("Get shortened Url " + url.get().getShortenedUrl());
            return new ResponseEntity<>(convertToDto(url.get()), HttpStatus.OK);
        }
        throw new UrlNotFoundException(URL_NOT_FOUND);
    }

    private Url convertToUrl(UrlDto urlDto){
        return modelMapper.map(urlDto, Url.class);
    }
    private UrlDto convertToDto(Url url){
        return modelMapper.map(url, UrlDto.class);
    }

}
