package com.bcopstein.ex1biblioeca.DTO;

import com.bcopstein.ex1biblioeca.Usuario;

public record UsuarioResponse(long id, String nome, String email){
    public static UsuarioResponse de(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNome(), u.getEmail());
    }
}