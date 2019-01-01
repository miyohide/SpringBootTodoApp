package sample.todo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;
import sample.todo.domain.Task;
import sample.todo.form.TaskForm;
import sample.todo.service.TaskService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TaskControllerTest {
    @Mock
    private TaskService taskService;
    @InjectMocks
    private TaskWebController taskWebController;

    @Test
    public void readAllTasks() {
        Task t1 = new Task(1, "s1", LocalDate.of(2018, 11, 1), false);
        Task t2 = new Task(2, "s2", LocalDate.of(2018, 10, 2), false);

        List<Task> list = Arrays.asList(t1, t2);
        when(taskService.findAllTasks()).thenReturn(list);
        ModelAndView mav = taskWebController.readAllTasks();
        assertThat(mav.getViewName(), is("tasks"));
        Map<String, Object> modelMap = mav.getModel();
        TaskForm formObject = (TaskForm) modelMap.get("form");
        assertThat(formObject.getDeadLine(), is(LocalDate.now()));
        assertThat(formObject.getHasDone(), is(false));
        assertThat(formObject.getNewTask(), is(true));
    }

    @Test
    public void createOneTask() {
        TaskForm tf = new TaskForm("form object", LocalDate.of(2017, 1, 2), false, true);
        ModelAndView mav = taskWebController.createOneTask(tf);
        verify(taskService).createTask(any());
        assertThat(mav.getViewName(), is("redirect:/tasks"));
    }

    @Test
    public void readOneTaskExistTask() {
        Task t1 = new Task(1, "s1", LocalDate.of(2018, 11, 1), false);
        when(taskService.findOneTask(t1.getId())).thenReturn(Optional.ofNullable(t1));
        ModelAndView mav = taskWebController.readOneTask(t1.getId());
        assertThat(mav.getViewName(), is("tasks"));
        Map<String, Object> modelMap = mav.getModel();
        assertThat(modelMap.get("taskId"), is(t1.getId()));
        TaskForm tf = (TaskForm)modelMap.get("form");
        assertThat(tf.getSubject(), is(t1.getSubject()));
        assertThat(tf.getDeadLine(), is(t1.getDeadLine()));
        assertThat(tf.getHasDone(), is(t1.getHasDone()));
        assertThat(tf.getNewTask(), is(false));
    }

    @Test
    public void readOneTaskNotExistTask() {
        int method_call_id = 299;
        when(taskService.findOneTask(method_call_id)).thenReturn(Optional.ofNullable(null));
        ModelAndView mav = taskWebController.readOneTask(method_call_id);
        assertThat(mav.getViewName(), is("redirect:/tasks"));
    }

    @Test
    public void updateOneTask() {
        TaskForm tf = new TaskForm("form subject", LocalDate.of(2017, 1, 2), false, true);
        Task t = new Task(1, tf.getSubject(), tf.getDeadLine(), tf.getHasDone());
        ModelAndView mav = taskWebController.updateOneTask(t.getId(), tf);
        verify(taskService).updateTask(t);
        assertThat(mav.getViewName(), is("redirect:/tasks"));
    }

    @Test
    public void deleteOneTaskExistTask() {
        Task t1 = new Task(1, "s1", LocalDate.of(2018, 11, 1), false);
        when(taskService.findOneTask(t1.getId())).thenReturn(Optional.ofNullable(t1));
        ModelAndView mav = taskWebController.deleteOneTask(t1.getId());
        assertThat(mav.getViewName(), is("redirect:/tasks"));
    }

    @Test
    public void deleteOneTaskNotExistTask() {
        int method_call_id = 312;
        when(taskService.findOneTask(method_call_id)).thenReturn(Optional.ofNullable(null));
        ModelAndView mav = taskWebController.deleteOneTask(method_call_id);
        verify(taskService, Mockito.times(0)).deleteOneTask(method_call_id);
        assertThat(mav.getViewName(), is("redirect:/tasks"));
    }
}
