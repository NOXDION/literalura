package com.alurachallenges.literalura.util;

import com.alurachallenges.literalura.model.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(JsonUtil.class.getName());

    public static ApiResponse parseApiResponse(String json) {
        try {
            return mapper.readValue(json, ApiResponse.class);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error parsing JSON", e);
            return null;
        }
    }
}
