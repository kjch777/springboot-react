import logo from './logo.svg';
import './App.css';
import { useState } from 'react';

function App() {
  
  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  
  const handleJoin = () => {
    // fetch & formData
    // formData 는 multipart 가 기본이다.
    const formData = new FormData();
    
    formData.append('username', username);
    formData.append('password', password);
    formData.append('email', email);

    const user = {username: username,
                  email: email,
                  password: password
                 };

    fetch('http://localhost:9012/api/register', {
      method: "POST",
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user)
      // body: formData ◀ 이미지를 보낼 수 있는 multipart 가 기본이다.
      // body: {'username': username,
      //        'email': email,
      //        'password': password
      //       }
    })
    .then(response => response.text())
    // 데이터가 정상적으로 저장됐다고 client 에게 알려줌
    .then(data => {
      alert('회원가입이 완료됐습니다.');
    })
    // 데이터 저장 실패 client 에게 알림
    .catch(error => {
      alert('회원가입 실패');
    })
  }

  return (
    <div className="App">
      <h2>회원 가입</h2>
      <label>닉네임: </label>
      <input type='text' placeholder='닉네임 입력' value={username} onChange={(e) => setUserName(e.target.value)} />
      
      <label>비밀번호: </label>
      <input type='password' placeholder='비밀번호 입력' value={password} onChange={(e) => setPassword(e.target.value)} />
      
      <label>이메일: </label>
      <input type='email' placeholder='이메일 입력' value={email} onChange={(e) => setEmail(e.target.value)} />

      <button onClick={handleJoin}>가입하기</button>
    </div>
  );
}

export default App;
