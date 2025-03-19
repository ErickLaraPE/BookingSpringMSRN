
import { useState,createContext,useEffect } from "react";
import clienteAxios from "../config/clienteAxios";

const AmbienteContext = createContext()

const AmbienteProvider = ({children}) => {

    const [ambientes,setAmbientes] = useState([])
    
    useEffect(() => {
        const cargaAmbientes = async () => {
            try {
                const {data} = await clienteAxios("/ambientes/")
                setAmbientes(data)
            } catch (error) {
                console.log(error)
            } 
        }
        cargaAmbientes()
    },[])

    return(
        <AmbienteContext.Provider
            value={{
                ambientes,
                setAmbientes
            }}
        >
            {children}
        </AmbienteContext.Provider>
    )
}

    export {
        AmbienteProvider
    }

export default AmbienteContext