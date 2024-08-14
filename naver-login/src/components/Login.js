import { useState } from 'react';
import '../css/Login.css';
import axios from 'axios';

export const Login = () => {

    const [id, setId] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const loginAxios = () => {
        axios.post('http://localhost:9010/login', null, {
            params: { id: id, password: password }
        })
        .then(response => {
            if(response.status === 200) { // 로그인 성공 시 200 주소가 보인다.
                setMessage("로그인 성공");
                
            }
        })
        .catch(e => {
            setMessage("로그인 시도 중 오류 발생");
        })
    }

    const loginFetch = () => {
        fetch('http://localhost:9010/login', {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({id, password})
        })
        .then(response => {
            if(response.status === 200) {
                return response.json();
            }
        })
        .then(result => {
            setMessage(result); // Java 에서 로그인 성공/실패 에 대한 메시지를 그대로 사용하는 것
        })
        .catch(error => {
            setMessage("error", error);
        });
    }

    return (
        <div className='login-container'>
            <h3>로그인</h3>
            <div>
                <label>
                    아이디: 
                    <input type="text" placeholder="아이디를 입력하세요." value={id} onChange={(e) => setId(e.target.value)} />
                </label>
                <label>
                    비밀번호: 
                    <input type="password" placeholder="비밀번호를 입력하세요." value={password} onChange={(e) => setPassword(e.target.value)} />
                </label>
                <button onClick={loginAxios}>로그인 하기</button>
                {message && <p>{message}</p>}
                <div className='findSign-buttons'>
                    <button>아이디 찾기</button>
                    <button>비밀번호 찾기</button>
                    <button>회원가입 하기</button>
                </div>
            </div>
            <label>
                SNS로 로그인 하기: 
                <img src="/naver_img/btnG_circleIcon.png" className='naverLogo-img' />
            </label>
            {/* 
                <button className='naverLogin-button'>
                    <img src="/naver_img/btnG_circleIcon.png" />
                    네이버로 회원가입 하기
                </button> 
            */}
        </div>
    )
}