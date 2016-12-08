package com.brianthi.spotify.controller;

import com.brianthi.spotify.util.Authenticator;
import com.brianthi.spotify.dao.pojo.Artist;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.exceptions.BadRequestException;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.models.PlaylistTrack;
import com.wrapper.spotify.models.SimplePlaylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class SpotifyController
{
    @Autowired
    private Authenticator spotifyAuthenticator;

    @CrossOrigin
    @GetMapping("apilogin")
    public String login() throws IOException, WebApiException, InterruptedException
    {
        Api api = spotifyAuthenticator.execute();

        String userId = api.getMe().build().get().getId();
        List<SimplePlaylist> myPlaylists = api.getPlaylistsForUser(userId).build().get().getItems();

        List<Artist> artists = new ArrayList<>();

        for (SimplePlaylist playlist : myPlaylists)
        {
            List<PlaylistTrack> playlistTracks = api.getPlaylistTracks(playlist.getOwner().getId(), playlist.getId()).build().get().getItems();
            playlistTracks.forEach(playlistTrack -> {
                playlistTrack.getTrack().getArtists().forEach(artist -> {
                    artists.add(new Artist(artist.getId(), artist.getName()));
                });
            });
        }

        Map<String, Integer> genres = new TreeMap<>();
        for(Artist artist : artists)
        {
            try
            {
                com.wrapper.spotify.models.Artist spotifyArtist = api.getArtist(artist.getSpotifyArtistId()).build().get();
                for(String genre : spotifyArtist.getGenres())
                {
                    if(genres.containsKey(genre))
                    {
                        genres.replace(genre, genres.get(genre) + 1);
                    }
                    else
                    {
                        genres.put(genre, 1);
                    }
                }
            } catch (BadRequestException ex)
            {
                Thread.sleep(3000);
            }
        }

        System.out.println(genres);

        return "";
    }

    public Authenticator getSpotifyAuthenticator()
    {
        return spotifyAuthenticator;
    }

    public void setSpotifyAuthenticator(Authenticator spotifyAuthenticator)
    {
        this.spotifyAuthenticator = spotifyAuthenticator;
    }
}
