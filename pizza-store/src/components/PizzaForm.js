import React, { useState } from 'react';
import axios from 'axios';

export const PizzaForm = () => {
    
    const [pizzaName, setPizzaName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState('');

    const requestData = {
        pizzaName,
        description,
        price
    }

    const handleRegister = () => {
        axios.post("http://localhost:9191/api/pizza", requestData)
        .then((response) => alert("성공", response))
        .catch((error) => alert("실패", error))
    }

    return (
        <div className='pizzaform-container'>
            <label>
                메뉴 이름: 
                <input type='text' value={pizzaName} onChange={(e) => setPizzaName(e.target.value)} />
            </label>
            <label>
                메뉴 설명: 
                <input type='text' value={description} onChange={(e) => setDescription(e.target.value)} />
            </label>
            <label>
                메뉴 가격: 
                <input type='number' value={price} onChange={(e) => setPrice(e.target.value)} />
            </label>
            <button onClick={handleRegister}>등록하기</button>
        </div>
    )
}