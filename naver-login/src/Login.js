import React, { useState } from "react";

export const Login = () => {
    const [userInfo, setUserinfo] = useState(null);

    return (
        <>
            {/* 만약 userInfo 정보가 있다면 로그인 된 상태, userInfo 정보가 없다면 네이버 로그인 창 보여주기*/}

            {/* 삼항 연산자 이용하기*/}
            {/* userInfo ? ('있을 때 보여줄 코드') : ('없을 때 보여줄 코드')*/}
            {userInfo ? (<div>
                <h1>로그인 된 상태입니다.</h1>
                <div>{JSON.stringify(userInfo, null, 2)}</div>
            </div>) : (<a href="http://localhost:9010/naverLogin">
                네이버 로그인
            </a>)}
            
            {/* userInfo 정보가 없을 때
                <a href="http://localhost:9010/naverLogin">
                    네이버 로그인
                </a>
            userInfo 정보가 있을 때
            <div>
                <h1>로그인 된 상태입니다.</h1>
                <div>{JSON.stringify(userInfo, null, 2)}</div>
            </div> */}
        </>
    )
}

/* 
백엔드에서 가져온 값.을 문자열로 처리하는 것(네이버에서 가져온 값, 특정 값을 특정하게 변환, 들여쓰기)
{             JSON.            stringify(userInfo,            null,                  2)}
JSON.stringify: 자바 백엔드에서 가져온 값을, 문자열로 변환해주는 것이다.
                자바에서 가져오는 데이터가 숫자인지 문자인지 알 수 없는 상태이기 때문에,
                안전하게 한번 더 문자열로 가져오겠다는 설정을 해주는 것이다.
                (userInfo 값으로 어떤 형식의 데이터가 들어올지 모르기 때문이다.)

userInfo: 회원정보

null: 가져온 값에서 특정 값을 변경하거나, 필터링 할 것인지를 설정하는 공간이다.
     (변경하지 않을 것이라면 null)

2: 들여쓰기 할 칸 수를 설정하는 공간이다.

======================================

{             JSON.            stringify(회원정보,            사용예제,                  2)}

const 회원정보 = {
    ▼ key : value
    
    id: "12345",
    name: "신짱구",
    phone: "010-9876-5432"
}

client 가 입력한 전화번호에서 - 만 제거하고 DB 에 저장하기

const 번호형식변경하기 = (key, value) => {
    // 만약 key 명칭이 phone 이라면
    if(key === 'phone') {
    return value.replace("-", ""); // ◀ "-" 을 제거하는 것. "-" 을 "" 로 치환하는 것이다.
    }
}

{JSON.stringify(회원정보, 번호형식변경하기, 2)}
번호형식변경하기 기능을 거친 회원정보는 아래와 같이 보여진다.

const 회원정보 = {
    ▼ key : value
    
    id: "12345",
    name: "신짱구",
    phone: "01098765432"
}

만약, 전화번호의 형식을 변경하지 않고, 가져온 회원정보를 그대로 사용할 경우
{JSON.stringify(회원정보, null, 2)}

null: 변경 사항 없음 처리
*/