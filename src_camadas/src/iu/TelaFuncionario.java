package iu;

import java.util.Scanner;

public class TelaFuncionario {

    private Scanner scanner;
    private TelaAtendente telaAtendente;
    private TelaAssistenteFinanceiro telaAssistenteFinanceiro;
    private TelaGerente telaGerente;
    private String tokenAtendente;
    private String tokenAssistenteFinanceiro;
    private String tokenGerente;

    public TelaFuncionario() {
        scanner = new Scanner(System.in);
        tokenAtendente = "40028922";
        tokenAssistenteFinanceiro = "12345678";
        tokenGerente = "98765432";
    }
    // Aqui os tokens estão salvos em Strings.
    // Podemos salvá-los em arquivos futuramente.
    // Também não é ideal que eles estejam declarados no arquivo da tela.

    public void iniciar() {
        while (true) {
            System.out.println(">>>> DIGITE SEU TOKEN DE FUNCIONARIO <<<<");

            String token = scanner.nextLine();

            switch (token) {
                case tokenAtendente:
                    telaAtendente.iniciar(); // Implementar
                    break;
                case tokenAssistenteFinanceiro:
                    telaAssistenteFinanceiro.iniciar(); // Implementar
                    break;
                case tokenGerente:
                    telaGerente.iniciar(); // Implementar
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("<Operação inválida. Tente novamente.>");
            }
        }
    }
}