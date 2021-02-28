package com.uvas.common.json.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.uvas.common.exceptions.StandardException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonUtils {

  private static final ObjectMapper Mapper = new ObjectMapper();

  private JsonUtils() {}


  public static <T> T fromJSON(String json, Class<T> clazz) {
    if (json == null) {
      return null;
    }
    try {
      return Mapper.readValue(json, clazz);
    } catch (JsonProcessingException e) {
      log.error("Error converting {} from JsonString", json, e);
      throw new StandardException(e.getMessage());
    }
  }

  public static <T> String toJSON(T object) {
    if (object == null) {
      return null;
    }
    try {
      Mapper.registerModule(new JavaTimeModule());
      Mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      return Mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      log.error("Error converting {} to JSON", object, e);
      throw new StandardException(e.getMessage());
    }
  }

}
