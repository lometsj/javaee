package com.lomebook.web;
import com.lomebook.repo.Bookrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class indexcontroller {

    @Autowired
    private Bookrepo b;


    @RequestMapping("/")
    public String index(ModelMap map){
        String t = b.findById(4949).getTitle();
        //String t = "hello";
        map.addAttribute("title",t);
        return "index";
    }
}
