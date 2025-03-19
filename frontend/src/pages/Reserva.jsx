/* eslint-disable react-hooks/exhaustive-deps */
import { useEffect, useState } from "react"
import clienteAxios from "../config/clienteAxios"
import { useParams } from "react-router-dom"

const Reserva = () => {

    const { id } = useParams()
    const [total,setTotal] = useState(0)
    const [reserva,setReserva] = useState({})

    useEffect(()=>{
        const totalyReserva = async () => {
            try {
                const token = localStorage.getItem('token')
                const config = {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${token}`
                    }
                }
                const result = await clienteAxios.get(`/reservas/calculaTotal/${id}`,config)
                setTotal(result.data.total)
                const result2 = await clienteAxios.get(`/reservas/${id}`,config)
                setReserva(result2.data)
            } catch (error) {
                console.log(error)
            }
        }
        totalyReserva()
    },[])

    return(
        <div className="mx-auto flex flex-col justify-center items-center">
            <h2 className="text-md text-center text-black">Reserva ID: <span className=" mx-2 text-md text-sky-500 font-bold">{reserva.reservaId}</span></h2>
            <div className="my-2 flex flex-row justify-center items-center">
                <label>Tipo de Ambiente: </label>
                <p className="text-md text-sky-500 font-bold mx-2">{reserva.tipoAmbReserva}</p>
            </div>
            <div className="my-2 flex flex-row justify-center">
                <label>Tipo de Pago: </label>
                <p className="text-md text-sky-500 font-bold mx-2">{reserva.tipoPago}</p>
            </div>
            <div className="my-2 flex flex-row justify-center">
                <label>Fecha de Creacion: </label>
                <p className="text-md text-sky-500 font-bold mx-2">{reserva.fechaCreacion}</p>
            </div>
            <div className="mt-12 flex flex-row justify-center">
                <label className="text-4xl font-bold">TOTAL: </label>
                <p className="text-4xl text-sky-500 font-bold mx-2">S/. {total}</p>
            </div>
        </div>
    )
}

export default Reserva