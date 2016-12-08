package com.brianthi;

import com.brianthi.spotify.controller.SpotifyController;
import com.brianthi.spotify.util.Authenticator;
import com.wrapper.spotify.exceptions.WebApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DiscoverSpApplicationTests {

    @Autowired
    Authenticator authenticator;

	@Test
	public void contextLoads() throws IOException, WebApiException, InterruptedException
    {
        SpotifyController controller = new SpotifyController();
        controller.setSpotifyAuthenticator(authenticator);
        controller.login();
	}

    //TODO: Need to have tests get configuration from test application.properties

}
