import React, { createContext } from "react";

// 로그인 한 멤버 정보를 저장하는 Context
// 새로고침을 하더라도 로그인 한 정보가 풀리지 않는다.
export const AuthContext = createContext({
    loginMember: null, // 로그인 한 멤버 정보를 저장 할 변수
    setLoginMember: () => {} // 로그인 한 멤버 정보를 업데이트 할 변수 
    // setLoginMember ▶ loginMember 로 전달하는 것
})