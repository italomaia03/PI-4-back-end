package br.edu.projeto_integrado.somar.dtos.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class SignUpRequest {
    @Email(message = "Coloque um email em um formato válido(ex: usuario@dominio.com")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[_*#@]).{8,32}$",
            message = "A senha deve conter de 8 a 20 caracteres (lowercase, uppercase, numbers, special(_,*,#,@))")
    private String password;
    @Pattern(regexp = "^[\\p{L}]+$", message = "O nome deve conter apenas caracteres alfabéticos.")
    private String firstName;
    @Pattern(regexp = "^[\\p{L}]+$", message = "O nome deve conter apenas caracteres alfabéticos.")
    private String lastName;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
