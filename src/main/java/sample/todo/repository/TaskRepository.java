package sample.todo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import sample.todo.domain.Task;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  TaskRepository(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Task> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM tasks order by id", new BeanPropertyRowMapper<Task>(Task.class));
  }

  public Optional<Task> findOne(Integer id) {
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);

    try {
      return Optional.ofNullable(
          jdbcTemplate.queryForObject(
              "SELECT * FROM tasks WHERE id = :id",
              param,
              new BeanPropertyRowMapper<>(Task.class)));
    } catch (EmptyResultDataAccessException e) {
      return Optional.ofNullable(null);
    }
  }

  public Task save(Task task) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(task);

    if (task.getId() == null) {
      SimpleJdbcInsert insert =
          new SimpleJdbcInsert((JdbcTemplate) jdbcTemplate.getJdbcOperations())
              .withTableName("tasks")
              .usingGeneratedKeyColumns("id");
      Number key = insert.executeAndReturnKey(param);
      task.setId(key.intValue());
    } else {
      jdbcTemplate.update(
          "UPDATE tasks SET subject = :subject, deadLine = :deadLine, hasDone = :hasDone WHERE id = :id",
          param);
    }
    return task;
  }

  public void deleteOne(Integer id) {
    SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
    jdbcTemplate.update("DELETE FROM tasks WHERE id = :id", param);
  }
}
