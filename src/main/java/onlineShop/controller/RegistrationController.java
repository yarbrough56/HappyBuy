package onlineShop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import onlineShop.model.Customer;
import onlineShop.service.CustomerService;

@Controller
public class RegistrationController {
    
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/customer/registration", method = RequestMethod.GET)
	public ModelAndView getRegistrationForm() {
		//第一个是页面名字； modelAtrribute（在jsp页面里）--》对应着表单里 model package下面的customer的class --》 new 一个 customer class
    	return new ModelAndView("register", "customer", new Customer());
    							
	}

	@RequestMapping(value = "/customer/registration", method = RequestMethod.POST)
	// 前端的表单属性自动对应model里class的值，把注册的信息发过去
	public ModelAndView registerCustomer(@Valid @ModelAttribute(value = "customer") Customer customer,
													
			
        	BindingResult result) {
    	ModelAndView modelAndView = new ModelAndView();
   	 
    	if (result.hasErrors()) {
    			// 看表单是不是合法
        	modelAndView.setViewName("register");
        	return modelAndView;
    	}
    	customerService.addCustomer(customer);
   	 
    	modelAndView.addObject("registrationSuccess", "Registered Successfully. Login using username and password");
    	modelAndView.setViewName("login");
    	return modelAndView;
	}
}
