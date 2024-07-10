

  /**
   * Creates a VerificationToken object for sending to the user.
   * @param user The user the token is being generated for.
   * @return The object created.
   */
  private VerificationToken createVerificationToken(LocalUser user) {
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(jwtService.generateVerificationJWT(user));
    verificationToken.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
    verificationToken.setUser(user);
    user.getVerificationTokens().add(verificationToken);
    return verificationToken;
  }

  /**
   * Logins in a user and provides an authentication token back.
   * @param loginBody The login request.
   * @return The authentication token. Null if the request was invalid.
   */
  public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFailureException {
    Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUsername());
    if (opUser.isPresent()) {
      LocalUser user = opUser.get();
      if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
        if (user.isEmailVerified()) {
          return jwtService.generateJWT(user);
        } else {
          List<VerificationToken> verificationTokens = user.getVerificationTokens();
          boolean resend = verificationTokens.size() == 0 ||
                  verificationTokens.get(0).getCreatedTimestamp().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
          if (resend) {
            VerificationToken verificationToken = createVerificationToken(user);
            verificationTokenDAO.save(verificationToken);
            emailService.sendVerificationEmail(verificationToken);
          }
          throw new UserNotVerifiedException(resend);
        }
      }
    }
    return null;
  }

  /**
   * Verifies a user from the given token.
   * @param token The token to use to verify a user.
   * @return True if it was verified, false if already verified or token invalid.
   */
  @Transactional
  public boolean verifyUser(String token) {
    Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
    if (opToken.isPresent()) {
      VerificationToken verificationToken = opToken.get();
      LocalUser user = verificationToken.getUser();
      if (!user.isEmailVerified()) {
        user.setEmailVerified(true);
        localUserDAO.save(user);
        verificationTokenDAO.deleteByUser(user);
        return true;
      }
    }
    return false;
  }

   /**
   * Sends the user a forgot password reset based on the email provided.
   * @param email The email to send to.
   * @throws EmailNotFoundException Thrown if there is no user with that email.
   * @throws EmailFailureException
   */
  public void forgotPassword(String email) throws EmailNotFoundException, EmailFailureException {
    Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(email);
    if (opUser.isPresent()) {
      LocalUser user = opUser.get();
      String token = jwtService.generatePasswordResetJWT(user);
      emailService.sendPasswordResetEmail(user, token);
    } else {
      throw new EmailNotFoundException();
    }
  }

  /**
   * Resets the users password using a given token and email.
   * @param body The password reset information.
   */
  public void resetPassword(PasswordResetBody body) {
    String email = jwtService.getResetPasswordEmail(body.getToken());
    Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(email);
    if (opUser.isPresent()) {
      LocalUser user = opUser.get();
      user.setPassword(encryptionService.encryptPassword(body.getPassword()));
      localUserDAO.save(user);
    }
  }

  /**
   * Method to check if an authenticated user has permission to a user ID.
   * @param user The authenticated user.
   * @param id The user ID.
   * @return True if they have permission, false otherwise.
   */
  public boolean userHasPermissionToUser(LocalUser user, Long id) {
    return user.getId() == id;
  }


  /**
   * Updates the profile of the authenticated user.
   *
   * @param currentUser    The currently authenticated user.
   * @param userUpdateBody The new user information.
   * @return
   */
  @Transactional
  public void updateUserProfile(LocalUser currentUser, UserUpdateBody userUpdateBody) {
    Address address = currentUser.getAddresses().get(0);
    address.setPhone(userUpdateBody.getPhone());
    address.setAddress(userUpdateBody.getAddress());
    address.setCity(userUpdateBody.getCity());
    address.setCountry(userUpdateBody.getCountry());
    address.setCAP(userUpdateBody.getCap());
    addressService.saveAddress(address);
  }

  public List<LocalUser> findAll(){
      return localUserDAO.findAll();
  }


  public LocalUser findByuser(String user) {
    return localUserDAO.findByUsernameIgnoreCase(user).get();
  }

  public LocalUser findById(Long id) {
    return localUserDAO.findById(id).get();
  }

  public LocalUser add(LocalUser user) {
    LocalUser usr = new LocalUser();
    usr.setEmail(user.getEmail());
    usr.setUsername(user.getUsername());
    usr.setFirstName(user.getFirstName());
    usr.setLastName(user.getLastName());
    usr.setPassword(encryptionService.encryptPassword(user.getPassword()));
    usr.setRole(false);
    usr.setEmailVerified(true);
    return localUserDAO.save(usr);
  }

}


