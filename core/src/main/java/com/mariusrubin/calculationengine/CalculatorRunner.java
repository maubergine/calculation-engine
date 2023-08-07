package com.mariusrubin.calculationengine;

import com.mariusrubin.calculationengine.calc.DefaultTaxCalculator;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * CLI class for executing the calculation engine.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class CalculatorRunner implements Callable<Integer> {

  private static final String YAML_EXTENSION = "yml";

  @Parameters(index = "0",
              description = "The directory containing tax scenarios",
              defaultValue = "./scenarios")
  private Path scenarioDir;

  @Option(names = {"-o", "--output-file"},
          description = "The path to the file into which to output the results, default: ${DEFAULT-VALUE}")
  private Path output = Paths.get("./output.txt").toAbsolutePath();

  @Option(names = {"-r", "--rates"},
          description = "The tax year you want to use to run the numbers, default: ${DEFAULT-VALUE}")
  private UkTaxRates rate = UkTaxRates.current();


  public static void main(String[] args) throws IOException {
    final int exitCode = new CommandLine(new CalculatorRunner()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws Exception {
    final var calculator = new DefaultTaxCalculator(rate);

    try (final var files = Files.walk(scenarioDir).sequential();
         final var out = new PrintStream(Files.newOutputStream(output,
                                                               StandardOpenOption.CREATE))) {

      files.filter(Files::isRegularFile)
           .filter(file -> file.toFile().getName().endsWith(YAML_EXTENSION))
           .sorted()
           .peek(System.out::print)
           .peek(file -> System.out.println())
           .forEach(file -> {
             final var loader = new YamlFileLoader(file);
             final var payer  = loader.taxPayer();
             final var result = calculator.calculate(payer);
             out.println(file.toAbsolutePath());
             out.println();
             CalcPrinter.print(result, out);
           });

    } catch (final IOException e) {
      e.printStackTrace();
      return 1;
    }
    return 0;
  }

}
