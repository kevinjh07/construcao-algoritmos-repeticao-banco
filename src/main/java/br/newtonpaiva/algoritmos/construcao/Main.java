package br.newtonpaiva.algoritmos.construcao;

import java.util.Scanner;

public class Main {
	
	private static final int CREDITAR = 1;
	private static final int DEBITAR = 2;
	private static final int TRANSFERIR = 3;
	private static final int SALDO = 4;
	private static final int SAIR = 5;
	
	private static final int CONTA_CORRENTE = 1;
	private static final int CONTA_POUPANCA = 2;
	
	private static final int TRANSFERENCIA_CORRENTE_POUPANCA = 1;
	private static final int TRANSFERENCIA_POUPANCA_CORRENTE = 2;
	
	private static String nomeCliente;
	private static String numeroContaCorrente;
	private static String numeroContaPoupanca;
	private static double saldoContaCorrente;
	private static double saldoContaPoupanca;
	
	private static Scanner sc;

	public static void main(String[] args) {
		getDadosUsuarios();

		int opcao = 0;
		do {
			sc = new Scanner(System.in);
			opcao = getOpcaoSelecionada(sc);
			switch (opcao) {
			case CREDITAR:
				creditarValorConta();
				break;
			case DEBITAR:
				debitarValorConta();
				break;
			case TRANSFERIR:
				fazerTransferencia();
				break;
			case SALDO:
				mostrarSaldo();
				break;
			case SAIR:
				mostrarMensagem("\nObrigado por trabalhar com o nosso banco!!!");
				break;
			default:
				mostrarMensagem("Opção inválida!");
			}
		} while (opcao != SAIR);
	}

	private static void getDadosUsuarios() {
		sc = new Scanner(System.in);
		mostrarMensagem("Entre com o nome do cliente:");
		nomeCliente = sc.nextLine();
		
		mostrarMensagem("Entre com o número da Conta Corrente:");
		numeroContaCorrente = sc.nextLine();
		
		mostrarMensagem(String.format("Entre com o saldo inicial da Conta Corrente %s", numeroContaCorrente));
		saldoContaCorrente = sc.nextDouble();
		
		sc = new Scanner(System.in);
		mostrarMensagem("Entre com o número da Conta Poupança:");
		numeroContaPoupanca = sc.nextLine();
		
		mostrarMensagem(String.format("Entre com o saldo inicial da Conta Poupança %s", numeroContaPoupanca));
		saldoContaPoupanca = sc.nextDouble();
	}
	
	private static int getOpcaoSelecionada(Scanner sc) {
		StringBuilder builder = new StringBuilder()
			.append("\nQual operação deseja realizar:")
			.append("\n1 – Creditar")
			.append("\n2 – Debitar")
			.append("\n3 – Transferir")
			.append("\n4 - Saldo")
			.append("\n5 – Sair");
		mostrarMensagem(builder.toString());
		
		return sc.nextInt();
	}
	
	private static void creditarValorContaCorrente() {
		mostrarMensagem(String.format("Qual valor a creditar na conta corrente %s", numeroContaCorrente));
		double valor = sc.nextDouble();
		saldoContaCorrente += valor;
		mostrarMensagem(String.format("\nSaldo atual na conta corrente %s", saldoContaCorrente));
	}
	
	private static void creditarValorContaPoupanca() {
		mostrarMensagem(String.format("Qual valor a creditar na conta poupança %s", numeroContaCorrente));
		double valor = sc.nextDouble();
		saldoContaPoupanca += valor;
		mostrarMensagem(String.format("Saldo atual na conta poupança %s", saldoContaPoupanca));
	}
	
	private static void debitarValorContaCorrente() {
		mostrarMensagem(String.format("Qual valor a debitar na conta corrente %s", numeroContaCorrente));
		double valor = sc.nextDouble();
		saldoContaCorrente -= valor;
		mostrarMensagem(String.format("\nSaldo atual na conta corrente %s", saldoContaCorrente));
	}
	
