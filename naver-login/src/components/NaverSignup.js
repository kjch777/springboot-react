import React, {useContext, useEffect, useState} from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom'; // 버튼 클릭 없이 위치 설정
import { AuthContext } from './layout/AuthContext';
/* 
useLocation: URL 의 정보를 포함한 객체이다.
             경로, hash, 문자열 값 등을 가지고 온 객체이다.
*/

export function NaverSignup() {
    const [userInfo, setUserInfo] = useState(null);
    /***** 2024-08-12 비밀번호 값 설정 추가 *****/
    const [password, setPassword] = useState(''); // 비밀번호 상태 추가
    const location = useLocation();
    const [loading, setLoading] = useState(true);

    const {loginMember} = useContext(AuthContext);

    // 어떠한 동작(클릭 등) 이 없어도, UserInfo 페이지에 들어오면 자동으로 실행되는 효과
    useEffect(() => {
        // URLSearchParams: URL 에서 ? 뒤에 붙어있는 Key - Value 값을 가져온다.
        // String redirectUrl = "http://localhost:3000/userinfo?access_token=" + accessToken;
        // userinfo? 뒤에 붙어있는 access_token 에 있는 데이터를 포함한다.
        const a = new URLSearchParams(location.search);
        const accessToken = a.get('access_token');
        console.log("토큰 확인: " + accessToken);
        // URLSearchParams 로 가져온 수 많은 값 중에서, Key 명칭이 access_token 인 값만 가져오겠다는 의미의 코드이다.

        // get 을 이용하여 userinfo 정보 가져오기
        // Java 에서는 userinfo?access_token= 뒤에 + 를 붙여 변수를 사용했지만,
        // JS 에서는 `` 을 사용해서 const accessToken = a.get('access_token');

        // 만약, accessToken 값이 존재하면 axios 를 발동
        if(accessToken){
        axios.get(`/signup/naver?access_token=${accessToken}`)
        .then(response => { // .then((res) => { ◀ (res) 형식으로 ( ) 를 사용하여 res 를 막아버리면, => 이후로는 res 가 선언되지 않은,
            // 즉, 지역변수명이 되기 때문에 res 를 찾을 수 없게 된다.
            setUserInfo(response.data);
            setLoading(false);
        })
        .catch((err) => {
            alert(err, "정보를 정상적으로 가져오지 못했습니다.");
        })
    }

    }, [location.search]); // location.search 로 검색된 Key - Value 값 중, access_token(= 특정 값) 값을 가져오면 useEffect 를 사용하겠다는 의미의 코드이다.

    if(loading) {
        return <div>데이터 정보 가져오는 중...</div>
    }

    // 회원가입 기능 만들기 ◀ React 에서 Java 로 데이터 전달하기
    // /naverAPI/register ◀ Java Controller 에서 지정해둔 주소에서 만나 데이터를 주고 받을 것
    const handleJoinMember = () => {
        // 만약, 비밀번호 입력 창이 비어있다면
        if(!password) { // !password ◀ 비밀번호가 없다는 뜻이다.
            alert("비밀번호를 입력해주세요.");
            return;
        }

        // 데이터를 특정 장소에 전달하러 가기
        // axios.post('어디서 만날 것인지 특정 위치 설정', {주고 받을 데이터를 설정})
        // axios.post('백엔드주소/naverAPI/register', {})
        axios.post('http://localhost:9010/naverAPI/register', {
            id: userInfo.response.id,
            email: userInfo.response.email,
            nickname: userInfo.response.nickname,
            name: userInfo.response.name,
            gender: userInfo.response.gender,
            
            profileImage: userInfo.response.profile_image,
            
            password: password
        })
        .then(response => {
            console.log(response.data); // 개발자가 값이 DB 에 정상적으로 들어갔는지 확인하는 코드
            alert("회원가입이 완료되었습니다."); // client 가 회원가입이 정상적으로 완료됐는지 확인하는 코드
        })
        .catch(e => {
            console.log("개발자가 에러를 확인하는 용도: ", e);
            alert("회원가입에 실패하였습니다.");
        })
    }

    return (
        <>
            <h1>회원 정보</h1>
            {userInfo ? 
            (<div>
                <input type='text' value={userInfo.response.id} disabled />
                <input type='email' value={userInfo.response.email} disabled />
                <input type='text' value={userInfo.response.nickname} disabled />
                <input type='text' value={userInfo.response.name} disabled />
                <input type='text' value={userInfo.response.gender} disabled />
                <img src={userInfo.response.profile_image} disabled style={{height: '250px', width: '250px'}} />
                {/* 네이버에서 가져온 id 값을 input 에 넣어주고, 수정하지 못하게 막음 처리해주는 코드*/}
            </div>) : 
            (<p>회원을 찾을 수 없습니다.</p>)}

            <div>
                <h2>회원가입에 필요한 비밀번호 작성하기</h2>
                <input type='password' value={password} onChange={(e) => setPassword(e.target.value)} />
                {/*
                    <input type='password' value={password} onChange={changePW} />
                    const changePW = (e) => {setPassword(e.target.value)}
                */}
                <button onClick={handleJoinMember}>회원가입 하기</button>
            </div>
        </>
    )
}