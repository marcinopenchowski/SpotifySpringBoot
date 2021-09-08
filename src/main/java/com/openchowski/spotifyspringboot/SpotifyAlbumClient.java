package com.openchowski.spotifyspringboot;

import com.openchowski.spotifyspringboot.model.Album;
import org.codehaus.jackson.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class SpotifyAlbumClient {

    @GetMapping("/album/{authorName}")
    public Album getAlbumsByAuthor(OAuth2Authentication details, @PathVariable String authorName){

        String jwt = ((OAuth2AuthenticationDetails)details.getDetails()).getTokenValue();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwt);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Album> exchange = restTemplate.exchange(
                "https://api.spotify.com/v1/search?q=" + authorName + "&type=track&market=US&offset=5&limit=10",
                    HttpMethod.GET,
                    httpEntity,
                    Album.class
                );

        return exchange.getBody();

    }

}
