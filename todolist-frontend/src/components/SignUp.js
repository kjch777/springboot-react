import React, {useState} from "react";

export const SignUp = () => {
    const [id, setId] = useState('');
    const [pw, setPw] = useState('');

    const [result, setResult] = useState('');
    const [pwCheck, setPwCheck] = useState('');
    const [name, setName] = useState('');

    const [idValid, setIdValid] = useState(false);

    const idCheck = (eventId) => {
        setId(eventId);

        if(eventId.trim().length < 4) {
            setIdValid(false);
            return;
        }

        fetch("/idCheck?id=" + eventId)
        .then(response => response.text())
        .then(result => {
            if(Number(result) === 0) {
                setIdValid(true);
            } else {
                setIdValid(false);
            }
        })
    }

    const SignUpBtn = () => {
        if(!idValid) {
            alert('아이디가 유효하지 않습니다.');
            return;
        }

        if(pw !== pwCheck) {
            alert('비밀번호가 서로 일치하지 않습니다.');
            return;
        }

        const inputValues = {};
        inputValues.id = id;
        inputValues.pw = pw;
        inputValues.name = name;

        fetch("/signup", {
            method: "POST",
            headers: {"Content-Type" : "application/json"},
            body: JSON.stringify(inputValues)
        })
        .then(response => response.text())
        .then(result => {
            if(Number(result) > 0) {
                setResult('회원가입 성공');

                setId('');
                setPw('');
                setPwCheck('');
                setName('');
            } else {
                setResult('회원가입 실패');
            }
        })
    }

    return (
        <div className="signup-container">
            <label>
                ID: 
                <input type="text" onChange={e => idCheck(e.target.value)} value={id} className={idValid ? '' : 'id-err'} />
            </label>
            <label>
                PW: 
                <input type="password" onChange={e => {setPw(e.target.value)}} value={pw} />
            </label>
            <label>
                PW Check: 
                <input type="password" onChange={e => {setPwCheck(e.target.value)}} value={pwCheck} />
            </label>
            <label>
                Name: 
                <input type="text" onChange={e => {setName(e.target.value)}} value={name} />
            </label>
            <button onClick={SignUpBtn}>회원가입 하기</button>

            <hr/>

            <h3>{result}</h3>
        </div>
    )
}