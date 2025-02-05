

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    public static void main(String[] args) {
        // Criando uma lista de funcionários
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 Inserir os funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 - Remover o funcionário "João"
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários com suas informações
        imprimirFuncionarios(funcionarios);

        // 3.4 - Aumento de 10% no salário
        aumentarSalarios(funcionarios, 10);

        // 3.5 - Agrupar os funcionários por função
        Map<String, List<Funcionario>> agrupadosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 - Imprimir os funcionários agrupados por função
        System.out.println("\nFuncionários agrupados por função:");
        agrupadosPorFuncao.forEach((funcao, lista) -> {
            System.out.println(funcao + ":");
            lista.forEach(f -> System.out.println(f.getNome()));
        });

        // 3.8 - Imprimir funcionários com aniversário nos meses 10 e 12
        imprimirAniversariantes(funcionarios, 10, 12);

        // 3.9 - Imprimir o funcionário com a maior idade
        imprimirFuncionarioMaisVelho(funcionarios);

        // 3.10 - Imprimir a lista de funcionários por ordem alfabética
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));
        System.out.println("\nFuncionários por ordem alfabética:");
        funcionarios.forEach(f -> System.out.println(f.getNome()));

        // 3.11 - Imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = totalSalarios(funcionarios);
        System.out.println("\nTotal dos salários: " + formatarMoeda(totalSalarios));

        // 3.12 - Imprimir quantos salários mínimos ganha cada funcionário
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(f -> {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(f.getNome() + " ganha " + salariosMinimos + " salários mínimos.");
        });
    }

    // Método para imprimir todos os funcionários
    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Funcionario f : funcionarios) {
            System.out.println(f.getNome() + ", " + f.getDataNascimento().format(formatter) + ", Salário: "
                    + formatarMoeda(f.getSalario()) + ", Função: " + f.getFuncao());
        }
    }

    // Método para formatar o salário
    private static String formatarMoeda(BigDecimal valor) {
        return valor.setScale(2, BigDecimal.ROUND_HALF_UP).toString().replace(".", ",");
    }

    // Método para aumentar os salários
    private static void aumentarSalarios(List<Funcionario> funcionarios, double percentual) {
        for (Funcionario f : funcionarios) {
            BigDecimal aumento = f.getSalario().multiply(BigDecimal.valueOf(percentual / 100));
            f.setSalario(f.getSalario().add(aumento));
        }
    }

    // Método para imprimir aniversariantes de outubro e dezembro
    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int... meses) {
        System.out.println("\nFuncionários que fazem aniversário nos meses 10 e 12:");
        for (Funcionario f : funcionarios) {
            if (Arrays.stream(meses).anyMatch(m -> f.getDataNascimento().getMonthValue() == m)) {
                System.out.println(f.getNome() + " - " + f.getDataNascimento());
            }
        }
    }

    // Método para imprimir o funcionário com a maior idade
    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparingInt(f -> f.getDataNascimento().getYear()))
                .orElseThrow(NoSuchElementException::new);
        System.out.println("\nFuncionário com a maior idade: " + maisVelho.getNome() + " - " +
                (LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear()) + " anos.");
    }

    // Método para calcular o total dos salários
    private static BigDecimal totalSalarios(List<Funcionario> funcionarios) {
        return funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
