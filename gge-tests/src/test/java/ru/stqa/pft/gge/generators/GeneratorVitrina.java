package ru.stqa.pft.gge.generators;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.gge.model.GeneratorData;
import ru.stqa.pft.gge.tests.TestBase;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by avdeev on 04.04.2016.
 */

public class GeneratorVitrina {

    public static void main(String[] args) throws Exception {
        int countRazd = Integer.parseInt(args[0]);
        File filePath = new File(args[1]);
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        Writer writer = new FileWriter(filePath);
        Clicker clicker = new Clicker();
        clicker.setUp();
        List<GeneratorData> genData = clicker.GenParam(countRazd);
        clicker.tearDown();
        writer.write(gson.toJson(genData));
        writer.close();
    }
}
