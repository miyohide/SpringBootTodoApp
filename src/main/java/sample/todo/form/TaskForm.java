package sample.todo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class TaskForm {

  private String subject;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate deadLine;

  private Boolean hasDone;
  private Boolean isNewTask;

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

  public Boolean getIsNewTask() {
    return isNewTask;
  }

  public void setIsNewTask(Boolean newTask) {
    isNewTask = newTask;
  }
}
