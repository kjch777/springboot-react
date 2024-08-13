import React, { useState } from "react";
import { AuthContext } from "./AuthContext"; // provider 가 감싸고 있는 곳에 전파할 내용

// Provider 가 감싸고 있는 모든 js 에 AuthContext 에 저장된 값이 적용될 수 있도록 감싸는 js 이다.
// 옛날 방식
const AuthProvider = ({children}) => {

    return (
        <AuthContext.Provider value={{loginMember, setLoginMember}}>
            {children}
        </AuthContext.Provider>
    )
}

// export default AuthProvider;
// export 를 해주지 않으면 외부 js 에서 쓸 수 없다.
// export ◀ 외부 js 에서 쓸 수 있도록 내보내는 것