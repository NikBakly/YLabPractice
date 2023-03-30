package fourthWeek.io.ylab.intensive.lesson04.movie;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class MovieLoaderImpl implements MovieLoader {
    private final DataSource dataSource;

    public MovieLoaderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void loadData(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            br.readLine();
            while (br.ready()) {
                String[] valueOfFields = br.readLine().split(";");
                createMovie(valueOfFields);
            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createMovie(String[] valueOfFields) throws SQLException {
        Movie newMovie = MovieMapper.toMovie(
                valueOfFields[0],
                valueOfFields[1],
                valueOfFields[2],
                valueOfFields[3],
                valueOfFields[4],
                valueOfFields[5],
                valueOfFields[6],
                valueOfFields[7],
                valueOfFields[8]
        );
        addMovie(newMovie);
    }

    private void addMovie(Movie movie) throws SQLException {
        String insertValuesSql =
                "INSERT INTO movie (year, length, title, subject, actors, actress, director, popularity, awards)" +
                        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertValuesSql)) {
            setValues(movie, preparedStatement);
            preparedStatement.executeUpdate();
        }
    }

    private void setValues(Movie movie, PreparedStatement preparedStatement)
            throws SQLException {
        if (movie.getYear() == null) {
            preparedStatement.setNull(1, Types.INTEGER);
        } else {
            preparedStatement.setInt(1, movie.getYear());
        }
        if (movie.getLength() == null) {
            preparedStatement.setNull(2, Types.INTEGER);
        } else {
            preparedStatement.setInt(2, movie.getLength());
        }
        if (movie.getTitle() == null) {
            preparedStatement.setNull(3, Types.VARCHAR);
        } else {
            preparedStatement.setString(3, movie.getTitle());
        }
        if (movie.getSubject() == null) {
            preparedStatement.setNull(4, Types.VARCHAR);
        } else {
            preparedStatement.setString(4, movie.getSubject());
        }
        if (movie.getActors() == null) {
            preparedStatement.setNull(5, Types.VARCHAR);
        } else {
            preparedStatement.setString(5, movie.getActors());
        }
        if (movie.getActress() == null) {
            preparedStatement.setNull(6, Types.VARCHAR);
        } else {
            preparedStatement.setString(6, movie.getActress());
        }
        if (movie.getDirector() == null) {
            preparedStatement.setNull(7, Types.VARCHAR);
        } else {
            preparedStatement.setString(7, movie.getDirector());
        }
        if (movie.getPopularity() == null) {
            preparedStatement.setNull(8, Types.INTEGER);
        } else {
            preparedStatement.setInt(8, movie.getPopularity());
        }
        if (movie.getAwards() == null) {
            preparedStatement.setNull(9, Types.BOOLEAN);
        } else {
            preparedStatement.setBoolean(9, movie.getAwards());
        }
    }
}
