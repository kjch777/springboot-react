import './App.css';
import axios from 'axios';
import { useState, useEffect } from 'react';
import { UserTable } from './component/UserTable';
import { UserForm } from './component/UserForm';
// select insert component 추가 작성
function App() {
  const [users, setUsers] = useState([]); // 회원 목록이 담길 빈 배열 생성
  // useEffect 는, button 또는 특정 값을 클릭하지 않아도, 자동으로 실행된다.(최초 1회만 실행할지, 주기적으로 실행할지 정하는 것)
  // App.js 가 실행되면 적용할 효과로, 만약 특정 변수명이 없다면, 기능이 최초 1회만 실행하고, 이후에는 실행되지 않는다.
  // useEffect(() => {기능}, [          ]);
  // 특정 변수명이 존재한다면, 특정 변수명에 변화가 있을 때마다 기능이 실행된다.
  // useEffect(() => {기능}, [특정 변수명]);

  useEffect(() => {
    SeeAllUsers(); // 웹 사이트에 접속하면, 최초 1회만 모든 회원들이 보이고,
  }, []); // [] 로 배열이 비어있기 때문에, 웹 사이트에 접속했을 때, 딱 한번만 실행되는 것이다.

  // const SeeAllUsers = () => {
  //   // axios 를 사용하여, 모든 회원을 보겠다는 기능 설정해주기
  //   axios.get("/users") // Controller @RequestMapping("/users") 로 작성해두었기 때문에, 여기서도 /users 인 것이다.
    
  //   /***** 응답을 무사히 가져왔을 때 *****/
  //   .then(res => { // Java 에서 DB 값에 있는 내용을 가져와, 사용자(client) 에게 가져온 내용에 대한 응답을 알려주는 것이다.
  //     setUsers(res.data); // 응답 결과 Data 로 users 를 변경하겠다.
  //   })

  //   /***** 응답을 정상적으로 가져오지 못했을 때(문제가 생겼을 때) *****/
  //   .catch(err => {
  //     alert("에러"); // 주로 alert 대신 console.log 로 작성하여, 개발자가 에러를 볼 수 있게 해준다.
  //   })

  //   // 1. axios 성공/실패에 대한 결과를 처리할 때
  //   const SeeAllUsers = () => {
  //     axios.get("/users") 
  //     .then(res => { 
  //       setUsers(res.data); 
  //     })
  //     .catch(err => {
  //       alert("에러"); 
  //     })
  //   }
  //   // 2. axios 성공에 대한 결과만 보여줄 때(async await 사용)
  //   /*
  //     async: 일단 기능 실행
  //     await: 값을 가져올 때까지 기능 실행 대기
  //   */
  //  const SeeAllUsers = async () => {
  //   const res = await axios.get("/users"); // Controller 에 있는 users 주소에 가서 데이터를 가져오겠다.
  //   // 가져오기 성공 시
  //   setUsers(res.data); // 가져오는 데에 성공하면, 가져온 데이터로 회원 목록을 만들어주는 것이다.
  //  }

  // async await 버전
  const SeeAllUsers = async () => {
    const res = await axios.get('/users');
    setUsers(res.data);
  };

  // async await 사용해서 user 추가하기
  // addUser 에서 가져온 user 한 명을 넣어주기
  const addUser = async (user) => {
    const res = await axios.post('/users', user); // Controller PostMapping 으로 전달하는 user 정보

    // ...users ◀ 기존에 작성한 회원목록에 user data 하나 추가
    setUsers([...users], res.data);
  }

  return (
    <div className="App">
      <h1>회원 관리</h1>
      <UserForm addUser={addUser} />
      <UserTable users={users} />
    </div>
  );
}

export default App;
