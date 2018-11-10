package com.todarch.um.domain;

import com.todarch.um.domain.kernel.EncryptedPassword;
import com.todarch.um.domain.shared.Email;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "USERS")
public class User {
  // The Convert annotation should not be used to specify conversion of the following: Id attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Email email;

  @Column(nullable = false)
  private EncryptedPassword password;

  @Column(name = "activation_code", nullable = true)
  private String activationCode;

  protected User() {
    // for hibernate
  }

  /**
   * Initializes a user entity with all required information.
   */
  public User(@NonNull Email email,
              @NonNull EncryptedPassword password) {
    this.email = email;
    this.password = password;
    this.activationCode = UUID.randomUUID().toString();
  }

  public Long id() {
    return id == null ? -1 : id;
  }

  public Email email() {
    return email;
  }

  public EncryptedPassword password() {
    return password;
  }

  public String activationCode() {
    return activationCode;
  }

  public void activate() {
    activationCode = null;
  }

  public boolean isActivated() {
    return activationCode == null;
  }
}

