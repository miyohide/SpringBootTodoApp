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
  @Autowired TaskRepository taskRepository;

  @Transactional(readOnly = false)
  public List<Task> findAllTasks() {
    List<Task> allTasks = taskRepository.findAll();
    allTasks.sort(Comparator.comparing(Task::getDeadLine));
    return allTasks;
  }

  @Transactional(readOnly = false)
  public Task createTask(Task task) {
    return taskRepository.save(task);
  }

  @Transactional(readOnly = true)
  public Optional<Task> findOneTask(Integer id) {
    return taskRepository.findOne(id);
  }

  @Transactional(readOnly = false)
  public Task updateTask(Task task) {
    return taskRepository.save(task);
  }

  @Transactional(readOnly = false)
  public void deleteOneTask(Integer id) {
    taskRepository.deleteOne(id);
  }
}
