package com.smart_park.exceptions;

import com.smart_park.exceptions.parking.ParkingLotNotExistingException;
import com.smart_park.exceptions.vehicle.VehicleAlreadyExistsException;
import com.smart_park.exceptions.vehicle.VehicleAlreadyParked;
import com.smart_park.exceptions.vehicle.VehicleNotExistingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //  vehicle already exist exception
    @ExceptionHandler(VehicleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleVehicleAlreadyExists(VehicleAlreadyExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // vehicle already parked exception
    @ExceptionHandler(VehicleAlreadyParked.class)
    public ResponseEntity<ErrorResponse> handleVehicleAlreadyParked(VehicleAlreadyParked ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
    }

    // vehicle not existing exception
    @ExceptionHandler(VehicleNotExistingException.class)
    public ResponseEntity<ErrorResponse> VehicleNotExistingException(VehicleNotExistingException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
    }

    // parking lot not existing exception
    @ExceptionHandler(ParkingLotNotExistingException.class)
    public ResponseEntity<ErrorResponse> ParkingLotNotExistingException(ParkingLotNotExistingException ex) {
        return buildResponse(HttpStatus.UNPROCESSABLE_CONTENT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnreadableBody(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid request body — check 'type' field and check the value. [see error]" + ex);
    }

    // shared helper to avoid repeating ResponseEntity.status(...).body(...) everywhere
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        ErrorResponse error = new ErrorResponse(status.value(), message);
        return ResponseEntity.status(status).body(error);
    }
}
