package com.kh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.dto.User;
import com.kh.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping // api 주소 값. 
 // @GetMapping 에 따로 주소 값을 지정해주지 않으면, 
 // @RequestMapping 값으로 자동 지정된다.
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@PostMapping
	public void insertUser(@RequestBody User user) { // @RequestBody == 전체
		userService.insertUser(user);
	}
	
	// await axios.delete(`/users`, {params: {id}});
	@DeleteMapping // api 삭제를 진행하기 위해 만나는 주소(api) 가 users
	public void deleteUser(@RequestParam(name = "id") int id) { // @RequestParam == 각각
		// name = ◀ 생략 가능
		// id 라고만 사용해서 기존에 이미 존재하는 다른 값들과 충돌이 날 수 있다.
		// 따라서, ("id") 로 정의해주어야 자동으로 인식한다.
		userService.deleteUser(id);
	}
	
	   // await axios.delete(`/users/${id}`);
	// @DeleteMapping("/{id}") // api 삭제를 진행하기 위해 만나는 주소(api) 가 users/유저id
	// public void deleteUser(@PathVariable("id") int id) { // @RequestParam == 각각
		// userService.deleteUser(id);
	// }
	
	@PutMapping
	public void updateUser(@RequestBody User user) {
		userService.updateUser(user);
	}
}
