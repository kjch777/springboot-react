import React, { useState, useContext } from "react";
import {TodoListContext as TodoListContext} from "./TodoListContext";

export const TodoList = () => {
  const { todoList, setTodoList, loginMember } = useContext(TodoListContext);

  const [ inputTodo, setInputTodo ] = useState("");

  let keyIndex = 0;

  // todoAddBtn 기능 설정
  const todoAddBtn = () => {
    // 만약 할 일이 입력되지 않았다면, 입력하라는 알림창 띄우기
    if (inputTodo.trim().length === 0) {
      // .trim() == 맨 앞, 맨 뒤 공백 제거
      alert("해야 할 일을 입력하세요.");
      return;
    }

    fetch("/todo", {
      // TodoController 에서, /todo 라는 URL 주소에서, DB 에 값 추가하기
      method: "post",

      // headers 에서 Content-Type 은, 
      // Controller 로 값을 전달할 때, 
      // 이 값이 이미지, 동영상, 글자 등 어떤 파일인지 전달하는 공간이다.
      headers: { "Content-Type": "application/json" },

      // JSON 으로 된 파일을 글자로 변경하여, 글자로 취급해 사용하는 것이다.
      body: JSON.stringify({
        title: inputTodo,
        todoMemberNo: loginMember.todoMemberNo,
      })
    })
      .then((response) => response.text())
      .then((todoNo) => {
        if (Number(todoNo) === 0) {
          // 실패 시 멈춤
          return;
        }
        /* 기존 todoList + 새로 추가된 Todo 를 이용해 새 배열을 만들어 todoList 대입*/
        const newTodo = {
          todoNo: todoNo,
          title: inputTodo,
          isDone: "Y",
          todoMemberNo: loginMember.todoMemberNo,
        };
        // 기존 todoList + newTodo 를 이용해 새로운 할 일 만들기
        // 기존에 있던 할 일 목록과 새로 등록할 할 일 목록 합치기
        const newTodoList = [...todoList, newTodo];

        // todoList 에 대입하기
        setTodoList(newTodoList);
        setInputTodo("");
        // 문제가 생기면 문제 console 창에서 보여주기
      })

      .catch((e) => console.log(e));
  };

  // O, X 업데이트
  // 할 일을 완료했다면 X 버튼 표시, 할 일을 완료하지 못했다면 O 버튼 표시
  // 할 일을 처음부터 끝까지 모두 완료 또는 미완료 처리하는 것이 아니라,
  // 특정 할 일과 그 할 일의 번호를 받아, 특정 할 일만 수정 처리하는 것이다.
  const todoUpdateBtn = (todo, index) => {
    console.log("todoCheck: ", todo);
    console.log("indexCheck: ", index);

    fetch("/todo", {
      method: "put", // Controller 에서 @PutMapping 으로 작성한다고 했기 때문에 put 을 쓴 것이다.
      headers: {'Content-Type' : 'application/json'},
      body: JSON.stringify({
        todoNo: todo.todoNo,
        isDone: todo.isDone === 'Y' ? 'N' : 'Y'}) /* 삼항연산자 조건이 ? true 일 때 실행할 구문 : false 일 때 실행할 구문
                                                                    todo.isDone === 'O' ? 'X'
                                                     만약 할 일 완료 여부에 O 로 표시되어 있다면, X 로 글자 변경하기
                                                                    todo.isDone === 'O' ? todo.isDone 이 X 라면 ◀ 을 뜻하는 표기이다.
                                                                    todo.isDone === 'O' ? 'O'
                                                     만약 할 일 완료 여부에 X 로 표시되어 있다면, O 로 글자 변경하기*/
    })
    .then(response => response.text())
    .then(result => {
      // 응답에 대한 결과가 없다면, 수정 실패 알림창 띄워주기
      if(result === '0') {
        alert("할 일 수정에 실패했습니다.");
        return;
      }
      // 수정 성공 시 todoList 값을 변경하고 새로고침 하기

      // 기존 할 일 목록(todoList) 을 복사해서 새로 추가된 할 일을 더한 다음,
      // 새로운 할 일 목록으로 업데이트 하기
      const newTodoList = [...todoList];

      // index 번호의 태그 값을 O 또는 X 로 변경하기
      newTodoList[index].isDone = newTodoList[index].isDone === 'Y' ? 'N' : 'Y';

      setTodoList(newTodoList);
    })
    .catch(e => console.log(e));
  };

  /* 삭제하고 싶은 할 일의 번호를 가지고 삭제하기*/
  const todoDeleteBtn = (todoNo, index) => {
    fetch("/todo", {
      method: 'delete',
      headers: {'Content-Type' : 'application/json'},
      body: todoNo
    })
    .then(response => response.text()) // 응답 결과를 글자 형식으로 가져오겠다는 코드이다.
    .then(result => {

      if(result === '0') {
        alert("삭제에 실패하였습니다.");
        return;
      }

      const newTodoList = [...todoList]; // 배열 복사

      // 배열.splice(index, 몇 칸)
      // ▶ 배열의 index 몇번째 태그부터 몇 칸을 잘라내어 변환할 지 지정하는 것이다.
      // 배열에서 잘라진 부분이 사라진다.
      newTodoList.splice(index, 1); // (내가 선택한 번호, 한 칸만 삭제)
      /* 
        newTodoList.                   splice            (index, 1);
      새로운할일목록.괄호안에작성한부분을제외하고,목록새로작성(index, 1);*/
      
      setTodoList(newTodoList); // 새로 작성한 목록으로 기존 목록 대체하기
    })
    // 삭제가 정상적으로 되지 않을 때, 왜 문제가 생겼는지 개발자용 console 창에서만 보여주기
    .catch(e => console.log(e));
  };

  return (
    <div>
      <h1>{loginMember.name}의 할 일 목록 보기</h1>
      <div className="todo-container">
        <h3>할 일 입력하기</h3>
        <div>
          <input
            type="text"
            value={inputTodo}
            onChange={(e) => setInputTodo(e.target.value)}
          />
          <button onClick={todoAddBtn}>할 일 추가하기</button>
        </div>
        <ul>
          {/* 배열.map: 기존 배열을 이용하여, 새로운 배열 생성하기*/}
          {todoList.map((todo, index) => (
            <li key={keyIndex++}>
              <div>
                <span className={todo.isDone === "N" ? "todo-complete" : ""}>
                  {todo.title}
                </span>
                <span>
                  <button
                    onClick={() => {
                      todoUpdateBtn(todo, index);
                    }}
                  >
                    {todo.isDone}
                  </button>
                  <button
                    onClick={() => {
                      todoDeleteBtn(todo.todoNo, index);
                    }}
                  >
                    삭제하기
                  </button>
                </span>
              </div>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};
