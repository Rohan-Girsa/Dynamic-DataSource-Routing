package com.demo.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.entity.User;
import com.demo.repository.UserRepository;
import com.demo.utility.DataSourceContextHolder;
import com.demo.utility.DataSourceEnum;

@RestController
public class UserController {

	private UserRepository userRepository;
	private final DataSourceContextHolder dataSourceContextHolder;

	public UserController(UserRepository userRepository, DataSourceContextHolder dataSourceContextHolder) {
		this.userRepository = userRepository;
		this.dataSourceContextHolder = dataSourceContextHolder;
	}

	@GetMapping("/get-all-users")
	public Iterable<User> getAllUsers(@RequestParam("db") String db) {
		if (DataSourceEnum.dependo.toString().equals(db)) {
			dataSourceContextHolder.setBranchContext(DataSourceEnum.dependo);
		}
		if (DataSourceEnum.m2w.toString().equals(db)) {
			dataSourceContextHolder.setBranchContext(DataSourceEnum.m2w);
		}
		if (DataSourceEnum.atpl.toString().equals(db)) {
			dataSourceContextHolder.setBranchContext(DataSourceEnum.atpl);
		}

		return userRepository.findAll();
	}

	@GetMapping("/get-single-user/{id}")
	public User getSingleUser(@RequestParam("db") String db, @PathVariable("id") Integer id) {
		if (DataSourceEnum.dependo.toString().equals(db)) {
			dataSourceContextHolder.setBranchContext(DataSourceEnum.dependo);
		}
		if (DataSourceEnum.m2w.toString().equals(db)) {
			dataSourceContextHolder.setBranchContext(DataSourceEnum.m2w);
		}
		if (DataSourceEnum.atpl.toString().equals(db)) {
			dataSourceContextHolder.setBranchContext(DataSourceEnum.atpl);
		}
		Optional<User> userOptional = userRepository.findById(id);
		return userOptional.get();
	}

	@GetMapping("/update-user-name/{id}")
	public String getSingleUserByName(@PathVariable("id") Integer id, @RequestParam("db") String db,
			@RequestParam("name") String name) {
		if (DataSourceEnum.dependo.toString().equals(db)) {
			dataSourceContextHolder.setBranchContext(DataSourceEnum.dependo);
		}
		if (DataSourceEnum.m2w.toString().equals(db)) {
			dataSourceContextHolder.setBranchContext(DataSourceEnum.m2w);
		}
		if (DataSourceEnum.atpl.toString().equals(db)) {
			dataSourceContextHolder.setBranchContext(DataSourceEnum.atpl);
		}
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			return "Not Found";
		}
		User user = userOptional.get();
		user.setName(name);
		userRepository.save(user);
		return "Successfully Updated Name";
	}

}
