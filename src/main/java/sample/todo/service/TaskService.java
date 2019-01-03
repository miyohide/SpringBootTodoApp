package sample.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.todo.domain.Task;
import sample.todo.repository.TaskRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
  private TaskRepository taskRepository;

  @Autowired
  TaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  @Transactional
  public List<Task> findAllTasks() {
    List<Task> allTasks = taskRepository.findAll();
    allTasks.sort(Comparator.comparing(Task::getDeadLine));
    return allTasks;
  }

  @Transactional
  public Task createTask(Task task) {
    return taskRepository.save(task);
  }

  @Transactional(readOnly = true)
  public Optional<Task> findOneTask(Integer id) {
    return taskRepository.findOne(id);
  }

  @Transactional
  public Task updateTask(Task task) {
    return taskRepository.save(task);
  }

  @Transactional
  public void deleteOneTask(Integer id) {
    taskRepository.deleteOne(id);
  }
}
