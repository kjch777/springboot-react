import React, { useState } from "react";
import "./App.css";
// import { LoginContext as LoginContext } from "./components/LoginContext";
import { SignUp as SignUp } from "./components/SignUp";
import { Login as Login } from "./components/Login";
import { TodoList as TodoList } from "./components/TodoList";
import { TodoListContext as TodoListContext } from "./components/TodoListContext";

/* App 컴포넌트(최상위 컴포넌트)*/
function App() {
  /* 상태 변수(값이 변하면 컴포넌트 리렌더링)*/

  // 회원가입 창 보이기/숨기기
  const [signUpView, setSignUpView] = useState(false);

  // 로그인 한 회원 정보 저장
  const [loginMember, setLoginMember] = useState(null);

  // 로그인 한 회원의 할 일 목록 저장
  const [todoList, setTodoList] = useState([]);

  return (
    <TodoListContext.Provider value={{loginMember, setLoginMember, todoList, setTodoList}}>
      <button onClick={() => {setSignUpView(!signUpView)}}>
        {signUpView ? ("회원가입 닫기") : ("회원가입 열기")}
      </button>
      
      {/* 회원가입 화면*/}
      <div className="signup-wrapper">
        
        {/* SignUp.js 에서 import 한 컴포넌트*/}

        {/* signupView 가 true 인 경우에만 화면에 출력하기*/}

        {/* 조건식 && (true 인 경우 실행)*/}
        {signUpView === true && (<SignUp />)}
      </div>
      <h1>Todo List</h1>
      
      {/* 로그인 컴포넌트*/}
      <Login />
      <hr />
      {/* 로그인 되었을 때, 로그인 한 회원의 Todo List 출력하기*/}
      {loginMember && <TodoList />}
    </TodoListContext.Provider>
  );
}

export default App;
