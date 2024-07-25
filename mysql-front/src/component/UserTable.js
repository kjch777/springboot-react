import React from 'react';

/* 
const 컴포넌트명 = (        ) => { }
-- 어떤 값도 받지 않고, 단독으로 기능을 진행할 때 사용한다.
-- 특정 상태 또는 값을 다른 곳에서 전달받고, 전달받은 값에 의한 변화가 필요하지 않은 경우 사용한다.

const 컴포넌트명 = ({특정 값}) => { }
-- 특정 값의 영향을 받아, 기능이 변화할 때 사용한다.
*/

// UserTable 컴포넌트는, 웹 사이트에 회원가입 한 유저들의 정보를 보는 공간이다.
// App.js 에서 전달받은 User 값들을 받아와, 유저 정보를 보여줄 것이다.
export const UserTable = ({users}) => {
    return (
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>NAME</th>
                    <th>EMAIL</th>
                    <th>ACTIONS</th>
                </tr>
            </thead>
            <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.name}</td>
                        <td>{user.email}</td>
                        <td>
                            <button>회원정보 삭제하기</button>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
    )
}