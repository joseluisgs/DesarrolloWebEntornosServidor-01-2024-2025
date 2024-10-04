package dev.joseluisgs.mapper;

import dev.joseluisgs.model.User;
import dev.joseluisgs.rest.responses.createupdatedelete.Request;
import dev.joseluisgs.rest.responses.createupdatedelete.Response;
import dev.joseluisgs.rest.responses.getall.UserGetAll;

public class UserMapper {
    public static User toUserFromCreate(UserGetAll userGetAll) {
        return User.builder()
                .id((long) userGetAll.getId())
                .firstName(userGetAll.getFirstName())
                .lastName(userGetAll.getLastName())
                .email(userGetAll.getEmail())
                .avatar(userGetAll.getAvatar())
                .build();
    }

    public static User toUserFromCreate(dev.joseluisgs.rest.responses.getbyid.UserGetById userGetById) {
        return User.builder()
                .id((long) userGetById.getId())
                .firstName(userGetById.getFirstName())
                .lastName(userGetById.getLastName())
                .email(userGetById.getEmail())
                .avatar(userGetById.getAvatar())
                .build();
    }

    public static Request toRequest(User user) {
        return Request.builder()
                .name(user.getFirstName() + " " + user.getLastName())
                .job("Developer")
                .build();
    }

    public static User toUserFromCreate(Response response) {
        return User.builder()
                .id(Long.parseLong(response.getId()))
                .firstName(response.getName())
                .lastName("")
                .email("")
                .avatar("")
                .job(response.getJob())
                //.createdAt(LocalDateTime.parse(response.getCreatedAt()))
                //.updatedAt(LocalDateTime.parse(response.getUpdatedAt()))
                .build();
    }

    public static User toUserFromUpdate(Response response, int id) {
        return User.builder()
                .id((long) id)
                .firstName(response.getName())
                .lastName("")
                .email("")
                .avatar("")
                .job(response.getJob())
                //.createdAt(LocalDateTime.parse(response.getCreatedAt()))
                //.updatedAt(LocalDateTime.parse(response.getUpdatedAt()))
                .build();
    }
}
