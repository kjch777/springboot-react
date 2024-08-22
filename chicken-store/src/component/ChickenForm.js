import React, {useState, useEffect} from "react";
import axios from "axios";
import '../css/ChickenForm.css';

export const ChickenForm = () => {
    
    const [chickenName, setChickenName] = useState("");
    const [description, setDescription] = useState("");
    const [price, setPrice] = useState("");
    
    const data = {
        chickenName,
        description,
        price
    }

    const handleSubmit = () => {
        axios.post("http://localhost:9191/api/chicken", data)
        .then((response) => {
            alert("성공")
        })
        .catch((e) => {
            alert("실패")
        })
    }

    return (
        <div className="chickenForm-container">
            <label>메뉴 명칭: 
                <input type="text" value={chickenName} onChange={(e) => setChickenName(e.target.value)} />
            </label>

            <label>메뉴 설명: 
                <textarea value={description} onChange={(e) => setDescription(e.target.value)} />
            </label>

            <label>메뉴 가격: 
                <input type="number" value={price} onChange={(e) => setPrice(e.target.value)} />
            </label>

            <button onClick={handleSubmit}>등록하기</button>
        </div>
    )
}