package com.lomebook.web;

import com.lomebook.enti.*;
import com.lomebook.repo.*;
import com.lomebook.tools.Acti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class apicontroller {

    @Autowired
    private Cartrepo cartrepo;

    @Autowired
    private Bookrepo bookrepo;
    @Autowired
    private Userrepo userrepo;

    private Acti acti = new Acti();

    @PostMapping("/api/register")
    public Map<String, String> registerAjax(@RequestBody @Valid Register register, BindingResult bindingResult, HttpServletResponse response,HttpServletRequest request) {
        System.out.println("get in ajax api");
        Map<String, String> ret = new HashMap<String, String>();
        //bindingResult.getAllErrors().stream().
        String t = bindingResult.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError error : list) {
                System.out.println(error.getDefaultMessage());
                ret.put("res",error.getDefaultMessage());
            }

            //ret.put("res", t);
            return ret;
        }
        UsersEntity is_email = userrepo.findByMail(register.getEmail());
        //UserEO userEO = userRepo.findByEmail(register.getEmail());
        UsersEntity is_name = userrepo.findByLoginId(register.getUsername());


        if (is_email != null) {
            ret.put("res", "email existed");
        } else if (is_name != null) {
            ret.put("res", "username existed");
        } else if (!register.getPassword().equals(register.getPassword_r())) {
            ret.put("res", "password should be same");
        } else {

            String rUrl = String.valueOf(request.getRequestURL());
            System.out.println(rUrl);

            String servername = request.getServerName();
            int port = request.getServerPort();
            String url = servername + ":" + String.valueOf(port);
            //String url = rUrl.substring(0, servername);
            //System.out.println(url);

            acti.sendactiEmail(register.getEmail(),url);
            ret.put("res", "success");
            UsersEntity add = new UsersEntity();
            add.setMail(register.getEmail());
            add.setLoginPwd(register.getPassword());
            add.setLoginId(register.getUsername());
            add.setAddress("");
            add.setPhone("");
            add.setName("");
            add.setUserStateId(2);
            userrepo.save(add);
        }

        return ret;
    }

    @PostMapping("/api/login")
    public Map<String, String> loginAjax(@RequestBody @Valid Login login, BindingResult bindingResult, HttpServletRequest req) {
        Map<String, String> ret = new HashMap<String, String>();
        String t = bindingResult.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));
        if (bindingResult.hasErrors()) {
            ret.put("res", t);
            return ret;
        }

        UsersEntity is_email = userrepo.findByLoginId(login.getUser());
        UsersEntity is_username = userrepo.findByLoginId(login.getUser());

        if (is_email == null && is_username == null) {
            ret.put("res", "no such user");
        } else {
            UsersEntity is = (is_email != null) ? is_email : is_username;
            //String password = is.getLoginPwd();
            if (is.getLoginPwd().equals(login.getPassword())) {
                req.getSession().setAttribute("user", is.getLoginId());
                if (is.getUserStateId() == 1) {
                    ret.put("res", "success");
                } else {
                    ret.put("res", "s_acti");
                    ret.put("email",is.getMail());
                }
            }
            else{
                ret.put("res","user or pwd wrong");
            }
            return ret;
        }
        return ret;
    }

    @GetMapping("/api/acti")
    public void actiCheck(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        if(acti.checkCode(id,code)){
            request.getSession().setAttribute("user",userrepo.findByMail(id).getLoginId());
            UsersEntity t = userrepo.findByMail(id);
            t.setUserStateId(1);
            userrepo.save(t);
            System.out.println("acti my !!!!!!!");

        }
        response.sendRedirect("/");
        return;
    }

    @GetMapping("/api/logout")
    public void logOut(HttpServletRequest req,HttpServletResponse response) throws Exception{
        req.getSession().removeAttribute("user");
        ModelAndView mv = new ModelAndView("Index");
        response.sendRedirect("/");
        return;
    }

    @GetMapping("/api/logout_b")
    public void logOutback(HttpServletRequest req,HttpServletResponse response) throws Exception{
        req.getSession().removeAttribute("userback");
        response.sendRedirect("/Loginback");
        return;
    }

    @PostMapping("/api/forget")
    public String forget(String email,HttpServletRequest request){
        String servername = request.getServerName();
        int port = request.getServerPort();
        String url = servername + ":" + String.valueOf(port);

        acti.sendforgetEmail(email,url);
        return "success";
    }

    @GetMapping("/api/reset")
    public void resetPwd(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String id = request.getParameter("id");
        String code = request.getParameter("code");
        if(acti.checkCode(id,code)){
            request.getSession().setAttribute("resetuser",userrepo.findByMail(id).getLoginId());
            response.sendRedirect("/Reset");
        }
        else {
            response.sendRedirect("/");
        }

    }

    @PostMapping("/api/resetpwd")
    public Map<String, String> resetAjax(@RequestBody @Valid Resetpwd resetpwd, BindingResult bindingResult, HttpServletRequest req) {
        System.out.println("into resetpwd");
        Map<String, String> ret = new HashMap<String, String>();
        if(resetpwd.getPassword().equals(resetpwd.getPassword_r())){
            String userid = req.getSession().getAttribute("resetuser").toString();
            UsersEntity t = userrepo.findByLoginId(userid);
            t.setLoginPwd(resetpwd.getPassword());
            userrepo.save(t);
            req.getSession().removeAttribute("resetuser");
            ret.put("res","success");
        }
        return ret;

    }

    @PostMapping("/api/modinfo")
    public Map<String, String> modAjax(@RequestBody @Valid Mod mod, BindingResult bindingResult, HttpServletRequest req) {
        Map<String, String> ret = new HashMap<String, String>();
        String userid = req.getSession().getAttribute("user").toString();
        UsersEntity t = userrepo.findByLoginId(userid);
        t.setName(mod.getName());
        t.setAddress(mod.getAddress());
        t.setPhone(mod.getPhone());
        userrepo.save(t);
        return ret;
    }

    @PostMapping("/api/previous")
    public void previousAjax(ModelMap map,HttpServletRequest request){
        int page = (int)request.getSession().getAttribute("page");
        if(page - 1 >= 0){
            request.getSession().setAttribute("page",page - 1);
        }
        return;
    }

    @PostMapping("/api/next")
    public void nextAjax(ModelMap map,HttpServletRequest request){
        int page = (int)request.getSession().getAttribute("page");
        request.getSession().setAttribute("page",page + 1);
        return;
    }

    @PostMapping("/api/previous_b")
    public void previousAjax_b(ModelMap map,HttpServletRequest request){
        int page = (int)request.getSession().getAttribute("page_b");
        if(page - 1 >= 0){
            request.getSession().setAttribute("page_b",page - 1);
        }
        return;
    }

    @PostMapping("/api/next_b")
    public void nextAjax_b(ModelMap map,HttpServletRequest request){
        int page = (int)request.getSession().getAttribute("page_b");
        request.getSession().setAttribute("page_b",page + 1);
        return;
    }

    @PostMapping("/api/addcart")
    public Map<String, String> cartAjax(@RequestBody Cartadd cartadd,HttpServletRequest req) {
        Map<String, String> ret = new HashMap<String, String>();
        if(req.getSession().getAttribute("user") == null){
            ret.put("res","fail");
        }
        else{
            int id = userrepo.findByLoginId(req.getSession().getAttribute("user").toString()).getId();
            if(cartrepo.findByUserIdAndBookId(id,cartadd.getBookid())==null){
                CartEntity t = new CartEntity();
                t.setBookId(cartadd.getBookid());
                System.out.println(cartadd.getBookid());
                t.setUserId(id);
                System.out.println(id);
                t.setCount(cartadd.getCount());
                cartrepo.save(t);
            }
            else{
                CartEntity t = cartrepo.findByUserIdAndBookId(id,cartadd.getBookid());
                int count = t.getCount();
                count += cartadd.getCount();
                t.setCount(count);
                cartrepo.save(t);
            }
            ret.put("res","success");
        }
        return ret;
    }

    @PostMapping("/api/changecount")
    public Map<String, String> countAjax(@RequestBody @Valid Cartcount cartcount,BindingResult bindingResult,HttpServletRequest req) {
        Map<String, String> ret = new HashMap<String, String>();
        if (bindingResult.hasErrors()) {
            ret.put("res", "");
            return ret;
        }
        CartEntity t = cartrepo.findById(cartcount.getId());
        t.setCount(cartcount.getCount());
        System.out.println(cartcount.getCount());
        cartrepo.save(t);
        return ret;
    }


    @Autowired
    Userinforepo userinforepo;

    @PostMapping("/api/login_b")
    public Map<String, String> backAjax(@RequestBody @Valid Login login,BindingResult bindingResult,HttpServletRequest req) {
        Map<String, String> ret = new HashMap<String, String>();
        if (bindingResult.hasErrors()) {
            ret.put("res", "fail");
            return ret;
        }
        if(userinforepo.findByUserName(login.getUser()).getUserPass().equals(login.getPassword())){
            ret.put("res","success");
            req.getSession().setAttribute("userback",login.getUser());
        }
        else{
            ret.put("res","fail");
        }
        return ret;
    }

    @Autowired
    Userinforolerepo userinforolerepo;

    @PostMapping("/api/setrole")
    public Map<String, String> backAjax(@RequestBody Setrole setrole,HttpServletRequest req) {
        //System.out.println(setrole.getUserinfoid());
        //System.out.println("?????");
        Map<String, String> ret = new HashMap<String, String>();
        List<UserInfoRoleEntity> t = new ArrayList<UserInfoRoleEntity>();
        t = userinforolerepo.findAllByUserInfoId(setrole.getUserinfoid());
        for(UserInfoRoleEntity i:t){
            userinforolerepo.delete(i);
        }
        for(int i :setrole.getRoleid()){
            //System.out.println(i);

            UserInfoRoleEntity j = new UserInfoRoleEntity();
            j.setRoleId(i);
            j.setUserInfoId(setrole.getUserinfoid());

            userinforolerepo.save(j);
        }
        ret.put("res","success");
        return ret;
    }

    @Autowired
    Rolerepo rolerepo;

    @Autowired
    Caterepo caterepo;

    @Autowired
    Pubrepo pubrepo;

    @PostMapping("/api/delete")
    public Map<String, String> backAjax(@RequestBody Deleteid deleteid,HttpServletRequest req) {
        Map<String, String> ret = new HashMap<String, String>();
        if(deleteid.getWhat().equals("userinfo"))
        {
            for(int i :deleteid.getId()){
                System.out.println(i);
                userinforepo.delete(userinforepo.findById(i));
            }
            ret.put("res","success");
        }
        else if(deleteid.getWhat().equals("roleid")){
            for(int i :deleteid.getId()){
                rolerepo.delete(rolerepo.findById(i));
            }
            ret.put("res","success");
        }
        else if(deleteid.getWhat().equals("bookid")){
            for(int i :deleteid.getId()){
                bookrepo.delete(bookrepo.findById(i));
            }
            ret.put("res","success");
        }
        else if(deleteid.getWhat().equals("cateid")){
            for(int i :deleteid.getId()){
                caterepo.delete(caterepo.findById(i));
            }
            ret.put("res","success");
        }
        else if(deleteid.getWhat().equals("pubid")){
            for(int i :deleteid.getId()){
                pubrepo.delete(pubrepo.findById(i));
            }
            ret.put("res","success");
        }
        return ret;
    }

    @PostMapping("/api/infoadd")
    public Map<String, String> infoaddAjax(@RequestBody Infoadd infoadd,HttpServletRequest req) {
        Map<String, String> ret = new HashMap<String, String>();
        UserInfoEntity t = new UserInfoEntity();
        t.setEmail(infoadd.getInfoemail());
        t.setUserName(infoadd.getInfoname());
        t.setUserPass(infoadd.getInfopwd());
        t.setRegTime(new Timestamp( System.currentTimeMillis() ));
        userinforepo.save(t);
        ret.put("res","success");
        return ret;
    }


    @PostMapping("/api/roleadd")
    public Map<String, String> roleaddAjax(@RequestBody Roleadd roleadd,HttpServletRequest req) {
        Map<String, String> ret = new HashMap<String, String>();
        RoleEntity t = new RoleEntity();
        t.setRoleName(roleadd.getRolename());
        t.setDelFlag(roleadd.getRoleflag());
        t.setRoleType(roleadd.getRoletype());
        t.setRemark(roleadd.getRoleremark());
        t.setSubTime(new Timestamp( System.currentTimeMillis()));
        rolerepo.save(t);
        ret.put("res","success");
        return ret;
    }
    @PostMapping("/api/bookadd")
    public Map<String, String> bookaddAjax(@RequestBody Bookadd bookadd,HttpServletRequest req) throws Exception{
        Map<String, String> ret = new HashMap<String, String>();
        BooksEntity t = new BooksEntity();
        t.setAurhorDescription(bookadd.getBookaucontent());
        t.setAuthor(bookadd.getBookauthor());
        t.setCategoryId(bookadd.getBookcate());
        t.setClicks(0);
        t.setContentDescription(bookadd.getBookcontent());
        t.setEditorComment(bookadd.getBookedit());
        t.setIsbn(bookadd.getBookisbn());
        t.setPublisherId(bookadd.getBookpub());
        t.setUnitPrice(bookadd.getBookprice());
        t.setPublishDate(Timestamp.valueOf(bookadd.getBooktime()));
        t.setTitle(bookadd.getBooktitle());
        t.setToc(bookadd.getBooktoc());
        bookrepo.save(t);
        ret.put("res","success");
        return ret;
    }

    @PostMapping("/api/cateadd")
    public Map<String, String> cateaddAjax(@RequestBody Cateadd cateadd,HttpServletRequest req) throws Exception {
        Map<String, String> ret = new HashMap<String, String>();
        CategoriesEntity t = new CategoriesEntity();
        t.setName(cateadd.getCate());
        caterepo.save(t);
        ret.put("res","success");
        return ret;
    }
    @PostMapping("/api/pubadd")
    public Map<String, String> pubaddAjax(@RequestBody Cateadd cateadd,HttpServletRequest req) throws Exception {
        Map<String, String> ret = new HashMap<String, String>();
        PublishersEntity t = new PublishersEntity();
        t.setName(cateadd.getCate());
        pubrepo.save(t);
        ret.put("res","success");
        return ret;
    }


    @PostMapping("/api/uploadpic")
    public String upload(String isbn, MultipartFile bookpic,HttpServletRequest request) {

        String fileName = isbn+".png";
        String filePath = request.getServletContext().getRealPath("imgupload/");
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try{


        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(bookpic.getBytes());
        out.flush();
        out.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return "success";
    }

    @Autowired
    Orderbookrepo orderbookrepo;

    @Autowired
    Ordersrepo ordersrepo;

    @PostMapping("/api/addorder")
    public Map<String, String> orderaddAjax(@RequestBody Cateadd cateadd,HttpServletRequest req) throws Exception {
        Map<String, String> ret = new HashMap<String, String>();
        String address = cateadd.getCate();
        String id = UUID.randomUUID().toString();
        int userid = userrepo.findByLoginId(req.getSession().getAttribute("user").toString()).getId();
        List <CartEntity> t = cartrepo.findAllByUserId(userid);
        int count = 0;


//        for(CartEntity i :t){
//            OrderBookEntity s = new OrderBookEntity();
//            s.setBookId(i.getBookId());
//            s.setOrderId(id);
//            s.setUnitPrice(BigDecimal.valueOf(bookrepo.findById(i.getBookId()).getUnitPrice()));
//            s.setQuantity(i.getCount());
//            orderbookrepo.save(s);
//            count += i.getCount() * bookrepo.findById(i.getBookId()).getUnitPrice();
//
//        }
        for(CartEntity i :t){
            count += i.getCount() * bookrepo.findById(i.getBookId()).getUnitPrice();
        }
        OrdersEntity o = new OrdersEntity();
        o.setOrderDate(new Timestamp( System.currentTimeMillis()));
        o.setOrderId(id);
        o.setPostAddress(address);
        o.setUserId(userid);
        o.setState(0);
        o.setTotalPrice(BigDecimal.valueOf(count));
        ordersrepo.save(o);
        for(CartEntity i :t){
            OrderBookEntity s = new OrderBookEntity();
            s.setBookId(i.getBookId());
            s.setOrderId(id);
            s.setUnitPrice(BigDecimal.valueOf(bookrepo.findById(i.getBookId()).getUnitPrice()));
            s.setQuantity(i.getCount());
            orderbookrepo.save(s);
            cartrepo.delete(i);
        }
        ret.put("res","success");
        return ret;

    }



}

