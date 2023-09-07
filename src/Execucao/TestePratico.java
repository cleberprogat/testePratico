package Execucao;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import Funcionario.Funcionario;

public class TestePratico {
	
	private List<Funcionario> listaDeFuncionarios;
	
	private Funcionario funcionario;

	private Map<String, List<Funcionario>> mapFuncao;
	
	public static void main(String[] args) throws ParseException {
		new TestePratico().init();
	}
	
	public void init() throws ParseException 	{
		// Requisito 3.1. Inserindo funcionarios na tabela
		mapFuncao = new HashMap<>();
		listaDeFuncionarios = new ArrayList<Funcionario>();
		inserindoFuncionarios("Maria", "18/10/2000", "Operador", 2009.44);
		inserindoFuncionarios("João", "12/05/1990", "Operador", 2284.38);
		inserindoFuncionarios("Caio", "02/05/1961", "Coordenador", 9836.14);
		inserindoFuncionarios("Miguel", "14/10/1988", "Diretor", 19119.88);
		inserindoFuncionarios("Alice", "05/01/1995", "Recepcionista", 2234.68);
		inserindoFuncionarios("Heitor", "19/11/1999", "Operador", 1582.72);
		inserindoFuncionarios("Arthur", "31/03/1993", "Contador", 4071.84);
		inserindoFuncionarios("Laura", "08/07/1994", "Gerente", 3017.45);
		inserindoFuncionarios("Heloisa", "24/05/2003", "Eletricista", 1606.85);
		inserindoFuncionarios("Helena", "02/09/1996", "Gerente", 2799.93);
		comandoEsperar();
		
		// Requisito 3.2. Excluindo João
		excluirFuncionario("João");
		comandoEsperar();
		
		// Requisito 3.3. Imprimir todos os funcionários
		imprimirFuncionariosComFormato("3.3.");
		comandoEsperar();
		
		// Requisito 3.4. Aumentar salário em 10%
		aumentarSalario(10);
		imprimirFuncionariosComFormato("3.4.");
		comandoEsperar();
		
		// Requisito 3.5. Agrupar por função em um MAP
		agruparFuncionariosPorFuncao();
		
		// Requisito 3.6. Imprimir agrupado por Funcao
		imprimirAgrupadosPorFuncao();
		comandoEsperar();
		
		// Requisito 3.8. Imprimir os que fazem aniversario 10 e 12
		imprimirAniversariantes();
		comandoEsperar();
		
		// Requisito 3.9. Imprimir funcionario maior idade
		imprimirMaiorIdade();
		comandoEsperar();
		
		// Requisito 3.10. Imprimir funcionario Ordem Alfabética
		ordenarAlfabeticamente();
		comandoEsperar();
		
		// Requisito 3.11 Imprimir o total dos salários dos funcionários
		imprimirTotalSalarios();
		comandoEsperar();
		
		// Requisito 3.11 Imprimir Quantidade de Salarios Minimos por Funcionario
		imprimirFuncionariosSalarioMinimo(1212.00);
		comandoEsperar();
		
	}

	// Inserção de Funcionario
	private void inserindoFuncionarios(String nomeFuncionario, String dataNascimentoFuncionario, String funcaoFuncionario, double salarioFuncionario) throws ParseException {
		// inserindo dados tabela
		funcionario = new Funcionario(null, null, null, null);
		funcionario.setNome(nomeFuncionario);
		funcionario.setDataNascimento(convertStringParaLocalDate(dataNascimentoFuncionario));
		funcionario.setFuncao(funcaoFuncionario);
		funcionario.setSalario(BigDecimal.valueOf(salarioFuncionario));
		listaDeFuncionarios.add(funcionario);
		System.out.println("Requisito 3.1.Funcionario "+nomeFuncionario+ " inserido\n");
	}
	
	//Exclusão de Funcionário
	private void excluirFuncionario(String nomeExclusao) {
		for (Funcionario funcionarioExcluido: listaDeFuncionarios) {
			if(funcionarioExcluido.getNome().equalsIgnoreCase("João")) {
				listaDeFuncionarios.remove(funcionarioExcluido);
				System.out.println("Requisito 3.2.Funcionario "+nomeExclusao+" excluido\n");
				break;
			}
		}
	}
	
	// Impressão de todos os funcionários com formato
	private void imprimirFuncionariosComFormato(String requisito) {
		for (Funcionario funcionario: listaDeFuncionarios) {
			String valorFormatado = new DecimalFormat("#,##0.00").format(funcionario.getSalario());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        String hojeFormatado = funcionario.getDataNascimento().format(formatter);
			System.out.println("Requisito "+requisito+"Funcionario: "+funcionario.getNome()+
							  " Nascimento: "+hojeFormatado+
					          " Funcao: "+funcionario.getFuncao()+
					          " Salario: "+valorFormatado);
		}
		
	}

	// Aumentar Salario por Percentual
	private void aumentarSalario(double percentual) {
		for (Funcionario funcionario: listaDeFuncionarios) {
			double novoSalario = funcionario.getSalario().doubleValue() * (1+(percentual/100));
			funcionario.setSalario(BigDecimal.valueOf(novoSalario));
		}
	}
	
	// Agrupar por funcao
	private void agruparFuncionariosPorFuncao() {
		for(FuncaoType item : FuncaoType.values()){
            List<Funcionario> listaDeFuncionarioPorFuncao = listaDeFuncionarios.stream()
                    .filter(funcionario -> funcionario.getFuncao().equalsIgnoreCase(item.getDescricao()))
                    .collect(Collectors.toList());
			mapFuncao.putIfAbsent(item.getDescricao(), listaDeFuncionarioPorFuncao);
        }
	}
	
