package com.todarch.um;

public final class Endpoints {
  private Endpoints() {
    throw new AssertionError("Cannot create objects from util class");
  }

  public static final String NON_SECURED = "/non-secured";
  public static final String REGISTRATION = NON_SECURED + "/register";
  public static final String AUTHENTICATION = NON_SECURED + "/authenticate";
  public static final String ACTIVATION = NON_SECURED + "/activate-account";

  private static final String API = "/api";
  public static final String ACCOUNT = API + "/account";
  public static final String AUTHENTICATE = API + "/authenticate";
  public static final String LOGOUT = API + "/logout";

}
