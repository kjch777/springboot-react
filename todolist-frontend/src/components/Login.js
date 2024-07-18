import React, {useContext, useState} from 'react';
import { TodoListContext as TodoListContext } from './TodoListContext';

export const Login = () => {
    const {loginMember, setLoginMember, setTodoList} = useContext(TodoListContext);
    
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');

    const LoginBtn = () => {
        fetch('/login', {
          method: "POST",
          headers: {"Content-Type": "application/json", 
                    "Accept": "application/json"}, 
          body: JSON.stringify({id: id, pw: pw}) 
        })
        .then(response => response.json())
        .then(map => {
            console.log(map);

            if(map.loginMember === null) {
                alert('아이디 또는 비밀번호가 일치하지 않습니다.');
                return;
            } 

            setLoginMember(map.loginMember);
            setTodoList(map.todoList);

            setId('');
            setPw('');
            alert('로그인 성공');
        })
    }

    const LogoutBtn = () => {
        setLoginMember(null);
        alert('로그아웃 성공');
    }

    return (
        <div className='login-container'>
            <table>
                <tbody>
                    <tr>
                        <th>ID</th>
                        <td><input type='text' onChange={e => setId(e.target.value)} value={id} /></td>
                    </tr>
                    <tr>
                        <th>PW</th>
                        <td><input type='password' onChange={e => setPw(e.target.value)} value={pw} /></td>
                        {!loginMember && (
                            <td><button onClick={LoginBtn}>로그인</button></td>
                        )}
                        {loginMember && (
                            <td><button onClick={LogoutBtn}>로그아웃</button></td>
                        )}
                    </tr>
                </tbody>
            </table>
        </div>
    )
}