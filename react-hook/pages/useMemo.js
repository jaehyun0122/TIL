import { useMemo } from 'react'

const getColor = color => {
    console.log("getColor")
    switch (color) {
        case "red":
            return "빨강"
    }
}
const getPhone = phone => {
    console.log("getPhone")
    switch (phone) {
        case "galaxy":
            return "kor"
    }
}

export default function Memo(props) {
    
    const colorValue = useMemo(() => getColor(props.color), [props.color])
    const phone = useMemo(() => getPhone(props.phone), [props.phone])
    
    // const colorValue = getColor(props.color)
    // const phoneValue = getPhone(props.phone)

    return (
        <>
            <h2>useMemo</h2>
            <div>props color :
                {props.color}
            </div>
            <div>
                {colorValue}
            </div>
            <div>props phone : 
                {props.phone}
            </div>
            <div>
                {phone}
            </div>
        </>
    )
}