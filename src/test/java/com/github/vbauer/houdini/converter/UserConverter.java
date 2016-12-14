package com.github.vbauer.houdini.converter;

import com.github.vbauer.houdini.annotation.ObjectConverter;
import com.github.vbauer.houdini.model.User;
import com.github.vbauer.houdini.model.UserDTO;
import org.springframework.stereotype.Component;

/**
 * @author Vladislav Bauer
 */

@SuppressWarnings("all")
@Component
@ObjectConverter
public class UserConverter {

    public static UserDTO fullInfo(final User user, final Boolean full) {
        final UserDTO userDTO = new UserDTO()
            .setId(user.getId())
            .setLogin(user.getLogin());

        if (Boolean.TRUE.equals(full)) {
            userDTO.setPassword(user.getPassword());
        }

        return userDTO;
    }

    public UserDTO shortInfo(final User user) {
        return fullInfo(user, false);
    }

    public UserDTO badConverter(final User user, final Boolean full, final Boolean male) {
        throw new UnsupportedOperationException("Converter is not supported");
    }

    private UserDTO privateConverter(final String login) {
        return shortInfo(new User().setLogin(login));
    }

}
