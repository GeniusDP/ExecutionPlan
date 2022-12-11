package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) {
    Map<Integer, Set<Integer>> map = new HashMap<>();

    try (
      FileInputStream fis = new FileInputStream("src/main/resources/input.txt");
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
      throw new RuntimeException(e);
    }

    map.forEach((key, value) -> System.out.println(key + " --> " + value));
    List<Integer> executionPlan = createExecutionPlan(map);
    System.out.println("executionPlan = " + executionPlan);
  }

  private static List<Integer> createExecutionPlan(Map<Integer, Set<Integer>> map) {
    List<Integer> executionPlan = new ArrayList<>();
    List<Integer> independentVertexes = getIndependentVertexes(map);
    Set<Integer> used = new HashSet<>();
    for (int vertex : independentVertexes) {
      executionPlan.addAll(traverse(vertex, map, used));
    }
    return executionPlan;
  }

  private static List<Integer> traverse(int vertex, Map<Integer, Set<Integer>> map, Set<Integer> used) {
    List<Integer> result = new ArrayList<>();
    if (used.contains(vertex)) {
      return Collections.emptyList();
    }
    Set<Integer> deps = map.get(vertex);
    for (int dep : deps) {
      result.addAll(traverse(dep, map, used));
    }
    used.add(vertex);
    result.add(vertex);
    return result;
  }

  // O( sum([dependencies]) )
  private static List<Integer> getIndependentVertexes(Map<Integer, Set<Integer>> map) {
    List<Integer> independentVertexes = new ArrayList<>();
    Set<Integer> dependencyVertexes = map.values().stream()
      .flatMap(Collection::stream)
      .collect(Collectors.toSet());

    for (int vertex : map.keySet()) {
      if (!dependencyVertexes.contains(vertex)) {
        independentVertexes.add(vertex);
      }
    }
    return independentVertexes;
  }

}