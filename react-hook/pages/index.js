import Memo from "../pages/useMemo"
import UseRef from "../pages/useRef"
import { useState } from 'react'

export default function Home() {

  const [ color, setColor ] = useState("black")
  const [ phone, setPhone ] = useState("galaxy")
  const changeColor = (e) => {
    setColor(e.target.value)
  }
  const changePhone = (e) => {
    setPhone(e.target.value)
  }
  return (
    <>
      <input type="text"
        onChange={changeColor}
      ></input><label>color</label>
      <br/>
      <input
        onChange={changePhone}
      ></input><label>phone</label>
      <Memo color={color} phone={phone} />
      <UseRef />
    </>
  )
}
