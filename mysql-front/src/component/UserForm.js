import React, {useState} from "react";

export const UserForm = ({addUser}) => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault(); // 제출 잠시 대기
        addUser({name, email});
        setName(''); // input 에 입력된 내용을 제출하고, 입력된 이름 값 지워주기
        setEmail(''); // input 에 입력된 내용을 제출하고, 입력된 이메일 값 지워주기
    }

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>이름: </label>
                <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
            </div>
            <div>
                <label>이메일: </label>
                <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
            </div>
            <button>회원 추가하기</button>
            <br /><br /><br /><br />
            <button>네이버 로그인을 통한 회원 추가하기</button>
        </form>
    )
}