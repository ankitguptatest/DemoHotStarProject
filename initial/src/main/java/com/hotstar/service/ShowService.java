package com.hotstar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotstar.dao.ShowRepository;

@Service
public class ShowService {

	@Autowired
	private ShowRepository showRepository;

	public List<String> findAssociatedTrays(String name) {
		return showRepository.getTraysContainingShow(name);
	}
}
