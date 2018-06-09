package com.todarch.um.helper;

import com.todarch.security.api.JwtUtil;
import com.todarch.um.domain.User;
import com.todarch.um.domain.UserRepository;
import com.todarch.um.domain.kernel.EncryptedPassword;
import com.todarch.um.domain.shared.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;

@Component
public class DbHelper {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  /**
   * Creates default test user using TestUser data.
   *
   * @return created test user object in detached state.
   */
  public User createTestUser() {
    EncryptedPassword encryptedPassword = TestUser.RAW_PASSWORD.encryptWith(passwordEncoder);
    User user = new User(TestUser.EMAIL, encryptedPassword);
    userRepository.saveAndFlush(user);
    return userRepository.findByEmail(TestUser.EMAIL).orElseThrow(onNotFound());
  }

  /**
   * Creates jwt for given user.
   * Used to access secured endpoints.
   *
   * @param user user information to use for jwt
   * @return jwt
   */
  public Jwt createJwt(User user) {
    Set<SimpleGrantedAuthority> authorities =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    org.springframework.security.core.userdetails.User principal =
        new org.springframework.security.core.userdetails.User(
            user.email().value(), "", authorities);

    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(principal, "", authorities);
    String token = jwtUtil.createToken(authentication, false, user.id());
    return Jwt.from(token);
  }

  private Supplier<AssertionError> onNotFound() {
    return () -> new AssertionError("User should have been created");
  }

  public void clearAll() {
    userRepository.deleteAll();
  }
}

