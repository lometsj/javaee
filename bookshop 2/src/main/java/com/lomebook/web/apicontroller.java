package com.lomebook.web;

import com.lomebook.enti.Code;
import com.lomebook.enti.Login;
import com.lomebook.enti.UsersEntity;
import com.lomebook.enti.Register;
import com.lomebook.repo.Userrepo;
import com.lomebook.tools.Acti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class apicontroller {


    @Autowired
    private Userrepo userrepo;

    private Acti acti = new Acti();

    @PostMapping("/api/register")
    public Map<String, String> registerAjax(@RequestBody @Valid Register register, BindingResult bindingResult, HttpServletResponse response) {
        //System.out.println("get in ajax api");
        Map<String, String> ret = new HashMap<String, String>();
        //bindingResult.getAllErrors().stream().
        String t = bindingResult.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));
        if (bindingResult.hasErrors()) {
            ret.put("res", t);
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
            //Acti acti = new Acti();
            acti.sendEmail(register.getEmail());
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

    @PostMapping("/api/acti")
    public Map<String ,String> actiAjax(@RequestBody @Valid Code code, BindingResult bindingResult,HttpServletRequest req){
        Map<String, String> ret = new HashMap<String, String>();
        String t = bindingResult.getAllErrors().stream().map(x -> x.getDefaultMessage()).collect(Collectors.joining(","));
        if (bindingResult.hasErrors()) {
            ret.put("res", t);
            return ret;
        }
        if(acti.checkCode(code.getUser(),code.getActicode())){
            req.getSession().setAttribute("user",userrepo.findByMail(code.getUser()).getLoginId());
            userrepo.findByMail((code.getUser())).setUserStateId(1);
            ret.put("res","success");

        }
        else{
            ret.put("res","acti code error");
        }
        return ret;
    }

}

