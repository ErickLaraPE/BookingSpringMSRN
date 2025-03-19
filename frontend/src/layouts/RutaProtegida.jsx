
import useAuth from "../hooks/useAuth"
import Header from "../components/Header"
import Footer from "../components/Footer"
import { Navigate,Outlet } from "react-router-dom"

const RutaProtegida = () => {
    
    const { auth, cargando } = useAuth()
    
    if(cargando) return 'Cargando...'
    
    return(
        <div>
            {auth.id ?
               (
                <div className='bg-gray-100 top-0'>
                    <Header />
                        <main className='p-10 flex-1'>
                            <Outlet/>
                        </main>
                    <Footer/>
                </div>
               ) : <Navigate to="/" />}
        </div>
    )
}
export default RutaProtegida
