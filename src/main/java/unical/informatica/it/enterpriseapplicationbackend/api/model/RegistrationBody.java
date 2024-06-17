package unical.informatica.it.enterpriseapplicationbackend.api.model;

import jakarta.validation.constraints.*;

/**
 * The information required to register a user.
 */
public class RegistrationBody {

  /** The username. */
  @NotNull
  @NotBlank
  @Size(min=3, max=255)
  private String username;
  /** The email. */
  @NotNull
  @NotBlank
  @Email
  private String email;
  /** The password. */
  @NotNull
  @NotBlank
  @Pattern(
          regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,32}$",
          message = "La password deve contenere almeno una lettera maiuscola, una lettera minuscola, un numero, un carattere speciale, e deve essere lunga tra 8 e 32 caratteri."
  )@Size(min=8, max=32)
  private String password;
  /** The first name. */
  @NotNull
  @NotBlank
  private String firstName;
  /** The last name. */
  @NotNull
  @NotBlank
  private String lastName;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
