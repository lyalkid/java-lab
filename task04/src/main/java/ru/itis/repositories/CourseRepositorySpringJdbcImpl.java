package ru.itis.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.itis.models.Course;
import ru.itis.models.Student;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class CourseRepositorySpringJdbcImpl implements CourseRepository {
    //language=sql
    private final static String SQL_SELECT_ALL = "select * from course";

    private final static String COURSE_TABLE = "course";

    private final DataSource dataSource;

    private final SimpleJdbcInsert insertCourse;

    private final JdbcTemplate jdbcTemplate;

    public CourseRepositorySpringJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;

        jdbcTemplate = new JdbcTemplate(dataSource);

        insertCourse = new SimpleJdbcInsert(dataSource);

        insertCourse.withTableName(COURSE_TABLE)
                .usingGeneratedKeyColumns("id");
    }

    private static final Function<Course, Map<String, Object>> toParams = course -> {
        Map<String, Object> params = new HashMap<>();
        params.put("title", course.getTitle());
        params.put("start_date", course.getStartDate());
        params.put("finish_date", course.getFinishDate());

        return params;
    };

    private static final RowMapper<Course> toCourse = (row, rowNum) -> Course.builder()
            .id(row.getInt("id"))
            .title(row.getString("title"))
            .startDate(row.getString("start_date"))
            .finishDate(row.getString("finish_date"))
            .build();

    @Override
    public void save(Course model) {
        int generatedId = insertCourse.executeAndReturnKey(toParams.apply(model)).intValue();

        model.setId(generatedId);
    }

    @Override
    public List<Course> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL, toCourse);
    }
}
