package org.example.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.models.DependencyGraph;

public class ExecutionPlanBuilderService {

  public List<Integer> getExecutionPlan(DependencyGraph dependencyGraph) {
    Map<Integer, Set<Integer>> dependencies = dependencyGraph.getDependencies();
    List<Integer> executionPlan = new ArrayList<>();
    List<Integer> independentVertexes = getIndependentVertexes(dependencies);
    Set<Integer> used = new HashSet<>();
    for (int vertex : independentVertexes) {
      executionPlan.addAll(traverse(dependencies, vertex, used));
    }
    return executionPlan;
  }

  private List<Integer> traverse(Map<Integer, Set<Integer>> map, int vertex, Set<Integer> used) {
    List<Integer> result = new ArrayList<>();
    if (used.contains(vertex)) {
      return Collections.emptyList();
    }
    Set<Integer> deps = map.get(vertex);
    for (int dep : deps) {
      result.addAll(traverse(map, dep, used));
    }
    used.add(vertex);
    result.add(vertex);
    return result;
  }


  // O( sum([dependencies]) )
  private List<Integer> getIndependentVertexes(Map<Integer, Set<Integer>> map) {
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
