package com.lomebook.web;
import com.lomebook.enti.BooksEntity;
import com.lomebook.repo.Bookrepo;
import com.lomebook.tools.Acti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class indexcontroller {

    @Autowired
    private Bookrepo b;

    @RequestMapping("/")
    public String index(ModelMap map){
        Acti a = new Acti();
        //a.sendEmail("lometsj@live.com");
        List<BooksEntity>booksEntityList = b.findAll();
        String t = b.findById(4949).getTitle();
        //String t = "hello";
        map.addAttribute("error",t);
        map.addAttribute("booklist",booksEntityList);
        return "index";
    }

    @RequestMapping("/register")
    public String register(ModelMap map){
        return "register";
    }

    @RequestMapping("/congrats")
    public String congrats(ModelMap map){
        return "congrats";
    }

    @RequestMapping("/login")
    public String login(ModelMap map){
        return "login";
    }
}
