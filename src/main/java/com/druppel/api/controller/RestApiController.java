package com.druppel.api.controller;

import com.druppel.api.dal.MeasurementSummary;
import com.druppel.api.dal.VMeasurementRepo;
import com.druppel.api.response.MeasurementResponse;
import com.druppel.api.service.RestDataTransfer;
import com.druppel.api.service.VMeasurementService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Request example: http://localhost:8080/druppel-api/past-readings/?api-key=DRUPPEL_KEY&days=days&esp-id=123456&type=temprature
 */
@RestController
@Validated
@RequestMapping("druppel-api")
public class RestApiController {
    @Autowired
    private RestDataTransfer data;
    @Autowired
    private VMeasurementService vMeasurementService;

    private final String KEY = "DRUPPEL_KEY";

    /**
     * Returns the requested measurements
     *
     * @param apiKey    String API key for request validation
     * @param days      int measurements you want to see of a specific amount of days
     * @param espId     int ESP ID
     * @param type      String type you want to query (optional)
     * @return ResponseEntity with the given message
     */
    @GetMapping(path = "/past-readings/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MeasurementResponse getMeasurements(
            // Query parameter validations
            @RequestParam(name = "api-key") @NotBlank(message = "API key cannot be empty") @Size(min = 10, max = 15, message = "Invalid API key") String apiKey,
            @RequestParam(name = "days") @NotBlank(message = "Timeframe cannot be empty") @Size(min = 1, max = 3, message = "Invalid time frame") int days,
            @RequestParam(name = "esp-id") @NotBlank(message = "ESP ID key cannot be empty") @Size(min = 5, max = 15, message = "Invalid ESP ID") int espId,
            @RequestParam(name = "type") String type
    ) throws Exception {
        // Check if api key is valid
        if (!this.isValidApiKey(apiKey)) {
            throw new Exception("Unauthorized");
        }

        int code =200;
        String message ="ok";
        MeasurementResponse response=new MeasurementResponse(code,message);
        List<MeasurementSummary> infoList = vMeasurementService.getAverageSummary(days, espId, type);

        return response;
    }

    /**
     * Temporary method to check if api key is valid (Key always 'DRUPPEL_KEY' at the moment)
     *
     * @param apiKey String api key
     * @return boolean if api key is valid or not
     */
    private boolean isValidApiKey(String apiKey) {
        return apiKey.equals(this.KEY);
    }

    /**
     * Exception handler for all the incoming exceptions of the REST validation
     *
     * @param ex rest exception
     * @return ResponseEntity with the given message
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<Object> handleException(Exception ex) {

        JSONObject result = new JSONObject();
        try {
            result.put("code", 500);
            result.put("message", ex.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make content type Json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

        return new ResponseEntity<>(result.toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}