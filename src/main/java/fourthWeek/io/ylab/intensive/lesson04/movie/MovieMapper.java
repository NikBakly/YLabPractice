package fourthWeek.io.ylab.intensive.lesson04.movie;

public class MovieMapper {
    private MovieMapper() {
    }

    public static Movie toMovie(String year,
                                String length,
                                String title,
                                String subject,
                                String actors,
                                String actress,
                                String director,
                                String popularity,
                                String awards) {
        Movie newMovie = new Movie();
        if (!year.isBlank()) {
            newMovie.setYear(Integer.valueOf(year));
        }
        if (!length.isBlank()) {
            newMovie.setLength(Integer.valueOf(length));
        }
        if (!title.isBlank()) {
            newMovie.setTitle(title);
        }
        if (!subject.isBlank()) {
            newMovie.setSubject(subject);
        }
        if (!actors.isBlank()) {
            newMovie.setActors(actors);
        }
        if (!actress.isBlank()) {
            newMovie.setActress(actress);
        }
        if (!director.isBlank()) {
            newMovie.setDirector(director);
        }
        if (!popularity.isBlank()) {
            newMovie.setPopularity(Integer.valueOf(popularity));
        }
        if (!awards.isBlank()) {
            if (awards.equalsIgnoreCase("yes")) {
                newMovie.setAwards(Boolean.TRUE);
            } else {
                newMovie.setAwards(Boolean.FALSE);
            }
        }
        return newMovie;
    }
}
