package com.bcopstein.ex1biblioeca;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Profile("jdbc")
@Repository 
public class LivroRepositoryJDBC implements LivroRepository{
    private static final String SELECT_LIVROS =
        "SELECT l.id, l.titulo, l.ano, l.autor_id, " +
        "       a.nome AS autor_nome, a.data_nascimento " +
        "FROM livros l INNER JOIN autores a ON l.autor_id = a.id";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Livro> livroRowMapper = new RowMapper<>() {
        @Override
        public Livro mapRow(ResultSet rs, int rowNum) throws SQLException {
            Autor autor = new Autor(rs.getLong("autor_id"),rs.getString("autor_nome"),rs.getObject("data_nascimento",LocalDate.class));
            return new Livro(rs.getLong("id"), rs.getString("titulo"),autor, rs.getInt("ano"));
        }
    };

    @Autowired
    public LivroRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <S extends Livro> S save(S livro) {
        int atualizados = jdbcTemplate.update(
            "UPDATE livros SET titulo = ?, autor_id = ?, ano = ? WHERE id = ?",
            livro.getTitulo(), livro.getAutor().getId(), livro.getAno(), livro.getId());

        if (atualizados == 0) {
            jdbcTemplate.update(
                "INSERT INTO livros (id, titulo, autor_id, ano) VALUES (?, ?, ?, ?)",
                livro.getId(), livro.getTitulo(), livro.getAutor().getId(), livro.getAno());
        }
        return livro;
    }

    public List<Livro> findAll() {
        return jdbcTemplate.query(SELECT_LIVROS, livroRowMapper);
    }

    public List<Livro> findByAutorNome(String autor) {
        return jdbcTemplate.query(SELECT_LIVROS + " WHERE a.nome = ?", livroRowMapper, autor);
    }

    public Optional<Livro> findById(Long id) {
        return jdbcTemplate.query(SELECT_LIVROS + " WHERE l.id = ?", livroRowMapper, id)
                .stream().findFirst();
    }

    public Livro findByTitulo(String titulo) {
        return jdbcTemplate.queryForObject(SELECT_LIVROS + " WHERE l.titulo = ?", livroRowMapper, titulo);
    }

    public boolean existsById(Long id) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM livros WHERE id = ?", 
            Integer.class,id
        );
        return count != null && count > 0;
    }

    public void deleteById(Long id) { 
        jdbcTemplate.update(
        "DELETE FROM livros WHERE id = ?",
        id);
    }

    public int count(){
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM livros",
            Integer.class);
        return count != null ? count : 0;
    }

}