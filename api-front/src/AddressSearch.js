import { useEffect, useState } from 'react';
import axios from 'axios';

// RESTful: client 와 DB 를 (지금은 Java 로) 연결하는 것
// API 백엔드에서 프론트엔드 대신 사용하는 것(포스트맨, 스웨거 등)
// csv xml json 형식
/*
csv: ,
xml: <> </>
json: [] {} ◀ 가볍고 빨라서 주로 사용된다.
*/
// 다음 주소 API 가져와서 사용

const AddressSearch = () => {
  const [address, setAddress] = useState('');
  const [addAddress, setAddAddress] = useState('');
  const [finalAddress, setFinalAddress] = useState('');

  // 백엔드 API URL 주소를 /api/addUser 로 지정하고, Restful 연결을 하려 한다.
  // Restful 연결: Java Controller 로 연결하여 DB 에 값을 넣겠다.
  const sendByFetch = () => {
    fetch("http://localhost:8090/api/addUser", {
        method: "POST",
        headers: {'Content-Type': 'application/json'},

        // Java 에서 Parameter 값도 address 로 설정해 주어야 한다.
        body: JSON.stringify({address: finalAddress})
    })
    // 데이터 삽입에 성공했을 때
    .then(response => response.json())
  }

  const sendByAxios = () => {
    axios.post("http://localhost:8090/api/addUser", {address: finalAddress})
    // 데이터 삽입에 성공했을 때
    .then(response => alert("데이터 삽입 성공"))
  }

  // 주소 검색을 완료하고, client 가 검색한 데이터를 가져와 기능 실행
  const handleComplete = (data) => {

    // client 가 선택한 기본 주소를 fullAddress 주소에 저장
    let fullAddress = data.address;
    
    // client 가 입력한 상세 주소를 extraAddress 주소에 저장
    let extraAddress = '';

    // R(Road): 도로명 주소 J(Jibeon): 지번 주소
    if (data.addressType === 'R') { // 주소 종류가 도로명 주소일 때
      
      // bname: 특정 동이 존재하면 추가
      if (data.bname !== '') {
        extraAddress += data.bname;
      }

      // buildingName: 특정 빌딩 이름이 존재하면 추가
      if (data.buildingName !== '') {
        extraAddress +=
          extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      }

      // fullAddress: 모든 주소 합치기
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }

    // 완성된 주소
    setAddress(fullAddress);
  };

  // 주소 검색 버튼을 눌렀을 때 실행 될 openPostcode 기능 작성
  const openPostcode = () => {
    
    // new window: 새 창에서 daum: 다음 주소 서비스 실행
    new window.daum.Postcode({ 

      // oncomplete: client 가 주소 검색을 완료했을 때 호출 할 함수 지정
      // 호출 할 함수: 주소 검색을 완료한 다음 실행할 기능 선택
      oncomplete: handleComplete,// oncomplete: 다음에서 제공하는 기능 handleComplete: 개발자가 만든 기능
    }).open(); // 실행하기
  };

  // useEffect 활용해서 최종주소 추가하기
  useEffect(() => {
    setFinalAddress(`${address}${addAddress}`);
  }, [address, addAddress]) // address: client 가 선택한 기본 주소 addAddress:

  return (
    <div>
      <button onClick={openPostcode}>주소 검색</button>
      {address && 
        (<div>
            <div>
                선택한 주소: {address}
                <input type='text' placeholder='추가 주소 입력(예: 아파트 동/호 수)' value={addAddress} onChange={(e) => setAddAddress(e.target.value)} />
            </div>
        </div>)}
      {address && addAddress && 
        (<>
            <p>최종 주소</p>
            <h5>{finalAddress}</h5>

            {/* 나중에 value={finalAddress} 값을 DB 에 넣어야 할 때 사용한다.
                주로 최종적으로 DB 에 값을 보낼 input 태그에는 hidden 처리를 하여 client 에게 보이지 않게 한다. */}
            <input type='text' value={finalAddress} hidden />
            <button onClick={sendByFetch}>전송하기</button>
        </>)}
    </div>
  );
};

export default AddressSearch;