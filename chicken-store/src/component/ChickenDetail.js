import { useEffect, useState } from "react"
import axios from "axios";
import { useParams } from "react-router-dom";
import '../css/ChickenDetail.css';

export const ChickenDetail = () => {
    
    const [chicken, setChicken] = useState(null);
    const {id} = useParams();
    // { } ◀ 특정 값을 받아오는 것
    // [ ] ◀ 변수명을 설정하는 것
    console.log("id: ", id);

    useEffect(() => {
        axios.get(`http://localhost:9191/api/chicken/${id}`)
        .then(response => {
            setChicken(response.data)
        })
        .catch(e => alert("오류 발생"))
    }, []);

    // 만약, 치킨 데이터가 없다면
    if(!chicken) {
        return (
            <div>
                Loading...
            </div>
        )
    }

    return (
        <div className="detail-container">
            <h1>{chicken.chickenName}</h1>
            <p>{chicken.description}</p>
            <p>₩{chicken.price}원</p>
        </div>
    )
}