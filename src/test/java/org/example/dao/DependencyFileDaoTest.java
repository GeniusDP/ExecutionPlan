package org.example.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.example.exceptions.InputFileNotFoundException;
import org.example.models.DependencyGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("file dependency dao testing")
class DependencyFileDaoTest {

  Map<Integer, Set<Integer>> sampleDependencies;
  String sourceDirectoryPathPrefix;


  @BeforeEach
  public void init() {
    sampleDependencies = new HashMap<>();
    sampleDependencies.put(1, Set.of(5, 2));
    sampleDependencies.put(2, Set.of(4, 3));
    sampleDependencies.put(3, Collections.emptySet());
    sampleDependencies.put(4, Set.of(5));
    sampleDependencies.put(5, Set.of(3));
    sampleDependencies.put(6, Set.of(7));
    sampleDependencies.put(7, Collections.emptySet());
    sampleDependencies.put(8, Set.of(2));
    sourceDirectoryPathPrefix = "src/test/resources/";
  }


  @Test
  @DisplayName("read ok")
  public void testDefaultReading() {
    DependencyDao dao = new DependencyFileDao(sourceDirectoryPathPrefix + "input0.txt");
    DependencyGraph provided = dao.readDependencyGraph();
    DependencyGraph expected = new DependencyGraph(sampleDependencies);
    Assertions.assertThat(provided).isEqualTo(expected);
  }


  @Test
  @DisplayName("input file not exists")
  public void testInputFileNotFound() {
    DependencyDao dao = new DependencyFileDao(sourceDirectoryPathPrefix + "non-existing-file.txt");
    org.junit.jupiter.api.Assertions.assertThrows(InputFileNotFoundException.class, dao::readDependencyGraph);
  }

}