package sample.todo.domain;

import java.time.LocalDate;

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
}
