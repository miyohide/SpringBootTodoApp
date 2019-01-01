package sample.todo.domain;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

public class Task {
  Integer id;
  String subject;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate deadLine;

  Boolean hasDone;

  public Task() {}

  public Task(Integer id, String subject, LocalDate deadLine, Boolean hasDone) {
    this.id = id;
    this.subject = subject;
    this.deadLine = deadLine;
    this.hasDone = hasDone;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public LocalDate getDeadLine() {
    return deadLine;
  }

  public void setDeadLine(LocalDate deadLine) {
    this.deadLine = deadLine;
  }

  public Boolean getHasDone() {
    return hasDone;
  }

  public void setHasDone(Boolean hasDone) {
    this.hasDone = hasDone;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task = (Task) o;
    return Objects.equals(id, task.id) &&
            Objects.equals(subject, task.subject) &&
            Objects.equals(deadLine, task.deadLine) &&
            Objects.equals(hasDone, task.hasDone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, subject, deadLine, hasDone);
  }
}
