package com.m0p4rk.pet911.controller;

import com.m0p4rk.pet911.model.QueryRequest;
import com.m0p4rk.pet911.model.QueryResponse;
import com.m0p4rk.pet911.service.QueryService;
import com.m0p4rk.pet911.enums.QueryCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/query")
public class QueryController {

    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);

    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<QueryResponse> processQuery(@RequestBody QueryRequest request) {
        logger.info("Received query: {}", request.getQuestionText());
        try {
            QueryResponse response = queryService.processQuery(request);
            logger.info("Processed query. Response category: {}", response.getCategory());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing query", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/categorize")
    public ResponseEntity<QueryCategory> categorizeQuery(@RequestParam String question) {
        logger.info("Categorizing query: {}", question);
        try {
            QueryCategory category = queryService.categorizeQuery(question);
            logger.info("Query categorized as: {}", category);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            logger.error("Error categorizing query", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // 에러 처리를 위한 예외 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Unhandled exception in QueryController", e);
        return ResponseEntity.internalServerError().body("An unexpected error occurred");
    }
}