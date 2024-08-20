import React, {useState, useEffect} from 'react';
import axios from 'axios';

export const ChickenList = () => {

    const [chickens, setChickens] = useState([]);

    // 최초 1회 실행하는 useEffect 를 사용하여, ChickenList.js 가 시작되자마자 DB 에 저장된 치킨 메뉴들 가져오기
    // useEffect(() => {기능 설정}, [언제 다시 기능을 작동시킬 것인지 설정])
    useEffect(() => {
        axios.get("http://localhost:9191/api/chicken")
        .then(response => setChickens(response.data)) // 가져온 데이터를 chickens 변수에 저장하는 과정이다.
        .catch(e => alert("오류 발생"))
    }, [])

    return (
        <div className='chicken-container'>
            <h1>치킨 메뉴</h1>
            <ul>
            {chickens.map(chicken => (
                <li key={chicken.id}>
                    {chicken.chickenName} - {chicken.description} - ₩{chicken.price}원
                </li>
            ))}
            </ul>
        </div>
    )
}