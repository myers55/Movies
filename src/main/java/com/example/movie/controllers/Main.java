package com.example.movie.controllers;

import com.example.movie.Movie;
import com.example.movie.ResultsPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class Main {
    private String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=be2a38521a7859c95e2d73c48786e4bb";

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(){
        return "home";
    }
    @RequestMapping(path = "/now-playing", method = RequestMethod.GET)
    public String playing(Model model){
        model.addAttribute("movies",getMovies(url));

        return "now-playing";
    }
    @RequestMapping(path = "/medium-popular-long-name", method = RequestMethod.GET)
    public String mpln(Model model){
         List<Movie> movies = getMovies(url);
        movies.stream().filter(entry -> entry.getPopularity() > 30 && entry.getPopularity() < 80 && entry.getTitle().length() > 10).collect(Collectors.toList());
        model.addAttribute("movies",movies);
                 return "medium-popular-long-name";
    }

    public static List<Movie> getMovies(String route){
        RestTemplate restTemplate = new RestTemplate();
        ResultsPage response = restTemplate.getForObject(route, ResultsPage.class);
        return response.getResults();
    }

}
