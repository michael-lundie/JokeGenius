package io.lundie.gradle.jokegenius.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import io.lundie.jokerlib.*;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.jokegenius.gradle.lundie.io",
                ownerName = "backend.jokegenius.gradle.lundie.io",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {

        Joker joker = new Joker();

        MyBean response = new MyBean();
        response.setData("Hi, " + name + " " + joker.getJoke());

        return response;
    }

    @ApiMethod(name = "getJoke")
    public MyBean getJoke() {
        Joker joker = new Joker();
        MyBean response = new MyBean();
        response.setData(joker.getJoke());
        return response;
    }

}
