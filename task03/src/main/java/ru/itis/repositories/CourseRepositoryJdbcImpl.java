package ru.itis.repositories;

import ru.itis.models.Course;

import javax.sql.ConnectionEvent;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CourseRepositoryJdbcImpl implements CourseRepository {

    //language=sql
    private final static String SQL_SELECT_ALL = "select * from course";

    //language=sql
    private final static String SQL_INSERT = "insert into course(title, start_date, finish_date) values (?, ?, ?)";

    private final DataSource dataSource;

    public CourseRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Course model) {
        try (Connection connection = dataSource.getConnection()) {

            try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String starSate = model.getStartDate();
                String finishDate = model.getFinishDate();

                statement.setString(1, model.getTitle());
                statement.setDate(2, Date.valueOf(LocalDate.parse(starSate, formatter)));
                statement.setDate(3, Date.valueOf(LocalDate.parse(finishDate, formatter)));

                int affectedRows = statement.executeUpdate();

                if (affectedRows != 1){
                    throw new SQLException("Cannot insert course");
                }

                try (ResultSet generatedIds = statement.getGeneratedKeys()){
                    if (generatedIds.next()){
                        model.setId(generatedIds.getInt("id"));
                    }
                    else {
                        throw new SQLException("Cannot resolve id");
                    }
                }
            }
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Course> findAll() {

        List<Course> courses = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()){

            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL)) {
                while (resultSet.next()){
                    Course course = Course.builder()
                            .id(resultSet.getInt("id"))
                            .title(resultSet.getString(("title")))
                            .startDate(resultSet.getString("start_date"))
                            .finishDate(resultSet.getString("finish_date"))
                            .build();
                    courses.add(course);
                }
            }
        }catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
        return courses;
    }
}
