package com.costcontrol.Springproject1.controllers;

import com.costcontrol.Springproject1.models.News;
import com.costcontrol.Springproject1.repo.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsrepository;

    @GetMapping("/news")
    public String news(Model model) {
        Iterable<News> neu = newsrepository.findAll();
        model.addAttribute("neu", neu);
        return "news_page";
    }
    @GetMapping("/news/add")
    public String newsAdd(Model model) {
        return "news_add";
    }
    @PostMapping("/news/add")
    public String newscontentAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        News news = new News(title,anons,full_text);
        newsrepository.save(news);
        return "redirect:/news";
    }
    @GetMapping("/news/{id}")
    public String newsDetails(@PathVariable(value="id") long id, Model model) {
        if(!newsrepository.existsById(id)){
            return "redirect:/news";
        }
        Optional<News> news = newsrepository.findById(id);
        ArrayList<News> res = new ArrayList<>();
        news.ifPresent(res::add);
        model.addAttribute("news", res);
        return "news_details";
    }
    @GetMapping("/news/{id}/edit")
    public String newsEdit(@PathVariable(value="id") long id, Model model) {
        if(!newsrepository.existsById(id)){
            return "redirect:/news";
        }
        Optional<News> news = newsrepository.findById(id);
        ArrayList<News> res = new ArrayList<>();
        news.ifPresent(res::add);
        model.addAttribute("news", res);
        return "news_edit";
    }
    @PostMapping("/news/{id}/edit")
    public String newsEditPost(@PathVariable(value="id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model){
        News news = newsrepository.findById(id).orElseThrow();
        news.setTitle(title);
        news.setAnons(anons);
        news.setFull_text(full_text);
        newsrepository.save(news);
        return "redirect:/news";
    }
    @PostMapping("/news/{id}/remove")
    public String newsDelete(@PathVariable(value="id") long id){
        News news = newsrepository.findById(id).orElseThrow();
        newsrepository.delete(news);
        return "redirect:/news";
    }
}