package com.mariusrubin.calculationengine;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mariusrubin.calculationengine.api.TaxPayer;
import com.mariusrubin.calculationengine.model.DefaultTaxPayer;
import com.mariusrubin.calculationengine.serde.TaxPayerInfo;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Loads taxpayer information from YAMl files.
 *
 * @author Marius Rubin
 * @since 0.1.0
 */
public class YamlFileLoader implements TaxPayerLoader {

  private final ObjectMapper mapper;
  private final Path         path;

  /**
   * Build a YAML file loader that attempts to load a taxpayer from a YAML file at the provided
   * path.
   *
   * @param path the path of the YAML file containing taxpayer info
   */
  public YamlFileLoader(final Path path) {
    mapper = new ObjectMapper(new YAMLFactory());
    this.path = path.toAbsolutePath();
  }


  /**
   * Build a YAML file loader that attempts to load a taxpayer from a YAML file at the URI (assuming
   * that URI refers to a file in the classpath).
   *
   * @param uri the URI of the YAML file containing taxpayer infos
   */
  public YamlFileLoader(final String uri) {
    this(Path.of(uri));
  }

  @Override
  public TaxPayer taxPayer() {
    try {
      return new DefaultTaxPayer(mapper.readValue(path.toFile(), TaxPayerInfo.class));
    } catch (final StreamReadException e) {
      //TODO improve exception handling
      throw new RuntimeException(e);
    } catch (final DatabindException e) {
      throw new RuntimeException(e);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}
