package ru.stqa.pft.gge.model;

import com.google.gson.annotations.Expose;

/**
 * Created by manuhin on 09.08.2016.
 */
public class UpLoadFileData {
  @Expose
  private String id;

  @Expose
  private String filePath;

  @Expose
  private String name;

  @Expose
  private String size;

  public String getId() {
    return id;
  }

  public String getFilePath() {
    return filePath;
  }

  public String getName() {
    return name;
  }

  public String getSize() {
    return size;
  }

  public UpLoadFileData withId(String id) {
    this.id = id;
    return this;
  }

  public UpLoadFileData withFilePath(String filePath) {
    this.filePath = filePath;
    return this;
  }

  public UpLoadFileData withName(String name) {
    this.name = name;
    return this;
  }

  public UpLoadFileData withSize(String size) {
    this.size = size;
    return this;
  }
}
