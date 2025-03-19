
import { useNavigate } from "react-router-dom"

const Menu = () => {

    const navigate = useNavigate()

    const irReservas = () => {
        navigate("/menu/reservas")
    }

    const irAmbientes = () => {
        navigate("/menu/ambientes")
    }

    return(
        <div className="mx-auto flex flex-col h-96">
            <h2 className="text-4xl font-bold text-sky-600 text-md mb-12 text-center">MENU PRINCIPAL - LURIN BOOKING</h2>
            <div className="flex flex-row mx-auto">
                <button className="mx-3 rounded-lg bg-sky-400 p-6 text-white hover:bg-sky-600 hover:cursor-pointer" type="button" onClick={()=>irReservas()}>RESERVAS</button>
                <button className="mx-3 rounded-lg bg-sky-400 p-6 text-white hover:bg-sky-600 hover:cursor-pointer" type="button" onClick={()=>irAmbientes()}>AMBIENTES</button>
            </div>
        </div>
    ) 
}

export default Menu