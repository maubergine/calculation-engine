package com.mariusrubin.calculationengine;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

/**
 * @author Marius Rubin
 * @since 0.1.0
 */
public class ACalculatorRunner {

  @Test
  public void shouldPrintHelpText() {
    final var command = new CommandLine(new CalculatorRunner());
    final var writer  = new StringWriter();
    command.setOut(new PrintWriter(writer));

    int exitCode = command.execute("-h");

    final var textOutput = writer.toString();

    assertThat(textOutput).contains(
        "Usage: calculation-engine-core [-h] [-o=<output>] [-r=<rate>] <scenarioDir>");

    assertThat(exitCode).isEqualTo(0);

  }

}