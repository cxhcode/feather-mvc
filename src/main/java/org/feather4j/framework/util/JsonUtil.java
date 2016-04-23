package org.feather4j.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by Jackie on 2015/11/26.
 * Email : chenxinhua@ishehui.com
 */
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("convert object to string error", e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(Class<T> tClass, String json) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("convert json to object error", e);
            throw new RuntimeException(e);
        }
    }

}
