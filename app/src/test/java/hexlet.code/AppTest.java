package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlsRepository;
import hexlet.code.util.NamedRoutes;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import io.javalin.testtools.JavalinTest;

public class AppTest {

    private Javalin app;

    @BeforeEach
    public final void setUp() throws IOException, SQLException {
        app = App.getApp();
    }

    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.rootPath());
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());

            String responseBody = response.body() != null ? response.body().string() : "";
            assertThat(responseBody).contains("Анализатор страниц");
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlsPath());
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://example.com";
            var response = client.post(NamedRoutes.urlsPath(), requestBody);
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());

            String responseBody = response.body() != null ? response.body().string() : "";
            assertThat(responseBody).contains("https://example.com");
        });
    }

    @Test
    public void testUrlPage() throws SQLException {
        Url url = new Url("https://example.com", Timestamp.valueOf(LocalDateTime.now()));
        UrlsRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            var response = client.get(NamedRoutes.urlPath(url.getId()));
            assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
        });
    }

//    @Test
//    public void testBuildCourse() {
//        JavalinTest.test(app, (server, client) -> {
//            var response = client.get("/courses/build");
//            assertThat(response.code()).isEqualTo(200);
//        });
//    }
//
//    @Test
//    public void testCreateCourse() {
//        JavalinTest.test(app, (server, client) -> {
//            var requestBody = "name=coursename&desdcription=coursedescription";
//            var response = client.post("/courses", requestBody);
//            assertThat(response.code()).isEqualTo(200);
//            assertThat(response.body().string()).contains("coursename");
//        });
//    }
//
//    @Test
//    public void testPostsPage() {
//        JavalinTest.test(app, (server, client) -> {
//            var response = client.get("/posts");
//            assertThat(response.code()).isEqualTo(200);
//        });
//    }
//
//    @Test
//    public void testCarsPage() {
//        JavalinTest.test(app, (server, client) -> {
//            var response = client.get("/cars");
//            assertThat(response.code()).isEqualTo(200);
//        });
//    }
//
//    @Test
//    public void testBuildCarPage() {
//        JavalinTest.test(app, (server, client) -> {
//            var response = client.get("/cars/build");
//            assertThat(response.code()).isEqualTo(200);
//        });
//    }
//
//    @Test
//    public void testCarPage() throws SQLException {
//        var car = new Car("honda", "accord");
//        CarRepository.save(car);
//        JavalinTest.test(app, (server, client) -> {
//            var response = client.get("/cars/" + car.getId());
//            assertThat(response.code()).isEqualTo(200);
//        });
//    }
//
//    @Test
//    void testCarNotFound() throws Exception {
//        JavalinTest.test(app, (server, client) -> {
//            var response = client.get("/cars/999999");
//            assertThat(response.code()).isEqualTo(404);
//        });
//    }
//
//    @Test
//    void testUserNotFound() throws Exception {
//        JavalinTest.test(app, (server, client) -> {
//            var response = client.get("/users/999999");
//            assertThat(response.code()).isEqualTo(404);
//        });
//    }
}
