package com.example.euf2_vilchez_alexandre;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.euf2_vilchez_alexandre.database.AppDatabase;
import com.example.euf2_vilchez_alexandre.database.Genre;
import com.example.euf2_vilchez_alexandre.database.Movie;
import com.example.euf2_vilchez_alexandre.database.MovieDao;
import com.example.euf2_vilchez_alexandre.database.MovieGenreCrossRef;
import com.example.euf2_vilchez_alexandre.database.MovieWithGenres;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etTitle, etDuration, etGenre;
    private TextView tvDisplay;
    private MovieDao movieDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieDao = AppDatabase.getDatabase(this).movieDao();

        etTitle = findViewById(R.id.etTitle);
        etDuration = findViewById(R.id.etDuration);
        etGenre = findViewById(R.id.etGenre);
        tvDisplay = findViewById(R.id.tvDisplay);

        findViewById(R.id.btnAdd).setOnClickListener(v -> addMovie());
        findViewById(R.id.btnUpdate).setOnClickListener(v -> updateMovie());
        findViewById(R.id.btnDelete).setOnClickListener(v -> deleteMovie());
        findViewById(R.id.btnSearchGenre).setOnClickListener(v -> searchByGenre());
        findViewById(R.id.btnRecommend).setOnClickListener(v -> showRecommendation());

        loadMovies();
    }

    // Carregar llista de pel·lícules
    private void loadMovies() {
        List<MovieWithGenres> movies = movieDao.getAllMoviesWithGenres();
        StringBuilder sb = new StringBuilder("Llista de pel·lícules:\n\n");
        for (MovieWithGenres m : movies) {
            sb.append("ID: ").append(m.movie.id).append(" - ").append(m.movie.title)
                    .append(" (").append(m.movie.duration).append(" min) - Gèneres: ");
            for (Genre g : m.genres) {
                sb.append(g.name).append(" ");
            }
            sb.append("\n");
        }
        tvDisplay.setText(sb.toString());
    }

    // Afegir pel·lícules
    private void addMovie() {
        String title = etTitle.getText().toString();
        String durationStr = etDuration.getText().toString();
        String genreName = etGenre.getText().toString();

        // Missatge d'error si no omplen camps
        if (title.isEmpty() || durationStr.isEmpty() || genreName.isEmpty()) {
            Toast.makeText(this, "Omple tots els camps", Toast.LENGTH_SHORT).show();
            return;
        }

        int duration = Integer.parseInt(durationStr);
        Movie movie = new Movie(title, duration);
        long movieId = movieDao.insertMovie(movie);

        // Si el gènere encara no existeix es crea
        Genre genre = null;
        List<Genre> genres = movieDao.getAllGenres();
        for (Genre g : genres) {
            if (g.name.equalsIgnoreCase(genreName)) {
                genre = g;
                break;
            }
        }
        if (genre == null) {
            genre = new Genre(genreName);
            long genreId = movieDao.insertGenre(genre);
            genre.id = (int) genreId;
        }

        // Afegir a la llista i refrescar
        movieDao.insertMovieGenreCrossRef(new MovieGenreCrossRef((int) movieId, genre.id));
        loadMovies();
        clearFields();
    }

    // Eliminar pel·lícula per títol
    private void deleteMovie() {
        String title = etTitle.getText().toString();
        if (title.isEmpty()) return;

        List<MovieWithGenres> movies = movieDao.getAllMoviesWithGenres();
        for (MovieWithGenres m : movies) {
            if (m.movie.title.equalsIgnoreCase(title)) {
                movieDao.deleteMovie(m.movie);
                break;
            }
        }
        loadMovies();
        clearFields();
    }

    // Modificar pel·lícula per títol
    private void updateMovie() {
        String title = etTitle.getText().toString();
        String durationStr = etDuration.getText().toString();
        if (title.isEmpty() || durationStr.isEmpty()) return;

        List<MovieWithGenres> movies = movieDao.getAllMoviesWithGenres();
        for (MovieWithGenres m : movies) {
            if (m.movie.title.equalsIgnoreCase(title)) {
                m.movie.duration = Integer.parseInt(durationStr);
                movieDao.updateMovie(m.movie);
                break;
            }
        }
        loadMovies();
        clearFields();
    }

    // Consultar per gènere
    private void searchByGenre() {
        String genreName = etGenre.getText().toString();
        if (genreName.isEmpty()) {
            loadMovies();
            return;
        }

        Genre genre = null;
        List<Genre> genres = movieDao.getAllGenres();
        for (Genre g : genres) {
            if (g.name.equalsIgnoreCase(genreName)) {
                genre = g;
                break;
            }
        }

        if (genre != null) {
            List<MovieWithGenres> movies = movieDao.getMoviesByGenre(genre.id);
            StringBuilder sb = new StringBuilder("Resultats per gènere " + genreName + ":\n\n");
            for (MovieWithGenres m : movies) {
                sb.append(m.movie.title).append(" (").append(m.movie.duration).append(" min)\n");
            }
            tvDisplay.setText(sb.toString());
        } else {
            tvDisplay.setText("No s'han trobat pel·lícules d'aquest gènere.");
        }
    }

    // Notificació per terminal
    private void showRecommendation() {
        String recommendedMovie = "Inception";
        
        System.out.println("Et recomano la següent pel·lícula: " + recommendedMovie);
    }

    private void clearFields() {
        etTitle.setText("");
        etDuration.setText("");
        etGenre.setText("");
    }
}
