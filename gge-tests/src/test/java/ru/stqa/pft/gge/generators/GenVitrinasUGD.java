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
public class GenVitrinasUGD extends TestBase {
  @Parameter(names = "-c", description = "Razdel count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  //public String baseUrl = "https://eis.gge.ru/auth/login.action";
  public String baseUrl = "http://ugd-test.mos.ru/authS2/dualAuthPage.action";
  public String listVitrinasUrl =
          "http://ugd-test.mos.ru/ugd/tabInfo.action?tab=CARD$PORTAL_TREE&documentId=#tab::id=0/card::cardId=CARD$PORTAL_TREE";
  public String loginUser = "OstashenkoAE";


  public static String project = "UGD";

  public static void main(String[] args) throws Exception {
    GenVitrinasUGD generator = new GenVitrinasUGD();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex) {
      jCommander.usage();
      return;
    }

    //generator.setUpGGEMGE("i.manylov", "Ukfdujc21", "https://eis.gge.ru/auth/login.action");
    generator.setUpUGD("OstashenkoAE",
            "1234qwer",
            true,
            "http://ugd-test.mos.ru/authS2/dualAuthPage.action",
            "http://ugd-test.mos.ru/ugd/tabInfo.action?tab=CARD$PORTAL_TREE&documentId=#tab::id=0/card::cardId=CARD$PORTAL_TREE");
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
    if (baseUrl.equals("http://ugd-test.mos.ru/authS2/dualAuthPage.action")) {
      isProdServer = true;
    }

    if (project.equals("UGD")) {
      vitrinas = app.vitrinagenUGD().GenParamUGD(count, isProdServer, loginUser, baseUrl, listVitrinasUrl);
    }
    return vitrinas;
  }
}
