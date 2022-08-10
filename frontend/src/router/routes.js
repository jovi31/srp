import Index from '../pages/Index'
import Error404 from '../pages/Error404'

import MainLayout from '../layouts/MainLayout'
import AdminLayout from '../layouts/AdminLayout'

import Recarga from '../pages/UserPages/Recarga'
import Saque from '../pages/UserPages/Saque'
import Agendamento from '../pages/UserPages/Agendamento'
import CadastroCartao from '../pages/UserPages/CadastroCartao'
import CadastroConta from '../pages/UserPages/CadastroConta'
import Recargas from '../pages/UserPages/Recargas'
import Saques from '../pages/UserPages/Saques'
import Agendamentos from '../pages/UserPages/Agendamentos'
import Cartoes from '../pages/UserPages/Cartoes'
import Contas from '../pages/UserPages/Contas'

import CadastroUsuario from '../pages/AdminPages/CadastroUsuario'
import CadastroEmpresa from '../pages/AdminPages/CadastroEmpresa'
import CadastroCidade from '../pages/AdminPages/CadastroCidade'
import CadastroUf from '../pages/AdminPages/CadastroUF'

import RecargasEmpresas from '../pages/AdminPages/Relatorios/RecargasEmpresas'
import SaquesEmpresas from '../pages/AdminPages/Relatorios/SaquesEmpresas'
import EmpresasCidades from '../pages/AdminPages/Relatorios/EmpresasCidades'
import RecargasPorCliente from '../pages/AdminPages/Relatorios/RecargasPorCliente'
import SaquesPorCliente from '../pages/AdminPages/Relatorios/SaquesPorCliente'

import Login from '../pages/Login'
import Cadastro from '../pages/Cadastro'

const routes = [
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: '', component: Index },
      { path: 'recarga', component: Recarga },
      { path: 'saque', component: Saque },
      { path: 'agendamento', component: Agendamento },
      { name: 'cadastroCartao', path: 'cadastroCartao', component: CadastroCartao, props: true },
      { name: 'cadastroConta', path: 'cadastroConta', component: CadastroConta, props: true },
      { path: 'recargas', component: Recargas },
      { path: 'saques', component: Saques },
      { path: 'agendamentos', component: Agendamentos },
      { path: 'cartoes', component: Cartoes },
      { path: 'contas', component: Contas }
    ]
  },

  {
    path: '/admin',
    component: AdminLayout,
    children: [
      { path: '', component: Index },
      { path: 'cadastroUsuario', component: CadastroUsuario },
      { path: 'cadastroEmpresa', component: CadastroEmpresa },
      { path: 'cadastroCidade', component: CadastroCidade },
      { path: 'cadastroUf', component: CadastroUf },

      { path: 'relatorios/recargasEmpresas', component: RecargasEmpresas },
      { path: 'relatorios/saquesEmpresas', component: SaquesEmpresas },
      { path: 'relatorios/empresasCidades', component: EmpresasCidades },
      { path: 'relatorios/recargasPorCliente', component: RecargasPorCliente },
      { path: 'relatorios/saquesPorCliente', component: SaquesPorCliente }
    ]
  },

  { path: '/auth', component: Login },

  { path: '/cadastro', component: Cadastro },

  { path: '*', component: Error404 }
]

export default routes
