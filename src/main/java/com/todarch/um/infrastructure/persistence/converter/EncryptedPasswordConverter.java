package com.todarch.um.infrastructure.persistence.converter;

import com.todarch.um.domain.kernel.EncryptedPassword;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EncryptedPasswordConverter implements
    AttributeConverter<EncryptedPassword, String> {
  @Override
  public String convertToDatabaseColumn(EncryptedPassword attribute) {
    if (attribute != null) {
      return attribute.value();
    }
    return null;
  }

  @Override
  public EncryptedPassword convertToEntityAttribute(String dbData) {
    if (dbData != null) {
      return EncryptedPassword.from(dbData);
    }
    return null;
  }
}
