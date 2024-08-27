import axios from "axios";
import React, { useState, useEffect } from "react"
import '../css/PizzaList.css';

export const PizzaList = () => {
    
    const [pizzas, setPizzas] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:9191/api/pizza")
        .then((response) => setPizzas(response.data))
        .catch((error) => alert("오류 발생", error))
    }, [])

    return (
        <div className="pizza-container">
            <h1>피자 메뉴</h1>
            <ul>
                {pizzas.map(pizza => (
                    <li key={pizza.id}>
                        <div className="pizza-name">{pizza.pizzaName}</div>
                        <div className="pizza-description">{pizza.description}</div>
                        <div className="pizza-price">{pizza.price}</div>
                        <button>상세 보기</button>
                    </li>
                ))}
            </ul>
        </div>
    )
}