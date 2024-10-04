package dev.joseluisgs.rest;

import dev.joseluisgs.rest.responses.createupdatedelete.Request;
import dev.joseluisgs.rest.responses.createupdatedelete.Response;
import dev.joseluisgs.rest.responses.getall.ResponseGetAll;
import dev.joseluisgs.rest.responses.getbyid.ResponseGetById;
import io.reactivex.rxjava3.core.Single;
import reactor.core.publisher.Mono;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.concurrent.CompletableFuture;

public interface UserApiRest {
    String API_USERS_URL = "https://reqres.in/api/";

    @GET("users")
    Call<ResponseGetAll> getAllSync();

    @GET("users")
    CompletableFuture<ResponseGetAll> getAllCompletableFuture();

    @GET("users")
    Mono<ResponseGetAll> getAllWithReactor();

    @GET("users")
    Single<ResponseGetAll> getAllWithRxJava();

    @GET("users")
    CompletableFuture<ResponseGetAll> getAllWithPage(@Query("page") int page);

    @GET("users/{id}")
    Call<ResponseGetById> getByIdSync(@Path("id") int id);

    @GET("users/{id}")
    CompletableFuture<ResponseGetById> getById(@Path("id") int id);

    @POST("users")
    CompletableFuture<Response> createUser(@Body Request user);

    @PUT("users/{id}")
    CompletableFuture<Response> updateUser(@Path("id") int id, @Body Request user);

    @DELETE("users/{id}")
    CompletableFuture<Response> deleteUser(@Path("id") int id);

    @POST("users")
    CompletableFuture<Response> createUserWithToken(
            @Header("Authorization") String token,
            @Body Request user
    );


}
