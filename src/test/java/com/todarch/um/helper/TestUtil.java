package com.todarch.um.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class TestUtil {
  private TestUtil() {
    throw new AssertionError("Cannot create a object from utility class");
  }

  /**
   * Convert an object to JSON byte array.
   *
   * @param object
   *            the object to convert
   * @return the JSON byte array
   * @throws IOException in case of error
   */
  public static byte[] toJsonBytes(Object object)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //
    // JavaTimeModule module = new JavaTimeModule();
    // mapper.registerModule(module);

    return mapper.writeValueAsBytes(object);
  }
}
