import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../css/Profile.css';

const Profile = () => {

    const viewingAPI = "http://localhost:9010/profile/viewing";
    const uploadAPI = "http://localhost:9010/profile/upload";

    const [files, setFiles] = useState([]);
    const [username, setUsername] = useState('');
    const [profile, setProfile] = useState([]);
    const [userId, setUserId] = useState(null);

    // const 로 변수명 또는 기능명 설정
    const changeFile = (e) => {
        // 파일을 변경했을 때, 프로필 썸네일에 이미지들 주소가 넘어갈 수 있도록 설정
        const selectedFiles = Array.from(e.target.files);
        console.log("선택한 파일들", selectedFiles);
        setFiles(selectedFiles);
    }

    const changeUsername = (e) => {
        setUsername(e.target.value);
    }

    // 1. fetch 방식: React 에서 기본으로 제공하는 Java 백엔드와 통신하는 기능(설치가 필요 없다.)
    const uploadImg1 = () => {
        const formData = new FormData(); // files 에서 이미지 파일이 여러개이기 때문에, 묶어서 전송하기 위한 코드이다.
        Array.from(files).forEach(file => {
            formData.append("files", file);
        });
        formData.append("username", username);

        // /profile/upload ◀ 이렇게만 작성하면 localhost 3000 번인지 9010 번인지 혼동될 수가 있다.
        fetch(uploadAPI, {
            method: "POST", // DB 에 값을 저장하기 위해 POST 사용
            headers: { }, // 'Content-Type' : 'multipart/form-data' ◀ 데이터에 파일(이미지) 이 포함됨을 Java 에 알려주는 것
            //
            body: formData
        })
        // ▼ MySQL DB 에 값을 성공적으로 삽입했다면, 성공 후 수행 할 작업 작성
        .then((response) => {
            
            // ▼ 응답에 대한 결과를 json 형식으로 받는 것
            // return response.json();
            return response.text();
        })
        .then((data) => {
            // DB 에 저장된 프로필 이미지와 닉네임 보여주기
            // Upload 후 Client 가 의식하지 못하도록 새로고침 하기
            getProfile();
        });
    }

    // 2. axios async await 방식: then 방식의 업그레이드 버전으로, try / catch 를 써서 오류 처리를 할 수 있다.
    // async () ◀ 이 기능에는 잠시 대기해야 할 코드가 적혀있다.
    const uploadImg2 = async () => {
        const formData = new FormData(); // files 에서 이미지 파일이 여러개이기 때문에, 묶어서 전송하기 위한 코드이다.
        Array.from(files).forEach(file => {
            formData.append("files", file);
        });
        formData.append("username", username);
        
        // ▼ formData 를 가져오기 전까지 잠시 대기
        //                                     /profile/upload ◀ 이렇게만 작성하면 localhost 3000 번인지 9010 번인지 혼동될 수가 있다.
        await axios.post(uploadAPI, formData, {
            headers: { } // 'Content-Type' : 'multipart/form-data' ◀ 데이터에 파일(이미지) 이 포함됨을 Java 에 알려주는 것
        })
        // ▼ MySQL DB 에 값을 성공적으로 삽입했다면, 성공 후 수행 할 작업 작성
        .then((response) => {
        /* 
            // fetch 에는 이 기능이 필요하지만, axios 에는 이 기능이 포함되어 있으므로 필요하지 않다.
            // ▼ 응답에 대한 결과를 json 형식으로 받는 것
            // return response.json();
            return response.text();
        })
        .then((data) => {
            // DB 에 저장된 프로필 이미지와 닉네임 보여주기
            // Upload 후 Client 가 의식하지 못하도록 새로고침 하기
        */
            const data = response.data;      
            getProfile();
        });
    }

    // 3. axios then 방식
    const uploadImg3 = () => {
        const formData = new FormData(); // files 에서 이미지 파일이 여러개이기 때문에, 묶어서 전송하기 위한 코드이다.
        Array.from(files).forEach(file => {
            formData.append("files", file);
        });
        formData.append("username", username);

        // 삼항 연산자를 사용하여, 수정 기능을 위한 URL 과 새 프로필을 저장할 URL 을 설정하기

        // /profile/upload ◀ 이렇게만 작성하면 localhost 3000 번인지 9010 번인지 혼동될 수가 있다.
        axios.post(uploadAPI, formData, {
            headers: { } // 'Content-Type' : 'multipart/form-data' ◀ 데이터에 파일(이미지) 이 포함됨을 Java 에 알려주는 것
        })
        // ▼ MySQL DB 에 값을 성공적으로 삽입했다면, 성공 후 수행 할 작업 작성
        .then((response) => {
            
            // ▼ 응답에 대한 결과를 json 형식으로 받는 것
            // return response.json(); // fetch 에는 이 기능이 필요하지만, axios 에는 이 기능이 포함되어 있으므로 필요하지 않다.
            // 또는 return response.text();
        // })
        // .then((data) => {
            // DB 에 저장된 프로필 이미지와 닉네임 보여주기
            // Upload 후 Client 가 의식하지 못하도록 새로고침 하기

            const data = response.data;
            getProfile();
        });
    }

    // 페이지를 새로고침 해서 업로드 한 파일을 클라이언트에게 보여주기
    useEffect(() => {
        getProfile();
    }, []);

    const getProfile = () => {
        axios.get(viewingAPI)
        .then ((response) => {
            setProfile(response.data);
            console.log("프로필 가져오기: " + response.data);
        })
    }

    // 닉네임 수정하기
    const editProfile = (p) => {
        setUserId(p.userId); // 수정할 사용자의 ID 설정
        setUsername(p.username);
    }

    return (
        <div>
            <h1>프로필 이미지 업로드</h1>
            <div className='profile-thumbnail'>
                {files.length > 0 && files.map((file, index) => (
                    <div key={index}>
                        <img src={URL.createObjectURL(file)} />
                    </div>
                ))}
            </div>
            {/* 
            <input type="file" multiple onChange={changeFile} />
            ▲ 와 ▼ 는 동일한 기능을 가지지만, const 를 사용하여 기능을 구분짓는 것과, 기능을 한 번에 작성해주는 차이를 가진다.
            <input type="file" multiple onChange={(e) => setFiles(e.target.files)} /> */}
            <input type="file" onChange={changeFile} />
            <input type="text" placeholder='닉네임을 입력하세요.' value={username} onChange={(e) => setUsername(e.target.value)} />
            <button onClick={uploadImg2}>프로필 저장하기</button>
            <hr></hr>
            <h3>프로필 상세 페이지</h3>
            <div>
                {profile.length > 0 && profile.map((p) => (
                    <div key={p.userId}>
                        <p>{p.username}</p>
                        <p>{p.createdAt}</p>
                        {p.profileImageUrl && p.profileImageUrl.split(',').map(image => (
                            <img key={image} src={`http://localhost:9010/images/${image}`} />
                        ))}
                        <button>프로필 수정하기</button>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default Profile;