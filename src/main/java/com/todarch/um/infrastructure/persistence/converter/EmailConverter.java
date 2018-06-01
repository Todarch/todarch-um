package com.todarch.um.infrastructure.persistence.converter;

import com.todarch.um.domain.shared.Email;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EmailConverter implements AttributeConverter<Email, String> {
  @Override
  public String convertToDatabaseColumn(Email attribute) {
    if (attribute != null) {
      return attribute.value();
    }
    return null;
  }

  @Override
  public Email convertToEntityAttribute(String dbData) {
    if (dbData != null) {
      return Email.from(dbData);
    }
    return null;
  }
}
