package com.woody.fine.controller.test;

import com.woody.fine.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class Test {

    @Autowired
    private TestService bookService;

    @RequestMapping("/book")
    @ResponseBody
    public String queryBookPrice(String name) {

        return bookService.queryBookpriceByName(name);
    }

    @RequestMapping("/info")
    @ResponseBody
    public void bookInfo() {
        System.out.println("Hi");
        System.out.println("Hi");
    }

    @RequestMapping("/bookPage")
    public ModelAndView queryBookPage(String name) {

        String price = bookService.queryBookpriceByName(name);

        ModelAndView md = new ModelAndView();
        md.addObject("bookPirce", price);
        md.addObject("bookName", name);
        md.setViewName("book");

        return md;
    }

}
