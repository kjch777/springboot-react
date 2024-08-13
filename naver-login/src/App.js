import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import { NaverSignup } from './components/NaverSignup';
import { Header } from './components/layout/Header';
import { useEffect, useState } from 'react';
import { AuthContext } from './components/layout/AuthContext';
import { NaverAPI } from './components/NaverAPI';
import { Login } from './components/Login';

// html 파일이 1개밖에 없는 React 에서는,
// Router 를 사용하여 각 js 파일의 경로를 설정해주어야 한다.
// BrowserRouter as Router ◀ Web 의 전체적인 경로
// Switch → Routes ◀ 경로들
// Route ◀ 경로
function App() {
  
  // 로그인 정보를 받고, 전달해주기
  const [loginMember, setLoginMember] = useState(null);

  // 만약 로그인 한 정보가 있다면, localStorage 에 저장하기
  useEffect(() => {
    if(loginMember) {
      localStorage.setItem("loginMember", JSON.stringify(loginMember));
    }
    // ▼ 로그인 한 멤버가 변경될 때마다 새로 저장하기
  }, [loginMember]);

  // 로그인이 저장된 정보 전달하기
  useEffect(() => {
    const savedMember = localStorage.getItem("loginMember");

    // 만약 loginMember 변수에 저장된 회원정보가 존재한다면, setLoginMember 에 넣어주겠다는 설정해주기
    if(savedMember) {
      setLoginMember(JSON.parse(savedMember)); // 데이터 손실 가능성이 있기 때문에, JSON 형식으로 한번 더 parse(변환) 해주는 것이다.
    }
  }, []);

  return (
    <AuthContext.Provider value={{loginMember, setLoginMember}}>
      <Router>
        <Header />
        <Routes>
          <Route path='/login' element={<Login />} />
          <Route path='/api/naver' element={<NaverAPI />} />
          <Route path='/signup/naver' element={<NaverSignup />} />
        </Routes>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
