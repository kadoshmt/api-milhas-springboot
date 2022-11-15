package br.com.janesroberto.milhas.dto;

import java.util.Set;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignUpDto {
  
  @NotBlank
  @Size(max = 50)
  @Email(message = "deve ser um endereço de email válido")
  private String email;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
 

//  public String getEmail() {
//    return email;
//  }
//
//  public void setEmail(String email) {
//    this.email = email;
//  }
//
//  public String getPassword() {
//    return password;
//  }
//
//  public void setPassword(String password) {
//    this.password = password;
//  }
//
//  public Set<String> getRole() {
//    return this.role;
//  }
//
//  public void setRole(Set<String> role) {
//    this.role = role;
//  }
}
