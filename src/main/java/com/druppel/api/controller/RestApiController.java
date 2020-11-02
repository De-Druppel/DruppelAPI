package com.druppel.api.controller;

import com.druppel.api.dal.MeasurementSummary;
import com.druppel.api.service.RestDataTransfer;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Request example: http://localhost:8080/druppel-api/past-readings/?api-key=DRUPPEL_KEY&timeframe=day&esp-id=123456
 */
@RestController
@Validated
@RequestMapping("druppel-api")
public class RestApiController {

    private final RestDataTransfer data = new RestDataTransfer();
    private final String KEY = "DRUPPEL_KEY";

    /**
     * Returns the requested measurements
     *
     * @param apiKey    String API key for request validation
     * @param timeframe String measurements you want to see of a specific timeframe (day|week|month)
     * @param espId     String ESP ID
     * @return ResponseEntity with the given message
     */
    @GetMapping(path = "/past-readings/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> getMeasurements(
            // Query parameter validations
            @RequestParam(name = "api-key") @NotBlank(message = "API key cannot be blank") @Size(min = 10, max = 15, message = "Invalid API key") String apiKey,
            @RequestParam(name = "timeframe") @Pattern(regexp = "^(day|week|month)$", message = "Timeframe does not exist") String timeframe,
            @RequestParam(name = "esp-id") @NotBlank(message = "ESP ID key cannot be blank") @Size(min = 5, max = 15, message = "Invalid ESP ID") String espId
    ) throws Exception {
        // Check if api key is valid
        if (!this.isValidApiKey(apiKey)) {
            throw new Exception("Unauthorized");
        }
      //  String jsonString = this.data.get(espId, timeframe);
     //   return new ResponseEntity<>(jsonString, HttpStatus.OK);
        return null;
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
    /**
     * Returns the requested average measurements
     *
     * @param date1 Date from you want to get the measurements(day|week|month)
     * @param date2 Date until you want to get the measurements(day|week|month)
     * @param espId Id of microcontroller
     * @return ResponseBody with the given message
     */
    @GetMapping(path="/past-readings2/")
    public @ResponseBody List<MeasurementSummary> getAverageMeasurementList(@RequestParam Date date1,@RequestParam Date date2,@RequestParam int espId){

        return data.getAverageSummary(date1, date2, espId);
    }

}