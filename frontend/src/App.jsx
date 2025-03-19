import { BrowserRouter as Router,Routes,Route } from "react-router-dom"
import { AuthLayout } from "./layouts/AuthLayout"
import RutaProtegida from "./layouts/RutaProtegida"
import AmbientesRegister from "./pages/AmbienteRegister"
import Ambientes from "./pages/Ambientes"
import Login from "./pages/Login"
import Menu from "./pages/Menu"
import Register from "./pages/Register"
import ReservaRegister from "./pages/ReservaRegister"
import Reservas from "./pages/Reservas"
import { AuthProvider } from "./context/AuthProvider"
import { ReservaProvider } from "./context/ReservaProvider"
import { AmbienteProvider } from "./context/AmbienteProvider"
import Reserva from "./pages/Reserva"

function App() {
  
  return (
    <Router>
        <AuthProvider>
          <ReservaProvider>
            <AmbienteProvider>
              <Routes>
                <Route path="/" element={<AuthLayout/>}>
                  <Route index element={<Login/>}/>
                  <Route path="register" element={<Register/>}/>
                </Route>
                <Route path="/menu" element={<RutaProtegida/>}>
                  <Route index element={<Menu/>}/>
                  <Route path="reservas" element={<Reservas/>}/>
                  <Route path="reservas/:id" element={<Reserva/>}/>
                  <Route path="reservas/register" element={<ReservaRegister/>}/>
                  <Route path="ambientes" element={<Ambientes/>}/>
                  <Route path="ambientes/register" element={<AmbientesRegister/>}/>
                </Route>
              </Routes>
            </AmbienteProvider>
          </ReservaProvider>
        </AuthProvider>
    </Router>
  )
}

export default App
