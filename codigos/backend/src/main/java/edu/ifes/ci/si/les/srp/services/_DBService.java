package edu.ifes.ci.si.les.srp.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.Agendamento;
import edu.ifes.ci.si.les.srp.model.CartaoCredito;
import edu.ifes.ci.si.les.srp.model.CartaoInteligente;
import edu.ifes.ci.si.les.srp.model.CartaoInteligentePK;
import edu.ifes.ci.si.les.srp.model.Cidade;
import edu.ifes.ci.si.les.srp.model.Conta;
import edu.ifes.ci.si.les.srp.model.Empresa;
import edu.ifes.ci.si.les.srp.model.Recarga;
import edu.ifes.ci.si.les.srp.model.Saque;
import edu.ifes.ci.si.les.srp.model.Status;
import edu.ifes.ci.si.les.srp.model.TipoConta;
import edu.ifes.ci.si.les.srp.model.TipoPeriodo;
import edu.ifes.ci.si.les.srp.model.UF;
import edu.ifes.ci.si.les.srp.model.Usuario;
import edu.ifes.ci.si.les.srp.repositories.AgendamentoRepository;
import edu.ifes.ci.si.les.srp.repositories.CartaoInteligenteRepository;
import edu.ifes.ci.si.les.srp.repositories.CidadeRepository;
import edu.ifes.ci.si.les.srp.repositories.ContaRepository;
import edu.ifes.ci.si.les.srp.repositories.EmpresaRepository;
import edu.ifes.ci.si.les.srp.repositories.RecargaRepository;
import edu.ifes.ci.si.les.srp.repositories.SaqueRepository;
import edu.ifes.ci.si.les.srp.repositories.UFRepository;
import edu.ifes.ci.si.les.srp.repositories.UsuarioRepository;

@Service
public class _DBService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	@Autowired
	private CartaoInteligenteRepository cartaoInteligenteRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private RecargaRepository recargaRepository;
	@Autowired
	private SaqueRepository saqueRepository;
	@Autowired
	private UFRepository ufRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		UF uf1 = new UF("ES", "Espírito Santo");
		UF uf2 = new UF("RJ", "Rio de Janeiro");
		
		Cidade cidade1 = new Cidade(null, "Cachoeiro de Itapemirim", uf1);
		Cidade cidade2 = new Cidade(null, "Rio de Janeiro", uf2);
		
		Empresa empresa1 = new Empresa(null, "Empresa01", "11111111111111", Arrays.asList(cidade1));
		Empresa empresa2 = new Empresa(null, "Empresa02", "22222222222222", Arrays.asList(cidade2));
		Empresa empresa3 = new Empresa(null, "Empresa03", "33333333333333", Arrays.asList(cidade1, cidade2));
		Empresa empresa4 = new Empresa(null, "Empresa04", "44444444444444", Arrays.asList(cidade1, cidade2));
		
		Usuario administrador = new Usuario(null, "Admin", "11111111111", "11", 
				"111111111", "admin@mail.com", "admin012", true);
		
		Usuario cliente1 = new Usuario(null, "Usuario01", "22222222222", "22", 
				"222222222", "usuario01@mail.com", "01234567", false);
		Usuario cliente2 = new Usuario(null, "Usuario02", "33333333333", "33", 
				"333333333", "usuario02@mail.com", "12345678", false);
		
		CartaoInteligentePK pk1 = new CartaoInteligentePK("00000000001", empresa1);
		CartaoInteligente cartaoInteligente1 = new CartaoInteligente(pk1, "Cartão01", (float) 25, cliente1);
		
		CartaoInteligentePK pk2 = new CartaoInteligentePK("00000000002", empresa1);
		CartaoInteligente cartaoInteligente2 = new CartaoInteligente(pk2, "Cartão01", (float) 20, cliente2);
		
		CartaoInteligentePK pk3 = new CartaoInteligentePK("00000000001", empresa2);
		CartaoInteligente cartaoInteligente3 = new CartaoInteligente(pk3, "Cartão02", (float) 0, cliente2);
		
		Conta conta1 = new Conta(null, "Usuario01", "22222222222", "001", "0001", 
				"123456789", TipoConta.CORRENTE, cliente1);
		
		CartaoCredito cartaoCredito1 = new CartaoCredito(0, "USUARIO01", "1111111111111", "06", 2023, "001");
		CartaoCredito cartaoCredito2 = new CartaoCredito(0, "USUARIO02", "2222222222222", "12", 2023, "002");
		CartaoCredito cartaoCredito3 = new CartaoCredito(0, "USUARIO02", "3333333333333", "03", 2022, "003");
		
		Recarga recarga1 = new Recarga(null, sdf.parse("2020-05-10"), Status.EFETIVADO, 
				(float) 20, cartaoInteligente1, cartaoCredito1);
		Recarga recarga2 = new Recarga(null, sdf.parse("2020-06-10"), Status.EFETIVADO, 
				(float) 25, cartaoInteligente1, cartaoCredito1);
		Recarga recarga3 = new Recarga(null, sdf.parse("2020-05-17"), Status.EFETIVADO, 
				(float) 20, cartaoInteligente2, cartaoCredito2);
		Recarga recarga4 = new Recarga(null, sdf.parse("2020-06-17"), Status.PENDENTE, 
				(float) 40, cartaoInteligente3, cartaoCredito3);
		
		Agendamento agendamento1 = new Agendamento(null, (float) 20, sdf.parse("2020-06-17"), 1, TipoPeriodo.MENSAL, 
				cartaoInteligente1, cartaoCredito1);
		Agendamento agendamento2 = new Agendamento(null, (float) 10, sdf.parse("2020-06-17"), 2, TipoPeriodo.SEMANAL, 
				cartaoInteligente3, cartaoCredito2);
		
		Saque saque1 = new Saque(null, sdf.parse("2020-05-12"), Status.EFETIVADO, (float) 20, conta1, cartaoInteligente1);
		Saque saque2 = new Saque(null, sdf.parse("2020-06-17"), Status.PENDENTE, (float) 25, conta1, cartaoInteligente1);
		
		ufRepository.saveAll(Arrays.asList(uf1, uf2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2));
		empresaRepository.saveAll(Arrays.asList(empresa1, empresa2, empresa3, empresa4));
		usuarioRepository.saveAll(Arrays.asList(administrador, cliente1, cliente2));
		cartaoInteligenteRepository.saveAll(Arrays.asList(cartaoInteligente1, cartaoInteligente2, cartaoInteligente3));
		contaRepository.save(conta1);
		recargaRepository.saveAll(Arrays.asList(recarga1, recarga2, recarga3, recarga4));
		agendamentoRepository.saveAll(Arrays.asList(agendamento1, agendamento2));
		saqueRepository.saveAll(Arrays.asList(saque1, saque2));
	}
}