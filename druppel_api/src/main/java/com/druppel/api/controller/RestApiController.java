package com.druppel.api.controller;

import com.druppel.api.service.RestDataTransfer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Request example: http://localhost:8080/druppel-api/past-readings/?api-key=DRUPPEL_KEY&timeframe=day&esp-id=123456
 */
@RestController
@Validated
@RequestMapping("druppel-api")
public class RestApiController {

    private final RestDataTransfer data = new RestDataTransfer();

    /**
     * Returns the requested measurements
     *
     * @param apiKey String API key for request validation
     * @param timeframe String measurements you want to see of a specific timeframe (day|week|month)
     * @param espId String ESP ID
     * @return ResponseEntity with the given message
     */
    @GetMapping("/past-readings/")
    @ResponseBody
    public ResponseEntity<String> getMeasurements(
            // Query parameter validations
            @RequestParam(name = "api-key") @NotBlank (message = "API key cannot be blank") @Size(min = 10, max = 15, message = "Invalid API key") String apiKey,
            @RequestParam(name = "timeframe") @Pattern(regexp = "^(day|week|month)$", message = "Timeframe does not exist") String timeframe,
            @RequestParam(name = "esp-id") @NotBlank (message = "ESP ID key cannot be blank") @Size(min = 5, max = 15, message = "Invalid ESP ID") String espId
    ) {
        // Check if api key is valid
        if (this.isValidApiKey(apiKey)) {
            String result = this.data.get(espId, timeframe);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Temporary method to check if api key is valid (Key always 'DRUPPEL_KEY' at the moment)
     *
     * @param apiKey String api key
     * @return boolean if api key is valid or not
     */
    private boolean isValidApiKey(String apiKey) {
        return apiKey.equals("DRUPPEL_KEY");
    }

    /**
     * Exception handler for all the incoming exceptions of the REST validation
     *
     * @param ex rest exception
     * @return ResponseEntity with the given message
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}