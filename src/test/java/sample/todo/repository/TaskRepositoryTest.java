package sample.todo.repository;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.ninja_squad.dbsetup.operation.Operation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import sample.todo.domain.Task;

import javax.sql.DataSource;
import java.util.List;

import static com.ninja_squad.dbsetup.Operations.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@JdbcTest
@ComponentScan
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
})
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    DataSource dataSource;

    @Test
    public void findAllTest() {
        final Operation DELETE_ALL = deleteAllFrom("tasks");
        final Operation INSERT =
                insertInto("tasks")
                .row()
                .column("id", 2)
                .column("subject", "subject2")
                .column("deadLine", "2018-12-4")
                .column("hasDone", false)
                .end()
                .row()
                .column("id", 1)
                .column("subject", "subject1")
                .column("deadLine", "2018-12-3")
                .column("hasDone", false)
                .end().build();
        Operation operation = sequenceOf(DELETE_ALL, INSERT);
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
        List<Task> actual = taskRepository.findAll();
        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getId(), is(1));
        assertThat(actual.get(1).getId(), is(2));
    }

    @Test
    public void findOneMatchData() {
        final Operation DELETE_ALL = deleteAllFrom("tasks");
        final Operation INSERT =
                insertInto("tasks")
                        .row()
                        .column("id", 2)
                        .column("subject", "subject2")
                        .column("deadLine", "2018-12-4")
                        .column("hasDone", false)
                        .end()
                        .row()
                        .column("id", 1)
                        .column("subject", "subject1")
                        .column("deadLine", "2018-12-3")
                        .column("hasDone", false)
                        .end()
                        .row()
                        .column("id", 3)
                        .column("subject", "subject3")
                        .column("deadLine", "2018-11-3")
                        .column("hasDone", false)
                        .end().build();
        Operation operation = sequenceOf(DELETE_ALL, INSERT);
        DbSetup dbSetup = new DbSetup(new DataSourceDestination(dataSource), operation);
        dbSetup.launch();
        List<Task> db_data = taskRepository.findAll();
        assertThat(db_data.size(), is(3));
        Task t = db_data.get(1);
        Task actual = taskRepository.findOne(t.getId()).get();

        assertThat(actual.getId(), is(t.getId()));
        assertThat(actual.getSubject(), is(t.getSubject()));
        assertThat(actual.getDeadLine(), is(t.getDeadLine()));
        assertThat(actual.getHasDone(), is(t.getHasDone()));
    }
}