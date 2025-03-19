
import { useNavigate } from "react-router-dom"
import useAmbiente from "../hooks/useAmbiente"

const Ambientes = () => {

    const navigate = useNavigate()
    const {ambientes} = useAmbiente()

    const irRegistrarAmbiente = () => {
        navigate("/menu/ambientes/register")
    }

    return(
            <div className="flex flex-col">
                <div className="flex flex-row justify-center items-center my-3">
                    <h2 className="text-4xl font-bold text-sky-600 text-md mb-12 text-center">AMBIENTES</h2>
                    <button className="mx-3 p-3 text-white rounded-lg bg-blue-400 hover:cursor-pointer hover:bg-blue-600 text-center" type="button" onClick={() => irRegistrarAmbiente()}>NUEVO AMBIENTE</button>
                </div>
                {ambientes.map(ambiente => {
                    return(
                        <div className="mx-auto grid grid-cols-5 grid-rows-2 gap-1 justify-center items-center">
                            <div className="flex flex-col items-center">
                                <p className="text-black text-md font-bold my-2">{ambiente.name}</p>
                                <p className="text-black text-md my-2">{ambiente.price}</p>
                            </div>
                        </div>
                    )
                })}
            </div>    
    )
}

export default Ambientes