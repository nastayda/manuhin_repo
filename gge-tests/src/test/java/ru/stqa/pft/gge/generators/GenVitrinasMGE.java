package ru.stqa.pft.gge.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.gge.model.GeneratorData;
import ru.stqa.pft.gge.tests.TestBase;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by manuhin on 21.04.2016.
 */
public class GenVitrinasMGE extends TestBase {
  @Parameter(names = "-c", description = "Razdel count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  //public String baseUrl = "https://eis.gge.ru/auth/login.action";
  //public String baseUrl = "https://test-eis.gge.ru/auth/login.action";
  //public String baseUrl = "https://vm-082-as-gge.mdi.ru/auth/login.action";
//  public String baseUrl = "https://vm-085-as-gge.mdi.ru/auth/login.action";
//  public String loginUser = "i.manylov";

//  public String baseUrl = "https://vm-097-tomcat-mge.mdi.ru:8443/auth/login.action";
//  public String baseUrl = "https://vm-086-as-mge.mdi.ru:8443/auth/login.action";
  public String baseUrl = "https://expertiza.mos.ru:8443/auth/login.action";
  public String loginUser = "galactica_admin1";


  public static String project = "MGE";

  public static void main(String[] args) throws Exception {
    GenVitrinasMGE generator = new GenVitrinasMGE();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }

    //generator.setUpGGEMGE("i.manylov", "Ukfdujc21", "https://eis.gge.ru/auth/login.action");
    //generator.setUpGGEMGE("i.manylov", "21", "https://test-eis.gge.ru/auth/login.action");
    //generator.setUpGGEMGE("i.manylov", "21", "https://vm-082-as-gge.mdi.ru/auth/login.action");
    //generator.setUpGGEMGE("i.manylov", "Ukfdujc21", "https://vm-085-as-gge.mdi.ru/auth/login.action");
//    generator.setUpGGEMGE("galactica_admin1", "21", "https://vm-097-tomcat-mge.mdi.ru:8443/auth/login.action");
//    generator.setUpGGEMGE("galactica_admin1", "21", "https://vm-086-as-mge.mdi.ru:8443/auth/login.action");
    generator.setUpMGE("adminMGE", "Qwerty391628", "https://expertiza.mos.ru/authS1/login.action");
    generator.run(project);
    generator.tearDown();
  }

  private void run(String project) throws IOException, InterruptedException {
    List<GeneratorData> vitrinas = generateVitrinasData(project, count);
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

  private List<GeneratorData> generateVitrinasData(String project, int count) throws InterruptedException {
    System.out.println(new File(".").getAbsolutePath());
    List<GeneratorData> vitrinas = new ArrayList<GeneratorData>();

    boolean isProdServer = false;
    if (baseUrl.equals("https://eis.gge.ru/auth/login.action") ||
            baseUrl.equals("https://vm-085-as-gge.mdi.ru/auth/login.action")) {
      isProdServer = true;
    }

    if (project.equals("MGE")) {
      vitrinas = app.vitrinagenMGE().GenParam2(count, isProdServer, loginUser, baseUrl);
    } else {
      vitrinas = app.vitrinagenMGE().GenParam(count, isProdServer, loginUser, baseUrl);
    }
    return vitrinas;
  }
}
