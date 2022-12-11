package org.example.dao;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.exceptions.InputFileNotFoundException;
import org.example.models.DependencyGraph;


public class DependencyFileDao implements DependencyDao {

  private final String filePath;

  public DependencyFileDao() {
    Properties properties = new Properties();
    try {
      String propertySourceFilePath = "src/main/resources/application.properties";
      properties.load(new FileReader(propertySourceFilePath));
    } catch (IOException e) {
      System.out.println("application.properties file not found. process terminated");
      System.exit(1337);
    }

    this.filePath = properties.getProperty("input-file-path");
  }

  public DependencyFileDao(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public DependencyGraph readDependencyGraph() {
    Map<Integer, Set<Integer>> map = new HashMap<>();
    try (
      FileInputStream fis = new FileInputStream(filePath);
      Scanner scanner = new Scanner(fis)
    ) {
      int numberOfVertexes = Integer.parseInt(scanner.nextLine());
      for (int i = 0; i < numberOfVertexes; i++) {
        String line = scanner.nextLine();
        String[] strings = line.split("[ \t]+");
        int vertex = Integer.parseInt(strings[0]);
        Set<Integer> integers = Arrays.stream(strings)
          .skip(1)
          .map(Integer::parseInt)
          .collect(Collectors.toSet());
        map.put(vertex, integers);
      }
    } catch (IOException e) {
      System.out.println("input file was not found");
      throw new InputFileNotFoundException(e);
    }
    return new DependencyGraph(map);
  }

}
