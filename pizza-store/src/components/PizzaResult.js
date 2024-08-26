import axios from "axios";
import { useEffect, useState } from "react"
import { useLocation } from "react-router-dom";

export const PizzaResult = () => {

    // 검색된 피자들을 담을 변수명 설정
    const [pizzas, setPizzas] = useState([]);

    // useLocation: 현재 페이지의 정보를 가지고 있다. (경로, 검색된 문자 등)
    // useLocation 안에는 pathname, search, hash, state, key 가 존재한다.
    // location ◀ 여기서는 정보가 담긴 변수이다.
    const location = useLocation(); // 컴퓨터 상에서 내 위치 정보를 변수에 담은 것
    const query = new URLSearchParams(location.search).get("query");
    // 정보가 담긴 변수 안에서, 특정 Key 의 값을 가지고 오는 것을 뜻한다.

    // 검색에 대한 결과가 바로 보여야 하고, 검색어가 수정되면 재 검색을 해야 한다.
    // query: 검색어(= keyWord, searchTerm)
    useEffect(() => {
        /* query 값이 바뀔 때마다, DB에서 다시 검색된 내용 불러오기 */
        // 만약, 쿼리가 존재한다면
        if (query) {
            axios.get(`http://localhost:9191/api/pizza/search?query=${query}`)
            .then((response) => setPizzas(response.data))
            .catch((error) => console.error("오류 발생", error));
        }
    }, [query])

    return (
        <div className="pizza-search-list">
            <h1>검색 결과: </h1>
            {pizzas.length > 0 ?
                (pizzas.map((pizza) => (
                    <div key={pizza.id}>
                        <h3>{pizza.pizzaName}</h3>
                        <p>{pizza.description}</p>
                        <p>{pizza.price}</p>
                    </div>
                ))) : 
                (<div>
                    <p>검색 결과가 없습니다.</p>
                </div>)}
        </div>
    )
}