	// Impressão agrupados por função
	private void imprimirAgrupadosPorFuncao() {
		System.out.println("Imprimindo funcionarios agrupado");
		for (String funcao : mapFuncao.keySet()) {
			System.out.println("Função.:" + funcao);
			for (Funcionario item : mapFuncao.get(funcao)) {
				System.out.println("Função.: " + funcao + " - Funcionario.: " + item.getNome());
			}
		}
	}
	
	// Impressão funcionarios aniversario mes 10 ou 12
	private void imprimirAniversariantes() {
		for (Funcionario funcionario: listaDeFuncionarios) {
			if (compararMes(funcionario.getDataNascimento(),10,12) > 0) {
				System.out.println("Requisito 3.8.Funcionario: "+funcionario.getNome()+
							  " Idade: "+calculaIdade(funcionario.getDataNascimento())+
					          " Funcao: "+funcionario.getFuncao()+"\n");
			}	
		}
	}

	// Impressão maior idade
	private void imprimirMaiorIdade() {
		String nomeFuncionario = "";
		int idade = 0;
		for (Funcionario funcionario: listaDeFuncionarios) {
			int maior = calculaIdade(funcionario.getDataNascimento());
			if (maior > idade) {
				idade = maior;
				nomeFuncionario = funcionario.getNome();
			}
		}
		System.out.println("Requisito 3.9.Funcionario: "+funcionario.getNome()+
						  " Idade: "+idade);
	}
	
	// Impressão total salários
	private void imprimirTotalSalarios() {
		double total = 0;
		for (Funcionario funcionario: listaDeFuncionarios) {
			total +=  funcionario.getSalario().doubleValue();
		}
		String valorFormatado = new DecimalFormat("#,##0.00").format(total);
		System.out.println("Requisito 3.11.Total Salarios: "+valorFormatado);
	}
	
	// Impressão de Salarios Minimos por Funcionario
	private void imprimirFuncionariosSalarioMinimo(double salarioMinimo) {
		String valorFormatado1 = "";
		String valorFormatado2 = "";
		
		for (Funcionario funcionario: listaDeFuncionarios) {
			double salariosMinimos = funcionario.getSalario().doubleValue() / salarioMinimo;
			valorFormatado1 = new DecimalFormat("#,##0.00").format(funcionario.getSalario());
			valorFormatado2 = new DecimalFormat("#,##0.00").format(salariosMinimos);
			System.out.println("Requisito 3.12.Funcionario: "+funcionario.getNome()+
						  " Salario: "+valorFormatado1+
				          " Salarios Minimos: "+valorFormatado2);
		}
	}
		
	// Ordenar em Ordem alfabetica
	private void ordenarAlfabeticamente() {
		listaDeFuncionarios.sort((p1, p2) -> p1.getNome().compareTo(p2.getNome()));
		listaDeFuncionarios.forEach((Funcionario funcionario) -> { 
		      System.out.println("Requisito 3.10: "+funcionario.getNome()); 
		   }); 
	}
	
	// MÉTODOS AUXILIARES
	// comparar mes
	public static int compararMes(LocalDate d1, int mes1, int mes2) {
		Date d2 = null;
		try {
			d2 = converteLocalDateParaDate(d1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int mes = mesNascimento(d2)+1;
	
		if ((mes == mes1) || (mes == mes2)) {
			return mes;
		} else {
			return 0;
		}
	}
	
	public static int mesNascimento(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);

		return c.get(Calendar.MONTH);
	}
	
	public static Date converteLocalDateParaDate(LocalDate dataConvertida) {
	    return java.sql.Date.valueOf(dataConvertida);
	}
	
	public enum FuncaoType {

	    GERENTE("Gerente"),
	    OPERADOR("Operador"),
	    COORDENADOR("Coordenador"),
	    DIRETOR("Diretor"),
	    RECEPCIONISTA("Recepcionista");

	    String descricao;

	    FuncaoType(String descricao) {
	        this.descricao = descricao;
	    }

	    public String getDescricao() {
	        return descricao;
	    }
	}    
	    
	// Aguardar ENTER do usuario para executar próxima tarefa
	private void comandoEsperar(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pressione Enter para continuar...\n");
        scanner.nextLine(); // Aguarda até que a tecla Enter seja pressionada
        // scanner.close();
    }
	
	// Conversão de Data
	private LocalDate convertStringParaLocalDate(String dataInformada){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataInformada, formatter);

        return data;
    }
	
	public static int calculaIdade(LocalDate localDate) {
		Date dataNascimento = null;
		try {
			dataNascimento = converteLocalDateParaDate(localDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Calendar dataConversao = Calendar.getInstance();
		
		dataConversao.setTime(dataNascimento);
		Calendar hoje = Calendar.getInstance();

		int idade = hoje.get(Calendar.YEAR) - dataConversao.get(Calendar.YEAR);

		if (hoje.get(Calendar.MONTH) < dataConversao.get(Calendar.MONTH)) {
			idade--;
		} else {
			if (hoje.get(Calendar.MONTH) == dataConversao.get(Calendar.MONTH)
					&& hoje.get(Calendar.DAY_OF_MONTH) < dataConversao.get(Calendar.DAY_OF_MONTH)) {
				idade--;
			}
		}

		return idade;
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	
	public List<Funcionario> getListaDeFuncionarios() {
		return listaDeFuncionarios;
	}

	public void setListaDeFuncionarios(List<Funcionario> listaDeFuncionarios) {
		this.listaDeFuncionarios = listaDeFuncionarios;
	}

	public Map<String, List<Funcionario>> getMapFuncao() {
		return mapFuncao;
	}

	public void setMapFuncao(Map<String, List<Funcionario>> mapFuncao) {
		this.mapFuncao = mapFuncao;
	}


}