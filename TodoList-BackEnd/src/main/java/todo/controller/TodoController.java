package todo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import todo.dto.Todo;
import todo.dto.TodoMember;
import todo.service.TodoService;

@RestController
public class TodoController {

	@Autowired
	private TodoService service;
	
	/**
	 * 아이디 중복 검사
	 * @Param id
	 * @Return 중복일 때는 1, 사용 가능할 때는 0
	 * 중복 == select COUNT 했을 때, 만약 사용하고자 하는 아이디가 존재하면 COUNT 값이 1,
	 * 사용하고자 하는 아이디가 DB 에 존재하지 않으면 0
	 * **/
	
	@GetMapping("/idCheck")
	public int idCheck(@RequestParam("id") String id) {
		return service.idCheck(id);
	}
	
	/**
	 * 회원가입
	 * @Param member
	 * @return 성공: 1, 실패: 0
	 * **/
	@PostMapping("/signup")
	public int signup(@RequestBody TodoMember member) {
		return service.signup(member);
	}
	
	/**
	 * 로그인
	 * @Param member
	 * @return 성공: 회원정보/todoList, 실패: null
	 * **/
	@PostMapping("/login")
	public Map<String, Object> login(@RequestBody TodoMember member) {
		return service.login(member);
	}
	
	/**
	 * 할 일 추가
	 * @Param todo
	 * @return 성공: 1, 실패: 0
	 * **/
	@PostMapping("/todo")
	public int insert(@RequestBody Todo todo) {
		return service.insert(todo);
	}
	
	/**
	 * 할 일 수정
	 * @Param todo
	 * @return 성공: 1, 실패: 0
	 * update 와 같이 수정할 때는 ▶ @PutMapping 을 사용한다.(@PostMapping 을 사용해도 상관없다.)
	 * **/
	@PutMapping("/todo")
	public int update(@RequestBody Todo todo) {
		return service.update(todo);
	}
	
	/**
	 * 할 일 삭제
	 * @Param todoNo
	 * @return 성공: 1, 실패: 0
	 * delete 와 같이 삭제할 때는 ▶ @DeleteMapping 을 사용한다.(@PostMapping 을 사용해도 상관없다.)
	 * **/
	@DeleteMapping("/todo")
	public int delete(@RequestBody int todoNo) {
		return service.delete(todoNo);
	}
	
	/**
	 * RestAPI 테스트
	 * @return 100
	 * **/
	@GetMapping("/test")
	public int test() {
		return 100;
	}
	
	/**
	 * CRUD: DataBase 에서, 데이터 조작의 기본적인 4가지 작업을 가리킨다.
	 * Create: 새로운 데이터를 생성하는 것 Insert
	 * Read: 데이터 읽기, 조회 Select
	 * Update: 데이터 수정
	 * Delete: 데이터 삭제
	 * Insert Select Update Delete: DataBase 에서 DML 이라고 부르는 용어이다.
	 * 
	 * HTTP 메서드(웹 주소에서 사용되는 기능 명칭): GET POST PUT DELETE
	 * HTTP 메서드(GET POST PUT DELETE): http(인터넷 == 웹) 에서, 사용자(클라이언트) 가
	 * 서버에 회원가입이나 로그인과 같은 요청을 보낼 때 사용하는 기능 명칭이다.
	 * http 메서드는 CRUD 작업과 연결되어 사용한다.
	 * 
	 * GET: 서버로부터 데이터를 조회하기 위한 요청을 나타낸다.
	 * CRUD 에서는 Read
	 * GET /users == 모든 사용자 목록을 조회하는 주소이다.
	 * 
	 * POST: 사용자(클라이언트) 가 서버에 새로운 데이터를 생성해달라는 요청을 보낼 때 사용한다.
	 * CRUD 에서는 Create
	 * POST /user == 새로운 사용자를 생성하겠다는 의미이다. body 에 사용자의 정보가 포함되어, DB 에 전송되는 것이다.
	 * 
	 * PUT: 사용자(클라이언트) 가 서버에 이미 존재하는 데이터를, 본인의 취지에 맞게 수정해달라는 요청을 보낼 때 사용한다.
	 * CRUD 에서는 Update
	 * PUT /mypage == 기존에 이미 존재하는 사용자가, 자신의 정보를 수정해달라고 서버에 요청, DB 가 수정된다.
	 * 
	 * DELETE: 사용자(클라이언트) 가, 서버에 이미 존재하는 데이터를 삭제해달라고 요청할 때 사용한다.
	 * CRUD 에서는 Delete
	 * DELETE /user/1 == 회원번호가 1인 사용자를 삭제하는 것이다.
	 * **/
}
