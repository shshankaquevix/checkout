package com.ftd.cart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftd.cart.vo.AddToCartRequest;

@Controller
public class HomePageController {

	/*@RequestMapping("/index")
	public String index(){
		return "homepage";
	}*/ 
	
	@RequestMapping("/cart/index")
    public String index(@ModelAttribute AddToCartRequest addToCartRequest) {
        return "homepage";
    }
}
