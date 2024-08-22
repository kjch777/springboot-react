import React, {useState, useEffect} from 'react';
import axios from 'axios';
import '../css/ChickenList.css';
import { Link, useNavigate } from 'react-router-dom';

export const ChickenList = () => {

    const [chickens, setChickens] = useState([]);
    const navigate = useNavigate();

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
                    <li key={chicken.id} className='chicken-item'>
                        <div className='chicken-name'>{chicken.chickenName}</div>
                        <div className='chicken-description'>{chicken.description}</div>
                        <div className='chicken-price'>₩{chicken.price}원</div>
                        {/* 
                        Link 와 useNavigate 는 사용 방법의 차이만 있을 뿐, 동일하게 작동된다.
                        <Link to={`/chicken-detail/${chicken.id}`}>
                            <button className='detail-button'>상세보기</button>
                        </Link>
                        <button className='detail-button' onClick={() => navigate(`/chicken-detail/${chicken.id}`)}>상세보기</button> 
                        */}
                        <button className='detail-button' onClick={() => navigate(`/chicken-detail/${chicken.id}`)}>상세보기</button>
                    </li>
                ))}
            </ul>
        </div>
    )
}