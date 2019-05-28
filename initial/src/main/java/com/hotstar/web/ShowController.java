package com.hotstar.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotstar.service.ShowService;

@Controller
@RequestMapping(path = "/show")
public class ShowController {

	@Autowired
	private ShowService showService;

	@GetMapping(path = "/mappedTray")
	public @ResponseBody List<String> getAllTrays(@RequestParam String name) {
		return showService.findAssociatedTrays(name);
	}
}
