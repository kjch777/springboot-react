import React, {useState} from "react";

export const UserForm = ({addUser}) => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault(); // 제출 잠시 대기
        addUser({name, email});
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
        </form>
    )
}