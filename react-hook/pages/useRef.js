import { useRef, useState } from "react"


export default function UseRef() {
    const [submitData, setSubmitData] = useState("");

    const refChange = () => {
        setSubmitData(refValue.current.value)
    }
    const refValue = useRef("");

    return (
        <>
            <h2>useRef</h2>
            <input ref={refValue} type="text"></input>
            <button onClick={refChange}>전송</button>
            {submitData}
        </>
    )
}