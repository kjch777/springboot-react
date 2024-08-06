import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App() {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [files, setFiles] = useState([]);
    const [posts, setPosts] = useState([]);

    const transferToJava = () => {
        // Form: 특정 값을 가져와서 넘겨줄 때 사용할 객체
        const formData = new FormData();
        
        // files ◀ 파일이 하나가 아니라 여러개이기 때문에, 여럿을 담을 수 있는 배열 설정하기
        Array.from(files).forEach(file => {
            // return formData.append("files", file);
            formData.append("files", file); // forEach 문에서만 return 생략이 가능하다.
        });
        formData.append("title", title);
        formData.append("content", content);

        // axios.post 를 사용하여 Java Controller 에 데이터 전송하기
        axios.post("/gallery/upload", formData, { // "http://localhost:9010/gallery/upload" 형식으로 작성해주어도 된다.
            headers: {
                // 전송 할 데이터에 텍스트가 아닌 파일이 함께 전송된다고 머릿말로 알려주기
                'Content-Type': 'multipart/form-data'
            }
        });
        alert("자바로 이미지 전송 완료");
    }

    const getPosts = async () => {
        const response = await axios.get('/posts');
        setPosts(response.data);
    }

    useEffect(() => {
        getPosts();
    }, [])

    return (
        <div className="App">
            <div>
                <label>제목: </label>
                <input type='text' value={title} onChange={(e) => setTitle(e.target.value)} />
            </div>
            <div>
                <label>내용: </label>
                <textarea value={content} onChange={(e) => setContent(e.target.value)} />
            </div>
            <div>
                <label htmlFor='chooseImg'>사진 선택</label>
                <input type='file' id='chooseImg' multiple className='inputImg' onChange={(e) => setFiles(e.target.files)}/> {/* style={{display:'none'}}*/} 
            </div>
            <button onClick={transferToJava}>등록</button>
            
            <div>
                {posts.map(post => (
                    <div key={post.id}>
                        <h2>{post.title}</h2>
                        <p>{post.content}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default App;