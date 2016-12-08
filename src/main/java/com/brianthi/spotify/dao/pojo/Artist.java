package com.brianthi.spotify.dao.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Artist
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int artistId;

    private String spotifyArtistId;

    private String name;

    public Artist(String spotifyArtistId, String name)
    {
        this.spotifyArtistId = spotifyArtistId;
        this.name = name;
    }

    public int getArtistId()
    {
        return artistId;
    }

    public void setArtistId(int artistId)
    {
        this.artistId = artistId;
    }

    public String getSpotifyArtistId()
    {
        return spotifyArtistId;
    }

    public void setSpotifyArtistId(String spotifyArtistId)
    {
        this.spotifyArtistId = spotifyArtistId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
