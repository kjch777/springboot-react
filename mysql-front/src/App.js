import './App.css';
import axios from 'axios';
import { useState, useEffect } from 'react';
import { UserTable } from './component/UserTable';
import { UserForm } from './component/UserForm';
import { EditUserForm } from './component/EditUserForm';
// select insert component 추가 작성
function App() {
  const [users, setUsers] = useState([]); // 회원 목록이 담길 빈 배열 생성
  const [userToEdit, setUserToEdit] = useState(null); // 수정한 회원 정보를 잠시 가지고 있을 변수 생성

  // useEffect 는, button 또는 특정 값을 클릭하지 않아도, 자동으로 실행된다.(최초 1회만 실행할지, 주기적으로 실행할지 정하는 것)
  // App.js 가 실행되면 적용할 효과로, 만약 특정 변수명이 없다면, 기능이 최초 1회만 실행하고, 이후에는 실행되지 않는다.
  // useEffect(() => {기능}, [          ]);
  // 특정 변수명이 존재한다면, 특정 변수명에 변화가 있을 때마다 기능이 실행된다.
  // useEffect(() => {기능}, [특정 변수명]);

  /***** useEffect 최초 1회만 실행 *****/
  // useEffect(() => {
  //   SeeAllUsers(); // 웹 사이트에 접속하면, 최초 1회만 모든 회원들이 보이고,
  // }, []); // [] 로 배열이 비어있기 때문에, 웹 사이트에 접속했을 때, 딱 한번만 실행되는 것이다.

  /***** useEffect [ ] 배열에 users 를 넣어, 회원 목록에 변화가 생기면 모두 불러오기 기능을 다시 실행할 수 있다. *****/
  useEffect(() => {
    SeeAllUsers(); // users: 회원 목록에 user 가 추가 또는 삭제되면 모든 user 다시보기
  }, [/* users*/]); // [users] 로 배열에 users 가 들어있기 때문에, 회원 목록에 user 가 추가되거나, 삭제될 경우, 회원 목록을 자동으로 새로고침

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

  /***** 추가된 모든 회원들을 보는 기능 *****/
  // async await 버전
  const SeeAllUsers = async () => {
    const res = await axios.get('/users');
    setUsers(res.data);
  };

  /***** 회원 추가 버튼 기능 *****/
  // async await 사용해서 user 추가하기
  // addUser 에서 가져온 user 한 명을 넣어주기
  const addUser = async (user) => {
    const res = await axios.post('/users', user); // Controller PostMapping 으로 전달하는 user 정보

    // ...users ◀ 기존에 작성한 회원목록에 user data 하나 추가
    setUsers([...users], res.data);
  }

  /***** 회원 삭제 버튼 기능 *****/
  const deleteUser = async (id) => {
    /*
    " "   ' ' = 안의 값을 모두 글자 취급한다.
    ` ` = 안의 특정 값을 변수명으로 취급해야할 때 사용한다.
          `` 안에서 변수명으로 취급해야하는 값(상황마다 변경되어야 하는 값) 은 ${이 안에 작성한다.}

    예시
    `` 을 사용했을 때
    http://localhost:3000/users?id=1

    "" '' 을 사용했을 때
    http://localhost:3000/users?id=${id}
    */
    await axios.delete(`/users`, {params: {id}});
    /*
        자바 Controller 에서, @DeleteMapping("/{id}") 매개변수(parameter) 에 (@PathVariable int id) 작성
        리액트 axios 에서, id=${id} 작성
        await axios.delete(`/users?id=${id}`);
        주소 값에 id 대신 삭제할 번호가 들어갈 수 있도록 설정해주는 것이다.

        자바 Controller 에서, @DeleteMapping() 에 특정 id 값을 설정하지 않을 경우
        매개변수(parameter) 에 (@RequestParam("id") int id) // value-"id" ◀ frontend 에서 가져온 id 값이라고 정의한 것이다.
        params: {id}
        await axios.delete(`/users`, {params: {id}});
    */
    
    /*
    setUsers(users.filter(user => user.id != id));
    users ◀ 현재 저장되어 있는 회원들 목록
    user.id != id ◀ user.id 회원 ID 와, ID(삭제하려는 회원 ID) 가 서로 일치하지 않으면,
    setUsers(새로운 회원 목록) 에 포함시키고,
    id(삭제하려는 회원 ID) 와 user.id 가 서로 일치하는 ID 의 회원 정보는 삭제한 다음,
    setUsers(새로운 회원 목록) 를 완성시킨다.

    filter ◀ 회원 목록 걸러내기 기능, 조건
    */
    setUsers(users.filter(user => user.id !== id));
  }

  /***** 회원 정보 수정 버튼 기능 *****/
  const updateUser = async (user) => {
    await axios.put('/users', user); // @PutMapping 에서, /users 로 주소 값이 설정된, 수정하는 주소와 연결하기
    
    setUsers(users.map(u => (u.id === user.id ? user : u)));
    // 수정한 회원의 id 값이 일치하는지 확인하고, id 값이 일치하지 않는다면,
    // 기존에 있던 회원 정보 그대로, 수정하지 않고 전달하는 것이다.
  }
  
  /***** 회원 정보 수정을 완료하면, 회원 목록에 수정된 회원을 전달하는 것 *****/
  const editUser = (user) => {
    setUserToEdit(user);
  }

  /***** 회원 정보 수정을 취소하면, 회원 목록에 *****/
  const cancelEdit = () => {
    setUserToEdit(null); // 회원 정보 수정을 취소할 때, 빈 값(null) 으로 변경하는 일종의 트릭이다.
  }


  return (
    <div className="App">
      <h1>회원 관리</h1>
      <UserForm addUser={addUser} />
      <UserTable users={users} deleteUser={deleteUser} editUser={editUser} />
      {userToEdit && (<EditUserForm userToEdit={userToEdit} updateUser={updateUser} cancelEdit={cancelEdit} />)}
    </div>
  );
}

export default App;
