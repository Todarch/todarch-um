package com.todarch.um.infrastructure.persistence.converter;

import com.todarch.um.domain.shared.UserId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * UserId converter.
 *
 * @deprecated keeping it here just in case later usage
 */
@Converter(autoApply = true)
@Deprecated
public class UserIdConverter implements AttributeConverter<UserId, String> {
  @Override
  public String convertToDatabaseColumn(UserId attribute) {
    return attribute.value();
  }

  @Override
  public UserId convertToEntityAttribute(String dbData) {
    return UserId.from(dbData);
  }
}
