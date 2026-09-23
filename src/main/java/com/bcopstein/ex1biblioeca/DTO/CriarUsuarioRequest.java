package com.bcopstein.ex1biblioeca.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarUsuarioRequest(
    @NotBlank @Size(max = 100) String nome,
    @NotBlank @Email String email
){}

