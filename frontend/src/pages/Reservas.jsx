/* eslint-disable react-hooks/exhaustive-deps */
import { useState,useEffect } from "react";
import { useNavigate } from "react-router-dom"
import clienteAxios from "../config/clienteAxios";
import useAuth from "../hooks/useAuth";
import useAmbiente from "../hooks/useAmbiente";
import { Link } from "react-router-dom";
//import useReserva from "../hooks/useReserva";

const Reservas = () => {

    const navigate = useNavigate();
    // Inputs de busqueda por campos
    const [inputTipoPago,setInputTipoPago] = useState('')  
    const [inputTipoAmbiente,setInputTipoAmbiente] = useState('')
    const [inputFechaCreacion,setInputFechaCreacion] = useState('')
    // Inputs de reservas para actualizar
    const [inpTipoAmb,setInpTipoAmb] = useState('')
    const [inpTipoPago,setInpTipoPago] = useState('')
    const [inpFechaCreacion,setInpFechaCreacion] = useState('')
    
    const [reservasFound,setReservasFound] = useState([])
    const [idSelected,setIdSelected] = useState(0)
    const [selectedIdAmbientes,setSelectedIdAmbientes] = useState([])
    
    const {auth} = useAuth()
    const {ambientes} = useAmbiente()

    useEffect(() => {
        
        const cargaReservas = async () => {
            const token = localStorage.getItem('token')
            const config = {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                }
            }
            console.log(auth)
            const {data} = await clienteAxios(`/reservas/usuario/${auth.id}`,config)
            setReservasFound(data)
        }
        cargaReservas()
    },[])

    const irRegistrarReserva = () => {
        navigate("/menu/reservas/register")
    }

    const buscaReservas = async () => {
        // se deben filtrar los criterios en base solo a las reservas del usuario autenticado
        const token = localStorage.getItem('token')
        const config = {
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            }
        }
        const {data} = await clienteAxios.post("/reservas/buscarReservasXCriterios/",{tipoPago:inputTipoPago || null,tipoAmbReserva:inputTipoAmbiente || null,fechaCreacion:inputFechaCreacion || null,usuario_id:auth.id},config)
        if (Array.isArray(data)) {
            setReservasFound(data)
        } else {
            setReservasFound([]) 
        }
    }

    const eliminar = async (id) => {
        try {
            const token = localStorage.getItem('token')
            const config = {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                }
            }
            await clienteAxios.delete(`/reservas/${id}`,config)
            setReservasFound(prevReservas => 
                prevReservas.filter(reserva => reserva.reservaId !== id)
            )
        } catch (error) {
            console.log("Error al eliminar la reserva:", error.response ? error.response.data : error.message);
        }
    }

    const editar = (objReserva) => {
        setIdSelected(objReserva.reservaId)
        setSelectedIdAmbientes(objReserva.ambientes.map(item => item.ambienteId))
        setInpTipoAmb(objReserva.tipoAmbReserva)
        setInpTipoPago(objReserva.tipoPago)
        setInpFechaCreacion(objReserva.fechaCreacion)
    }

    const handleCheckboxChange = (id) => {
        setSelectedIdAmbientes((idPrevSelected) => {
            if (idPrevSelected.includes(id)) {
                return idPrevSelected.filter((item) => item !== id);
            } else {
                return [...idPrevSelected, id];
            }
        });
    }

    const actualizar = async (id) => {
        try {
            const token = localStorage.getItem('token')
            const config = {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`
                }
            }
            const {data} = await clienteAxios.put(`/reservas/${id}`,{tipoAmbReserva:inpTipoAmb,tipoPago:inpTipoPago,fechaCreacion:inpFechaCreacion,ambientes_id:selectedIdAmbientes},config)
            setReservasFound(prevReservasFound =>
                prevReservasFound.map(reserva =>
                    reserva.reservaId === data.reservaId ? data : reserva
                )
            );
            setIdSelected(0)
        } catch (error) {
            console.log(error)
        }
    }

    const cancelar = () => {
        setIdSelected(0)
    }

    return(
        <div className="flex flex-col">
            <div className="flex flex-row justify-center items-stretch mb-6">
                <h2 className="text-4xl font-bold text-sky-600 text-md mb-12 text-center">RESERVAS</h2>
                <button className="ml-24 p-2 text-white rounded-lg bg-blue-400 text-center hover:cursor-pointer hover:bg-blue-600" type="button" onClick={() => irRegistrarReserva()}>NUEVA RESERVA</button>
            </div>
            <div className="bg-white py-6">
                <h2 className="text-lg text-sky-600 font-bold text-center mb-6 mt-3">CRITERIOS DE BUSQUEDA</h2>
                <div className="flex flex-row justify-center items-center">
                    <div className="flex flex-row mx-3 items-center">
                        <label>Tipo de Pago:</label>
                        <input className="mx-1 rounded-md bg-gray-100 p-2" type='text' onChange={(e)=>setInputTipoPago(e.target.value)}/>
                    </div>
                    <div className="flex flex-row mx-3 items-center">
                        <label>Tipo de Ambiente:</label>
                        <input className="mx-1 rounded-md bg-gray-100 p-2" type='text' onChange={(e)=>setInputTipoAmbiente(e.target.value)}/>
                    </div>
                    <div className="flex flex-row mx-3 items-center">
                        <label>Fecha de Creacion:</label>
                        <input className="mx-1 rounded-md bg-gray-100 p-2" type='text' onChange={(e)=>setInputFechaCreacion(e.target.value)}/>
                    </div>
                    <div>
                        <button className="mx-2 rounded-lg bg-sky-400 p-3 text-white hover:bg-sky-600 hover:cursor-pointer" type="button" onClick={()=>buscaReservas()}> Buscar reservas</button>
                    </div>
                </div>
            </div>
            <div className="my-6">
            {reservasFound.map(reserva => {
                const isEditing = reserva.reservaId === idSelected; // Verifica si es el elemento en edición
            return (
            <div key={reserva.reservaId} className="flex flex-row my-6 mx-auto items-center justify-center">
                {isEditing ? (
                <>
                    <input className="text-md mx-6" value={inpTipoAmb} onChange={(e) => setInpTipoAmb(e.target.value)} />
                    <input className="text-md mx-6" value={inpTipoPago} onChange={(e) => setInpTipoPago(e.target.value)} />
                    <input className="text-md mx-6" value={inpFechaCreacion} onChange={(e) => setInpFechaCreacion(e.target.value)} />
                    <div className="flex flex-col">
                        {ambientes.map((amb) => (
                            <div key={amb.ambienteId} className="my-2">
                                <label className="flex items-center px-4 py-2 hover:bg-gray-100">
                                    <input
                                        type="checkbox"
                                        checked={selectedIdAmbientes.includes(amb.ambienteId)}
                                        onChange={() => handleCheckboxChange(amb.ambienteId)}
                                        className="mr-2"
                                    />
                                    {amb.name}
                                </label>
                            </div>
                        ))}
                    </div>
                    <div className="mx-2">
                        <button className="bg-sky-400 hover:bg-sky-600 hover:cursor-pointer p-2 text-white rounded-lg mx-3" type="button" onClick={() => actualizar(reserva.reservaId)}>Actualizar</button>
                        <button className="bg-yellow-400 hover:bg-yellow-600 hover:cursor-pointer p-2 text-white rounded-lg mx-3" type="button" onClick={() => cancelar()}>Cancelar</button>
                    </div>
                </>
            ) : (
                <>
                    <Link className="flex flex-row" to={`/menu/reservas/${reserva.reservaId}`}>
                    <p className="text-md mx-6">{reserva.tipoAmbReserva}</p>
                    <p className="text-md mx-6">{reserva.tipoPago}</p>
                    <p className="text-md mx-6">{reserva.fechaCreacion}</p>
                    <div className="flex flex-col">
                        {reserva.ambientes.map((amb) => (
                            <div key={amb.ambienteId} className="my-2">
                                <p className="text-black font-bold text-center text-md">{amb.name}</p>
                            </div>
                        ))}
                    </div>
                    <div className="mx-2">
                        <button className="bg-green-400 hover:bg-green-600 hover:cursor-pointer p-2 text-white rounded-lg mx-3" type="button" onClick={() => editar(reserva)}>Editar</button>
                        <button className="bg-red-400 hover:bg-red-600 hover:cursor-pointer p-2 text-white rounded-lg mx-3" type="button" onClick={() => eliminar(reserva.reservaId)}>Eliminar</button>
                    </div>
                    </Link>
                </>
            )}
                </div>
            );
        })}
        </div>
    </div>
    )
}

export default Reservas