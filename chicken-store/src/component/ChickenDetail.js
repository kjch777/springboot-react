import { useEffect, useState } from "react"
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import '../css/ChickenDetail.css';

export const ChickenDetail = () => {
    
    const navigate = useNavigate(); // 페이지를 이동하기 위한 navigate 함수
    // navigate ◀ 기능 작성에서 이동할 때 주로 사용한다. const 기능작성 = () => {} 또는 useEffect 같은 Hook 안에 작성한다.
    // 이동하기 위해 필요한 동작이 사용자의 눈에 직접적으로 보이지 않는다. 개발자가 암묵적으로 이동시킨다.
    
    // Link ◀ 태그에서 직접적으로 주소 이동하는 코드를 작성할 때 주로 사용한다.

    const [chicken, setChicken] = useState(null);
    const {id} = useParams();
    // { } ◀ 특정 값을 받아오는 것
    // [ ] ◀ 변수명을 설정하는 것
    console.log("id: ", id);

    // 수정된 데이터를 저장하는 공간으로, 맨 처음에는 수정한 내용이 없기 때문에 빈 공간("") 으로 설정해주는 것이다.
    const [editData, setEditData] = useState({
        chickenName: "",
        description: "",
        price: ""
    });

    // handleEdit 버튼을 클릭했는지 아닌지를 체크하는 boolean
    const [isEdit, setIsEdit] = useState(false);

    useEffect(() => {
        axios.get(`http://localhost:9191/api/chicken/${id}`)
        .then(response => {
            setChicken(response.data)
            
            // 내용 수정하기(setEditData)
            setEditData({
                chickenName: response.data.chickenName,
                description: response.data.description,
                price: response.data.price
            })
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

    const handleEditSave = () => {
        axios.put(`http://localhost:9191/api/chicken/${id}`, editData)
        .then(response => {
            setChicken(response.data); // 기존에 DB에 저장된 내용 가져오기
            setIsEdit(false);
        })
        .catch(error => {
            console.error("오류 발생", error)
        })
    }

    const handleEdit = () => {
        setIsEdit(true);
    }

    const handleToPrev = () => {
        setIsEdit(false);
    }

    const handleDelete = () => {
        axios.delete(`http://localhost:9191/api/chicken/${id}`)
        .then(() => {
            alert("삭제되었습니다.");
            navigate("/"); // 삭제하고 메인 페이지로 이동하기
        })
        .catch(error => {
            console.log("삭제 과정에 문제 발생");
        })
    }

    return (
        <div className="detail-container">
            {/* handleEdit 버튼을 눌렀다면 ? (수정하는 기능 출력) : (누르지 않았다면 작성된 내용 보여주기) */}
            { isEdit ? 
            
            (<div>
                <input type="text" name="chickenName" value={editData.chickenName} onChange={(e) => setEditData({...editData, chickenName:e.target.value})} />
                <textarea name="description" value={editData.description} onChange={(e) => setEditData({...editData, description:e.target.value})} />
                <input type="number" name="price" value={editData.price} onChange={(e) => setEditData({...editData, price: e.target.value})} />
                <button onClick={handleEditSave}>저장하기</button>
                <button onClick={handleToPrev}>돌아가기</button>
            </div>) : 
            
            (<div>
                <h1>{chicken.chickenName}</h1>
                <p>{chicken.description}</p>
                <p>₩{chicken.price}원</p>
                <button onClick={handleEdit}>수정하기</button>
                <button onClick={handleDelete}>삭제하기</button>
            </div>)
            }
        </div>
    )
}