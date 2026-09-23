package com.bcopstein.ex1biblioeca;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Profile("jdbc")
@Repository
public class AutorRepositoryJDBC implements AutorRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Autor> autorRowMapper = (rs, rowNum) ->
        new Autor(rs.getLong("id"), rs.getString("nome"),
                  rs.getObject("data_nascimento", LocalDate.class));

    public AutorRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <S extends Autor> S save(S autor) {
        int atualizados = jdbcTemplate.update(
            "UPDATE autores SET nome = ?, data_nascimento = ? WHERE id = ?",
            autor.getNome(), autor.getDataNascimento(), autor.getId());

        if (atualizados == 0) {
            jdbcTemplate.update(
                "INSERT INTO autores (id, nome, data_nascimento) VALUES (?, ?, ?)",
                autor.getId(), autor.getNome(), autor.getDataNascimento());
        }
        return autor;
    }

    public List<Autor> findAll() {
        return jdbcTemplate.query(
            "SELECT id, nome, data_nascimento FROM autores", autorRowMapper);
    }

    public Optional<Autor> findByNome(String nome) {
        return jdbcTemplate.query(
            "SELECT id, nome, data_nascimento FROM autores WHERE nome = ?",
            autorRowMapper, nome).stream().findFirst();
    }
}
