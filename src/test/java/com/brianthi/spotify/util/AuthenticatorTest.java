package com.brianthi.spotify.util;

import com.wrapper.spotify.exceptions.WebApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticatorTest
{

    @Autowired
    Authenticator authenticator;

    @Test
    public void testExecuteAuthentication() throws IOException, WebApiException
    {
        authenticator.execute();
    }

    @Test
    public void testMultipleAuthenticationExecutions()
    {
        Authenticator authenticator = new Authenticator();

    }

}
