package todo.dto;

import lombok.*;

// VO == DataBase 까지는 가지 않음(읽기 전용) ex) email 인증번호
// DTO == DataBase 와 연동해서 값을 사용
// Entity == JPA DataBase Oracle 에 테이블을 만들지 않아도, 알아서 테이블을 만들어주고, 칼럼을 지정해주고, 칼럼 값을 설정해준다.

@Getter
@Setter
@ToString
public class Todo {

	private int todoNo; // 할 일의 번호
	private String title; // 할 일의 제목(내용)
	private String isDone; // 할 일을 완료했는지, 완료 여부
	private int todoMemberNo; // 어떤 사용자의 할 일인지, 사용자의 번호 연동
}
