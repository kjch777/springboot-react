import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import '../css/SearchResult.css';

export const SearchResult = () => {

    // 치킨들을 담을 변수명
    const [chickens, setChickens] = useState([]);

    // 주소 값에서 가져온 쿼리 추출
    const location = useLocation();

    // 주소 값 API URL 쿼리 파라미터 추출
    const query = new URLSearchParams(location.search).get("query");

    // 만약, 주소에서 쿼리 안에 client 가 원하는 특정 값이 존재한다면
    useEffect(() => {
        if(query){
            axios.get(`http://localhost:9191/api/chicken/search?query=${query}`)
            .then((response) => setChickens(response.data))
            .catch((error) => console.error("오류 발생", error))
        }
    }, [query])

    return (
        <div className='chicken-list'>
            <h2>검색 결과: "{query}"</h2>
            {chickens.length > 0 ? 
                (chickens.map((chicken) => (
                    <div key={chicken.id} className='chicken-item'>
                        <h3>{chicken.chickenName}</h3>
                        <p>{chicken.description}</p>
                        <p>{chicken.price}원</p>
                    </div>
                ))) : 
            (<div>
                <p className='no-results'>검색 결과가 없습니다.</p>
            </div>)}
        </div>
    )
}