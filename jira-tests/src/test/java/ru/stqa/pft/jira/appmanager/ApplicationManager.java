package ru.stqa.pft.jira.appmanager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Юрий on 09.04.2016.
 */
public class ApplicationManager {

  private final Properties properties;

  public ApplicationManager(String browser) {
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
  }

  public void stop() {
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

}
