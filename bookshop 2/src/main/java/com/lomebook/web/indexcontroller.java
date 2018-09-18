package com.lomebook.web;
import com.lomebook.enti.*;
import com.lomebook.repo.*;
import com.lomebook.tools.Acti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.BooleanSupplier;


@Controller
public class indexcontroller {

    @Autowired
    private Bookrepo bookrepo;

    @Autowired
    private Searchrepo searchrepo;

    @Autowired
    private Cartrepo cartrepo;

    @Autowired
    private Userrepo userrepo;

    private void saveSearch(String str){
        SearchDetailsEntity searchDetailsEntity = new SearchDetailsEntity();
        searchDetailsEntity.setId(UUID.randomUUID().toString());
        searchDetailsEntity.setKeyWords(str);
        searchDetailsEntity.setSearchDateTime(new java.sql.Timestamp(System.currentTimeMillis()));
        searchrepo.save(searchDetailsEntity);
    }

    @RequestMapping("/bookinfo")
    public String bookInfo(ModelMap map,HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        BooksEntity booksEntity = bookrepo.findById(id);
        map.addAttribute("book",booksEntity);
        return "BookInfo";
    }

    @RequestMapping("/search_i")
    public String searchIsbn(ModelMap map,HttpServletRequest request){
        String isbn = request.getParameter("isbn");
        List<BooksEntity> datas = bookrepo.findAllByIsbnLike("%"+isbn+"%");
        saveSearch(isbn);
        map.addAttribute("books",datas);
        return "Index";
    }

    @RequestMapping("/search_t")
    public String searchTitle(ModelMap map,HttpServletRequest request){
        String title = request.getParameter("title");
        List<BooksEntity> datas = bookrepo.findAllByTitleLike("%"+title+"%");
        saveSearch(title);
        map.addAttribute("books",datas);
        return "Index";
    }

    @RequestMapping("/")
    public String index(ModelMap map,HttpServletRequest request){
        int page;
        if(request.getSession().getAttribute("page") == null){
            request.getSession().setAttribute("page",0);
            page = 0;
        }
        else{
            page = (int)request.getSession().getAttribute("page");
        }
        Pageable pageable =new PageRequest(page, 12);
        Page<BooksEntity> datas = bookrepo.findAll(pageable);
        map.addAttribute("books",datas);
        return "Index";
    }

    @RequestMapping("/Index")
    public String index_2(ModelMap map){
        return "Index";
    }

    @RequestMapping("/UserInfo")
    public String userinfo(ModelMap map){
        return "UserInfo";
    }

    @RequestMapping("/Register")
    public String register(@ModelAttribute("userInfo") Register register, ModelMap map){
        return "Register";
    }

    @RequestMapping("/congrats")
    public String congrats(ModelMap map){
        return "congrats";
    }

    @RequestMapping("/Login")
    public String login(ModelMap map){
        return "Login";
    }

    @RequestMapping("/Reset")
    public String reset(ModelMap map,HttpServletRequest request){
        if(request.getSession().getAttribute("resetuser")!=null){
            return "Reset";
        }
        return "Index";
    }

    @RequestMapping("/Forget")
    public String forget(){
        return "Forget";
    }

    @RequestMapping("/Cart")
    public String cart(HttpServletRequest request,ModelMap map){
        if(request.getSession().getAttribute("user")!=null){
            List<CartEntity> carts = cartrepo.findAllByUserId(userrepo.findByLoginId(request.getSession().getAttribute("user").toString()).getId());
            //map.addAttribute("carts",carts);
            List<BookcartEntity> bookcartEntities = new ArrayList<BookcartEntity>();
            int count = 0;
            int num = 0;
            for(CartEntity t:carts){
                BookcartEntity bookcartEntity = new BookcartEntity();
                bookcartEntity.setCartEntity(t);
                bookcartEntity.setBooksEntity(bookrepo.findById(t.getBookId()));
                bookcartEntities.add(bookcartEntity);
                count += t.getCount() * bookrepo.findById(t.getBookId()).getUnitPrice();
                num += t.getCount();
            }
            map.addAttribute("carts",bookcartEntities);
            map.addAttribute("count",count);
            map.addAttribute("num",num);
            return "Cart";
        }
        return "Cart";
    }

    @RequestMapping("/Loginback")
    public String loginback(HttpServletRequest request,ModelMap map){
        return "Loginback";
    }

    @Autowired
    Userinforepo userinforepo;

    @Autowired
    Rolerepo rolerepo;

    @Autowired
    Caterepo caterepo;

    @Autowired
    Pubrepo pubrepo;

    @RequestMapping("/Back")
    public String back(HttpServletRequest request,ModelMap map){
        if(request.getSession().getAttribute("userback")==null){
            return "Index";
        }
        int page;
        if(request.getSession().getAttribute("page_b") == null){
            request.getSession().setAttribute("page_b",0);
            page = 0;
        }
        else{
            page = (int)request.getSession().getAttribute("page_b");
        }
        Pageable pageable =new PageRequest(page, 12);
        Page<BooksEntity> datas = bookrepo.findAll(pageable);
        map.addAttribute("books",datas);

        List <UserInfoEntity> t = new ArrayList<UserInfoEntity>();
        t = userinforepo.findAll();
        map.addAttribute("users",t);

        List <RoleEntity> t2 = new ArrayList<RoleEntity>();
        t2 = rolerepo.findAll();
        map.addAttribute("roles",t2);

//        List <BooksEntity> t3 = new ArrayList<BooksEntity>();
//        t3 = bookrepo.findAll();
//        map.addAttribute("books",t3);

        List <CategoriesEntity> t4 = new ArrayList<CategoriesEntity>();
        t4 = caterepo.findAll();
        map.addAttribute("cates",t4);

        List <PublishersEntity> t5 = new ArrayList<PublishersEntity>();
        t5 = pubrepo.findAll();
        map.addAttribute("pubs",t5);
        return "Back";
    }
}
