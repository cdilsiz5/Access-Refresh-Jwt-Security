package com.id3.app.mapper;



import com.id3.app.dto.UserDto;
import com.id3.app.model.User;
import com.id3.app.request.CreateUserRequest;
import com.id3.app.request.UpdateUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper USER_MAPPER= Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> userList);

    User createUser(CreateUserRequest request);

    void updateUserRequest(UpdateUserRequest request, @MappingTarget User user);


}
