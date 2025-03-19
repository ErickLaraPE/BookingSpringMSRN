import { useContext } from "react";
import AmbienteContext from "../context/AmbienteProvider";

const useAmbiente = () => {
    return useContext(AmbienteContext)
}

export default useAmbiente