package com.todarch.um.domain;

import com.todarch.um.domain.kernel.EncryptedPassword;
import com.todarch.um.domain.shared.Email;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
  // The Convert annotation should not be used to specify conversion of the following: Id attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  private Email email;

  @Column(nullable = false)
  private EncryptedPassword password;

  protected User() {
    // for hibernate
  }

  public User(@NonNull Email email,
              @NonNull EncryptedPassword password) {
    this.email = email;
    this.password = password;
  }

  public Long id() {
    return id == null ? -1 : id;
  }
}

