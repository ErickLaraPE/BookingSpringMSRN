import { useState,createContext } from "react";

const ReservaContext = createContext()

const ReservaProvider = ({children}) => {

    const [reservas,setReservas] = useState([])
    
    return(
        <ReservaContext.Provider
            value={{
                reservas,
                setReservas
            }}
        >
            {children}
        </ReservaContext.Provider>
    )
}

    export {
        ReservaProvider
    }

export default ReservaContext