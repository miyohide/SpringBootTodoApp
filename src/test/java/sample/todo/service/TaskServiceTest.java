package sample.todo.service;

import org.junit.Test;;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import sample.todo.domain.Task;
import sample.todo.repository.TaskRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskService taskService;

    @Test
    public void findAllTasksNoTask() {
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());
        assertThat(taskService.findAllTasks().isEmpty(), is(true));
    }

    @Test
    public void findAllTasksSortByDeadLine() {
        Task t1 = new Task(1, "subject1", LocalDate.of(2018, 11, 1), false);
        Task t2 = new Task(1, "subject1", LocalDate.of(2018, 10, 2), false);

        List<Task> list = Arrays.asList(t1, t2);
        when(taskRepository.findAll()).thenReturn(list);
        assertThat(taskService.findAllTasks(), is(contains(t2, t1)));
    }

    @Test
    public void createTask() {
        Task t = new Task(null, "subject1", LocalDate.of(2018, 1, 3), false);
        taskService.createTask(t);
        verify(taskRepository).save(t);
    }

    @Test
    public void findOneTask() {
        int id = 1;
        taskService.findOneTask(id);
        verify(taskRepository).findOne(id);
    }

    @Test
    public void updateTask() {
        Task t = new Task(10, "subject1", LocalDate.of(2018, 1, 3), false);
        taskService.updateTask(t);
        verify(taskRepository).save(t);
    }

    @Test
    public void deleteTask() {
        int id = 10;
        taskService.deleteOneTask(id);
        verify(taskRepository).deleteOne(id);
    }
}
