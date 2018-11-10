package com.todarch.um.domain;

import com.todarch.um.domain.shared.Email;
import com.todarch.um.domain.shared.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(Email email);

  Optional<User> findByActivationCode(String activationCode);

  /**
   * For manual id generation.
   *
   * @return userId object
   * @deprecated keeping it here just in case later usage
   */
  @Deprecated
  default UserId nextId() {
    return UserId.from(UUID.randomUUID().toString());
  }


}
