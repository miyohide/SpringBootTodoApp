package sample.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sample.todo.domain.Task;
import sample.todo.form.TaskForm;
import sample.todo.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskWebController {

  private static final String VIEW_NAME_OF_TASKS = "tasks";
  private static final String REDIRECT_TO = "redirect:/" + VIEW_NAME_OF_TASKS;

  private final TaskService taskService;

  @Autowired
  TaskWebController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping(value = "/tasks")
  public ModelAndView readAllTasks() {
    TaskForm form = createInitialForm();
    ModelAndView modelAndView = toTasksPage();
    modelAndView.addObject("form", form);
    List<Task> tasks = taskService.findAllTasks();
    modelAndView.addObject("tasks", tasks);
    return modelAndView;
  }

  @PostMapping(value = "/tasks")
  public ModelAndView createOneTask(@ModelAttribute TaskForm form) {
    createTaskFromForm(form);
    return new ModelAndView(REDIRECT_TO);
  }

  @GetMapping(value = "/tasks/{id}")
  public ModelAndView readOneTask(@PathVariable Integer id) {
    Optional<TaskForm> form = readTaskFromId(id);
    if (form.isEmpty()) {
      return new ModelAndView(REDIRECT_TO);
    }
    ModelAndView modelAndView = toTasksPage();
    modelAndView.addObject("taskId", id);
    modelAndView.addObject("form", form.get());
    List<Task> tasks = taskService.findAllTasks();
    modelAndView.addObject("TASKS", tasks);
    return modelAndView;
  }

  @PutMapping(value = "/tasks/{id}")
  public ModelAndView updateOneTask(@PathVariable Integer id, @ModelAttribute TaskForm form) {
    updateTask(id, form);
    return new ModelAndView(REDIRECT_TO);
  }

  @DeleteMapping(value = "/tasks/{id}")
  public ModelAndView deleteOneTask(@PathVariable Integer id) {
    deleteTask(id);
    return new ModelAndView(REDIRECT_TO);
  }

  private void deleteTask(Integer id) {
    Optional<Task> task = taskService.findOneTask(id);
    if (task.isPresent()) {
      taskService.deleteOneTask(id);
    }
  }

  private ModelAndView toTasksPage() {
    return new ModelAndView(VIEW_NAME_OF_TASKS);
  }

  private TaskForm createInitialForm() {
    String formSubject = "";
    LocalDate formDeadLine = LocalDate.now();
    return new TaskForm(formSubject, formDeadLine, false, true);
  }

  private void createTaskFromForm(TaskForm form) {
    String subject = form.getSubject();
    LocalDate deadLine = form.getDeadLine();
    Boolean hasDone = form.getHasDone();
    Task task = new Task(null, subject, deadLine, hasDone);
    taskService.createTask(task);
  }

  private void updateTask(Integer id, TaskForm form) {
    String subject = form.getSubject();
    LocalDate deadLine = form.getDeadLine();
    Boolean hasDone = form.getHasDone();
    Task task = new Task(id, subject, deadLine, hasDone);
    taskService.updateTask(task);
  }

  private Optional<TaskForm> readTaskFromId(Integer id) {
    Optional<Task> task = taskService.findOneTask(id);
    if (task.isEmpty()) {
      return Optional.ofNullable(null);
    }
    String formSubject = task.get().getSubject();
    LocalDate formDeadLine = task.get().getDeadLine();
    Boolean hasDone = task.get().getHasDone();
    TaskForm form = new TaskForm(formSubject, formDeadLine, hasDone, false);
    return Optional.ofNullable(form);
  }
}
