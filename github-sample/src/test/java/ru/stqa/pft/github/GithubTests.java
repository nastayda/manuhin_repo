package ru.stqa.pft.github;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by Юрий on 18.04.2016.
 */
public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("e3611cc70a53d57fd53c3acf156084c840814b27");
    RepoCommits commits = github.repos().get(new Coordinates.Simple("manyurij", "java_study")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());

    }
  }
}
