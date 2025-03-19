import { useState } from "react"
import clienteAxios from "../config/clienteAxios"
import Alerta from "../config/Alerta"
import { useNavigate } from "react-router-dom"
import useAmbiente from "../hooks/useAmbiente"

const AmbientesRegister = () => {

    const [nameInput,setNameInput] = useState('')
    const [priceInput,setPriceInput] = useState(0)
    const [alerta,setAlerta] = useState({})
    const navigate = useNavigate()
    const {setAmbientes} = useAmbiente()

    const guardarAmbiente = async () => {
        try {
            const token = localStorage.getItem('token')
            const config = {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                }
            }
            const {data} = await clienteAxios.post("/ambientes/",{name:nameInput,price:priceInput},config)
            
            setAlerta({msg:data.msg,error:data.errorFlag})
            setAmbientes(data)
            setTimeout(() => {
                navigate("/menu/ambientes")
            },3000)
        } catch (error) {
            if (error.response) {
                const errorMessages = [
                    error.response.data.name,
                    error.response.data.price
                ]
                const anyError = errorMessages.find(msg => msg !== null)
                setAlerta({ msg: anyError, error: true });
            }
            setTimeout(()=>{
                setAlerta({})
            },3000)
        }
    }

    const {msg} = alerta

    return (
        <div className="flex flex-col bg-white p-6 rounded-lg mx-auto w-1/5">
            <h2 className="text-4xl font-bold text-sky-600 text-md mb-12 text-center">REGISTRO DE AMBIENTES</h2>
            {msg && <Alerta alerta={alerta}/>}
            <div className="flex flex-col my-2">
                <label>Nombre del Ambiente:</label>
                <input className="rounded-md bg-gray-100 p-2" type="text" onChange={(e)=>setNameInput(e.target.value)}/>
            </div>
            <div className="flex flex-col my-2">
                <label>Precio del Ambiente:</label>
                <input className="rounded-md bg-gray-100 p-2" type="text" onChange={(e)=>setPriceInput(e.target.value)}/>
            </div>
            <div className="flex flex-col my-2">
                <button className="bg-green-400 hover:cursor-pointer hover:bg-green-600 rounded-md p-3 text-white text-center" type="button" onClick={()=>guardarAmbiente()}>Guardar Ambiente</button>
            </div>
        </div>
    )
}

export default AmbientesRegister