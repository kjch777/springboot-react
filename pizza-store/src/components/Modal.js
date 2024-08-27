import '../css/PizzaRouter.css';

/* 
    is: 주로 true/false (boolean) 를 나타낼 때 사용한다.
    on: 주로 이벤트(e, event) 를 처리하기 위한 함수 이름으로 사용된다.
        = 특정 사건이 발생했을 때, 대처할 기능
        = 닫기 사건 발생 => 대처 == 메인 화면에서 특정 기능이 보이지 않게 닫는다.
*/

// [ ]: 변수 선언
// { }: 외부에서 가져온 값 사용
export const Modal = ({isOpen, onClose, children}) => {

    if(!isOpen) { // 만약, 모달을 열기 전이라면
        return null; // 다시 돌려 보내기를 이용하여 화면에 닫기 버튼이 보이지 않도록 설정하는 일종의 트릭이다.
    }

    return (
        <div className='modal-container'>
            <button onClick={onClose}>&times;</button>
            {children}
        </div>
    )
}