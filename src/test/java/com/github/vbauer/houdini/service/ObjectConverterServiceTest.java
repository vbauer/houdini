package com.github.vbauer.houdini.service;

import com.github.vbauer.houdini.core.BasicTest;
import com.github.vbauer.houdini.model.User;
import com.github.vbauer.houdini.model.UserDTO;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Vladislav Bauer
 */

public class ObjectConverterServiceTest extends BasicTest {

    private static final int ID = 1;
    private static final String LOGIN = "vbauer";
    private static final String PASSWORD = "password";


    @Test
    public void testShortUserInfo() {
        final User user = createUser();
        final UserDTO userDTO = converterService.convert(UserDTO.class, user);

        Assert.assertEquals(ID, userDTO.getId());
        Assert.assertEquals(LOGIN, userDTO.getLogin());
        Assert.assertNull(userDTO.getPassword());
    }

    @Test
    public void testFullUserInfo() {
        final User user = createUser();
        final UserDTO userDTO = converterService.convert(UserDTO.class, user, true);

        Assert.assertEquals(ID, userDTO.getId());
        Assert.assertEquals(LOGIN, userDTO.getLogin());
        Assert.assertEquals(PASSWORD, userDTO.getPassword());
    }


    private User createUser() {
        return new User()
            .setId(ID)
            .setLogin(LOGIN)
            .setPassword(PASSWORD);
    }

}
