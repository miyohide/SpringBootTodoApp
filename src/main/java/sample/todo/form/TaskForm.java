package sample.todo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class TaskForm {

  String subject;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  LocalDate deadLine;

  Boolean hasDone;
  Boolean isNewTask;

  public TaskForm(String subject, LocalDate deadLine, boolean hasDone, boolean isNewTask) {
    this.subject = subject;
    this.deadLine = deadLine;
    this.hasDone = hasDone;
    this.isNewTask = isNewTask;
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

  public Boolean getNewTask() {
    return isNewTask;
  }

  public void setNewTask(Boolean newTask) {
    isNewTask = newTask;
  }
}
