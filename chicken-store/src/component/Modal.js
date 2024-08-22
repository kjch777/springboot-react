import '../css/Modal.css';

// 버튼을 열거나 닫을 때 동작하는 코드
export const Modal = ({isOpen, onClose, children}) => {

    // isOpen 이 false 이면, 아래 return ( ) 안의 코드를 보지 않고
    if (!isOpen) {
        return null; // 아래 return ( ) 안에 있는 html 코드(태그) 를 보지 않고 함수 종료
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="modal-close" onClick={onClose}>
                    &times;
                </button>
                {children}
            </div>
        </div>
    )
}