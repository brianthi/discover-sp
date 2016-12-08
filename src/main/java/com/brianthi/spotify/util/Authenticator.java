package com.brianthi.spotify.util;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.models.AuthorizationCodeCredentials;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@Service
public final class Authenticator
{
    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @Value("${spotify.redirect.uri}")
    private String redirectUri;

    private final List<String> scopes = Collections.singletonList("playlist-read-private");

    public Api execute() throws WebApiException, IOException
    {
        final Api api = Api.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectURI(redirectUri)
                .build();

        final String state = "Initialize Authentication";
        String authorizeURL = api.createAuthorizeURL(scopes, state);

        String code = getAuthorizationCode(authorizeURL);

        AuthorizationCodeCredentials codeCredentials;
        try
        {
            codeCredentials = api.authorizationCodeGrant(code).build().get();
        } catch (WebApiException | IOException e )
        {
            //TODO: Add real logging
            e.printStackTrace();
            throw e;
        }

        api.setAccessToken(codeCredentials.getAccessToken());
        api.setRefreshToken(codeCredentials.getRefreshToken());

        return api;
//        WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
//
//        final HtmlPage spotifyAuthPage = webClient.getPage(authorizeURL);
//        spotifyAuthPage.getEnclosingWindow().getEnclosedPage().initialize();
//        JavaScriptJobManager manager = spotifyAuthPage.getEnclosingWindow().getJobManager();
//        while (manager.getJobCount() > 0) {
//            try
//            {
//                Thread.sleep(1000);
//            } catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//        final HtmlPage spotifyLogInPage = spotifyAuthPage.getAnchors().get(1).click();
//
//        manager = spotifyLogInPage.getEnclosingWindow().getJobManager();
//        while (manager.getJobCount() > 0) {
//            try
//            {
//                Thread.sleep(1000);
//            } catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//        }
//
//        final HtmlPage facebookLoginPage = spotifyLogInPage.getAnchors().get(1).click();
//
//        manager = facebookLoginPage.getEnclosingWindow().getJobManager();
//        while (manager.getJobCount() > 0) {
//            try
//            {
//                Thread.sleep(1000);
//            } catch (InterruptedException e)
//            {
//                e.printStackTrace();
//            }
//        }
//        facebookLoginPage.getElementById("email").setTextContent("4158463039");
//        facebookLoginPage.getElementById("pass").setTextContent("hostNE90a");
//        String authorized = facebookLoginPage.getElementById("loginbutton").click();
    }

    private String getAuthorizationCode(String authorizeURL) throws MalformedURLException
    {
        Capabilities caps = new DesiredCapabilities();
        ((DesiredCapabilities) caps).setJavascriptEnabled(false);
        ((DesiredCapabilities) caps).setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] {
                "--web-security=false",
                "--ssl-protocol=any",
                "--ignore-ssl-errors=true"
        });
        WebDriver driver = new FirefoxDriver(caps);
        driver.get(authorizeURL);
        driver.findElement(By.xpath("/html/body/div/div[2]/div/div[2]/div/a")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/a")).click();
        driver.findElement(By.id("email")).sendKeys("bthi1120");
        driver.findElement(By.id("pass")).sendKeys("spotifydiscovery1289");
        driver.findElement(By.id("loginbutton")).submit();

        URL url;

        try
        {
            url = new URL(driver.getCurrentUrl());
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
            throw e;
        }

        driver.close();
        return url.getQuery().split("&")[0].split("=")[1];
    }

}
