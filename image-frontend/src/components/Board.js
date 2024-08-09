import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../App.css';

const Board = () =>  {

    const postsAPI = "http://localhost:9010/posts";
    const uploadAPI = "http://localhost:9010/gallery/upload";

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
        axios.post(uploadAPI, formData, { // "http://localhost:9010/gallery/upload" 형식으로 작성해주어도 된다.
            headers: {
                // 전송 할 데이터에 텍스트가 아닌 파일이 함께 전송된다고 머릿말로 알려주기
                'Content-Type': 'multipart/form-data'
            }
        });
        alert("자바로 이미지 전송 완료");
        
        // DB 에 이미지를 업로드 한 다음, 업로드 된 이미지 불러오기
        loadPost();
    }

    const loadPost = () => { // const 로 기능을 미리 작성해놓기(나중에 필요할 때 기능을 사용하기 위함)
        axios.get(postsAPI) // Java Controller 단에서 설정해둔 url api 주소에서 데이터 가져오기
        .then(response => {
            setPosts(response.data);
            console.log(response.data);
        })
    }

    // 맨 처음 사이트에 들어왔을 때, 바로 loadPost 를 실행하기
    useEffect(() => {
        loadPost();
    }, [])

    return (
        <div className="App">
            <div className='form-container'>
                <table>
                    <tbody>
                        <tr>
                            <td>
                                <label>제목: </label>
                            </td>
                            <td>
                                <input type='text' value={title} onChange={(e) => setTitle(e.target.value)} />
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <label>내용: </label>
                            </td>
                            <td>
                                <textarea value={content} onChange={(e) => setContent(e.target.value)} />
                            </td>
                        </tr>

                        <tr>
                            <td>
                                <label htmlFor='chooseImg' className='file-label'>
                                    사진 선택
                                    <input type='file' id='chooseImg' multiple className='inputImg' onChange={(e) => setFiles(e.target.files)}/> {/* style={{display:'none'}}*/} 
                                </label>
                            </td>  
                        </tr>
                        
                        <tr>
                            <td>
                                <button onClick={transferToJava}>이미지 업로드</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div className='posts-container'>
                {posts.map(post => (
                    <div key={post.id} className='post'>
                        <h2>{post.title}</h2>
                        <p>{post.content}</p>
                        <p>{post.createdAt}</p>
                        {/* 
                            {post.files && post.files.map(file => (
                          // post.files(이미지) 가 존재할 경우에만 && 뒤의 코드 실행
                                <img key={file} src={file} /> ))}
                             // <img key={file} src={file} /> ))} ◀ Array 에 대한 배열이 제대로 나오지 않으면, 오류가 발생 할 가능성이 높다.
                                                                  // , 로 구분짓는 등, 이미지를 여러개 다룰 때는 쓰이지 않는 형식이다.
                                                                  // DB 에 이미지를 , 로 구분지어 여러장을 저장했기 때문에,
                                                                  // , 를 기준으로 이미지를 불러와야 한다. 
                        */}
                        <div className='images'>
                            {post.imageUrl.split(',').map(image => ( // 여러개의 이미지를 , 로 구분(split)
                                <img key={image} src={`http://localhost:9010/images/${image}`} /> // ◀ 이 경로는 Java WebConfig 에서 설정해둔 주소이다. 
                            ))}
                        </div>   
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Board;

{/* 
    <table>
        <thead>
            <tr>
                <th>제목</th>
                <th>내용</th>
                <th>생성일</th>
                <th>이미지</th>
            </tr>
        </thead>
        <tbody>
            {posts.map(post => (
                <tr key={post.id}>
                    <td>{post.title}</td>
                    <td>{post.content}</td>
                    <td>{post.createdAt}</td>
                    <td>
                        {post.imageUrl.split(',').map(image => ( 
                            <img key={image} src={`http://localhost:9010/images/${image}`} style={{height: '100px', width: '100px'}} /> 
                        ))}
                    </td>
                    <td>
                        <button>이미지 수정하기</button>
                    </td>
                </tr>
            ))}
        </tbody>
    </table>
*/}