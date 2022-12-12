package org.example.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.example.models.DependencyGraph;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("execution plan builder service testing")
class ExecutionPlanBuilderServiceTest {

  ExecutionPlanBuilderService service = new ExecutionPlanBuilderService();

  @ParameterizedTest
  @DisplayName("all cases")
  @MethodSource("testParameters")
  public void allCasesTestMethod(DependencyGraph graph) {
    List<Integer> providedExecutionPlan = service.getExecutionPlan(graph);
    Map<Integer, Set<Integer>> dependenciesGraph = graph.getDependencies();

    for(int scriptId: dependenciesGraph.keySet()){
      Set<Integer> deps = dependenciesGraph.get(scriptId);
      boolean result = scriptDependsOnItsDependencies(scriptId, deps, providedExecutionPlan);
      Assertions.assertThat(result).isTrue();
    }

  }

  private boolean scriptDependsOnItsDependencies(int scriptId, Set<Integer> dependencies, List<Integer> executionPlan) {
    for (int dependency : dependencies) {
      int indexOfScriptId = executionPlan.indexOf(scriptId);
      int indexOfDependency = executionPlan.indexOf(dependency);
      if(indexOfDependency > indexOfScriptId){
        return false;
      }
    }
    return true;
  }

  private static Stream<Arguments> testParameters() {
    return Stream.of(
      Arguments.arguments(exampleDependencyGraph1()),
      Arguments.arguments(exampleDependencyGraph2()),
      Arguments.arguments(exampleDependencyGraph3())
    );
  }

  private static DependencyGraph exampleDependencyGraph1() {
    Map<Integer, Set<Integer>> dependencies = new HashMap<>();
    dependencies.put(1, Set.of(5, 2));
    dependencies.put(2, Set.of(4, 3));
    dependencies.put(3, Collections.emptySet());
    dependencies.put(4, Set.of(5));
    dependencies.put(5, Set.of(3));
    dependencies.put(6, Set.of(7));
    dependencies.put(7, Collections.emptySet());
    dependencies.put(8, Set.of(2));
    return new DependencyGraph(dependencies);
  }

  private static DependencyGraph exampleDependencyGraph2() {
    Map<Integer, Set<Integer>> dependencies = new HashMap<>();
    dependencies.put(1, Set.of(2, 3));
    dependencies.put(2, Set.of(4, 5));
    dependencies.put(3, Set.of(4));
    dependencies.put(4, Set.of(5));
    dependencies.put(5, Collections.emptySet());
    return new DependencyGraph(dependencies);
  }

  private static DependencyGraph exampleDependencyGraph3() {
    Map<Integer, Set<Integer>> dependencies = new HashMap<>();
    dependencies.put(1, Set.of(100, 3, -500, 5));
    dependencies.put(100, Set.of(3, 5));
    dependencies.put(3, Set.of(-500, 5));
    dependencies.put(-500, Collections.emptySet());
    dependencies.put(5, Set.of(-500));
    return new DependencyGraph(dependencies);
  }
}