	private static void debitarValorContaPoupanca() {
		mostrarMensagem(String.format("Qual valor a debitar na conta poupança %s", numeroContaCorrente));
		double valor = sc.nextDouble();
		if ((saldoContaPoupanca - valor) < 0) {
			mostrarMensagem("\nO valor da conta poupança não pode ficar negativo!");
			return;
		}
		saldoContaPoupanca -= valor;
		mostrarMensagem(String.format("\nSaldo atual na conta poupança %s", saldoContaPoupanca));
	}
	
	private static void fazerTransferencia() {
		int conta = getContaTransferencia();
		switch (conta) {
			case TRANSFERENCIA_CORRENTE_POUPANCA:
				transferenciaCorrentePoupanca();
				break;
			case TRANSFERENCIA_POUPANCA_CORRENTE:	
			transferenciaPoupancaCorrente();
				break;
			default:
				break;
		}
		StringBuilder builder = new StringBuilder()
				.append(String.format("\nSaldo atual na Conta Corrente %s -> %s\n", numeroContaCorrente, saldoContaCorrente))
				.append(String.format("\nSaldo atual na Conta Poupança %s -> %s\n", numeroContaPoupanca, saldoContaPoupanca));
		mostrarMensagem(builder.toString());
	}
	
	private static void transferenciaCorrentePoupanca() {
		mostrarMensagem(String.format("\nQual valor a transferir da CC %s para CP %s?", numeroContaCorrente, numeroContaPoupanca));
		double valorContaCorrente = sc.nextDouble();
		if (saldoContaCorrente < 0) {
			mostrarMensagem("\nSó é possível realizar transferências com saldo positivo!");
			return;
		}
		if (valorContaCorrente > saldoContaCorrente) {
			mostrarMensagem("\nO valor da transferência não pode ser maior que o saldo!");
			return;
		}
		saldoContaCorrente -= valorContaCorrente;
		saldoContaPoupanca += valorContaCorrente;
	}

	private static void transferenciaPoupancaCorrente() {
		mostrarMensagem(String.format("\nQual valor a transferir da CP %s para CC %s?", numeroContaCorrente, numeroContaPoupanca));
		double valorContaPoupanca = sc.nextDouble();
		if (valorContaPoupanca > saldoContaPoupanca) {
			mostrarMensagem("O valor da transferência não pode ser maior que o saldo!");
			return;
		}
		saldoContaPoupanca -= valorContaPoupanca;
		saldoContaCorrente += valorContaPoupanca;
	}

	private static void mostrarSaldo() {
		StringBuilder builder = new StringBuilder()
			.append(String.format("\nSenhor(a) %s", nomeCliente))
			.append(String.format("\nSaldo da sua Conta Corrente %s: %s", numeroContaCorrente, saldoContaCorrente))
			.append(String.format("\nSaldo da sua Conta Poupança %s: %s", numeroContaPoupanca, saldoContaPoupanca))
			.append(String.format("\nSaldo total no Banco: %s\n", saldoContaCorrente + saldoContaPoupanca));
		mostrarMensagem(builder.toString());
	}

	private static void creditarValorConta() {
		int opcao = getConta();
		switch(opcao) {
			case CONTA_CORRENTE:
				creditarValorContaCorrente();
				break;
			case CONTA_POUPANCA:
				creditarValorContaPoupanca();
				break;
			default:
				break;
		}
	}
	
	private static void debitarValorConta() {
		int opcao = getConta();
		switch(opcao) {
			case CONTA_CORRENTE:
				debitarValorContaCorrente();
				break;
			case CONTA_POUPANCA:
				debitarValorContaPoupanca();
				break;
			default:
				break;
		}
	}
	
	private static void mostrarMensagem(String mensagem) {
		System.out.printf(String.format("%s\n", mensagem));
	}

	private static int getConta() {
		StringBuilder builder = new StringBuilder()
				.append("\nQual conta?")
				.append("\n1 - Corrente")
				.append("\n2 - Poupança");
		mostrarMensagem(builder.toString());
		return sc.nextInt();
	}
	
	private static int getContaTransferencia() {
		StringBuilder builder = new StringBuilder()
				.append("\nEntre:")
				.append("\n1 - Conta corrente e poupança")
				.append("\n2 - Poupança e conta corrente");
		mostrarMensagem(builder.toString());
		return sc.nextInt();
	}

}
