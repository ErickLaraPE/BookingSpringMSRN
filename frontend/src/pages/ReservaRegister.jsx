
import { useState } from "react"
import useAmbiente from "../hooks/useAmbiente"
import clienteAxios from "../config/clienteAxios"
import { useNavigate } from "react-router-dom"
import Alerta from "../config/Alerta"
import useAuth from "../hooks/useAuth"
import useReserva from "../hooks/useReserva"

const ReservaRegister = () => {

    const [tipoAmbienteField,setTipoAmbienteField] = useState('')
    const [tipoPagoField,setTipoPagoField] = useState('')
    const [selectedIdAmbientes, setSelectedIdAmbientes] = useState([]); //arreglo de ids de ambientes
    const navigate = useNavigate()
    const {ambientes} = useAmbiente()
    const {auth} = useAuth()
    const {setReservas} = useReserva()

    //Funcion para manejar la seleccion y deseleccion del checkbox
    const handleCheckboxChange = (id) => {
        setSelectedIdAmbientes((idPrevSelected) => {
            // idPrevSelected es el arreglo de valores actuales de selectedIdAmbientes
            if (idPrevSelected.includes(id)) {
                return idPrevSelected.filter((item) => item !== id)
            } else {
                return [...idPrevSelected, id]
            }
        })
    }

    const guardarReserva = async () => {
        try {
            const token = localStorage.getItem('token')
            const config = {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                }
            }
            const {data} = await clienteAxios.post("/reservas/",{tipoAmbReserva:tipoAmbienteField,tipoPago:tipoPagoField,ambientes_id:selectedIdAmbientes,usuario_id:auth.id},config)
            setReservas(data)
            setTimeout(()=>{
                navigate('/menu/reservas')
            },3000)
        } catch (error) {
            console.log(error)
        }
    }

    return (
        <div className="flex flex-col bg-white p-6 rounded-lg mx-auto w-1/5">
            <h2 className="text-4xl font-bold text-sky-600 text-md mb-12 text-center">REGISTRO DE RESERVAS</h2>
            <div className="flex flex-col my-2">
                <label>Tipo de Ambiente de Reserva:</label>
                <input className="rounded-md bg-gray-100 p-2" type="text" onChange={(e)=>setTipoAmbienteField(e.target.value)}/>
            </div>
            <div className="flex flex-col my-2">
                <label>Tipo de Pago:</label>
                <input className="rounded-md bg-gray-100 p-2" type="text" onChange={(e)=>setTipoPagoField(e.target.value)}/>
            </div>
            <div className="flex flex-col my-2">
                <label>Seleccion de Ambientes:</label>
                <div className="py-1">
                        {ambientes.map((ambiente) => {
                            return(
                                <div key={ambiente.ambienteId} className="flex flex-row">
                                    <label className="flex items-center px-4 py-2 hover:bg-gray-100">
                                    <input
                                        type="checkbox"
                                        checked={selectedIdAmbientes.includes(ambiente.ambienteId)}
                                        onChange={() => handleCheckboxChange(ambiente.ambienteId)}
                                        className="mr-2"
                                    />
                                    {ambiente.name}
                                    </label>
                                </div>
                            )
                        })}
                </div>
            </div>
            <div className="flex flex-col my-2">
                <button className="bg-green-400 hover:cursor-pointer hover:bg-green-600 rounded-md p-3 text-white text-center my-2" type="button" onClick={()=>guardarReserva()}>Guardar Reserva</button>
            </div>
        </div>
    )
}

export default ReservaRegister