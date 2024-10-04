package dev.joseluisgs;

import dev.joseluisgs.model.User;
import dev.joseluisgs.repository.UserRemoteRepository;
import dev.joseluisgs.rest.RetrofitClient;
import dev.joseluisgs.rest.UserApiRest;

public class Main {
    public static void main(String[] args) {
        var retrofit = RetrofitClient.getClient(UserApiRest.API_USERS_URL);
        var userApiRest = retrofit.create(UserApiRest.class);

        UserRemoteRepository userRemoteRepository = new UserRemoteRepository(userApiRest);
        var users = userRemoteRepository.getAllCompletableFuture();
        users.forEach(System.out::println);

        System.out.println("----------------------------------------");
        var usersPage = userRemoteRepository.getAllWithPage(2);
        usersPage.forEach(System.out::println);

        System.out.println("----------------------------------------");
        var user = userRemoteRepository.getById(2);
        System.out.println(user);

        System.out.println("----------------------------------------");
        var userCreate = User.builder()
                .firstName("Jose")
                .lastName("Garcia")
                .job("Developer")
                .email("jose@example.com")
                .avatar("https://reqres.in/img/faces/2-image.jpg")
                .build();

        var userCreated = userRemoteRepository.createUser(userCreate);
        System.out.println(userCreated);

        System.out.println("----------------------------------------");
        var userUpdated = userRemoteRepository.updateUser(2, userCreate);
        System.out.println(userUpdated);

        System.out.println("----------------------------------------");
        userRemoteRepository.deleteUser(563453643);

        System.out.println("PR----------------------------------------");
        userRemoteRepository.getAllWithReactor().subscribe(System.out::println);

        System.out.println("RX----------------------------------------");
        userRemoteRepository.getAllWithRxJava().blockingForEach(System.out::println);

        System.out.println("----------------------------------------");
        var jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        userCreated = userRemoteRepository.createUserWithToken(jwt, userCreate);
        System.out.println(userCreated);

        System.exit(0);
    }
}