package ru.stqa.pft.gge.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.gge.model.GeneratorData;
import ru.stqa.pft.gge.tests.TestBase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuhin on 21.04.2016.
 */
public class GenVitrinas extends TestBase {
  @Parameter(names = "-c", description = "Razdel count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws Exception {
    GenVitrinas generator = new GenVitrinas();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }

    generator.setUp2("i.manylov", "Ukfdujc21", "https://eis.gge.ru/auth/login.action");
    generator.run();
    generator.tearDown();
  }

  private void run() throws IOException, InterruptedException {
    List<GeneratorData> vitrinas = generateVitrinasData(count);
    if (format.equals("xml")) {
      saveAsXml(vitrinas, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(vitrinas, new File(file));
    } else {
      System.out.println("Unrecognized format " + format);
    }
  }

  private void saveAsJson(List<GeneratorData> vitrinas, File file) throws IOException {
    Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation().create();
    String json = gson.toJson(vitrinas);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void saveAsXml(List<GeneratorData> vitrinas, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(GeneratorData.class);
    String xml = xstream.toXML(vitrinas);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  private List<GeneratorData> generateVitrinasData(int count) throws InterruptedException {
    System.out.println(new File(".").getAbsolutePath());
    List<GeneratorData> vitrinas = new ArrayList<GeneratorData>();
    vitrinas = app.vitrinagen().GenParam(count);
    return vitrinas;
  }
}
