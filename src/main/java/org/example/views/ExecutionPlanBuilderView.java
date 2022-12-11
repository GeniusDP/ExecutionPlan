package org.example.views;

import java.util.List;
import org.example.models.DependencyGraph;

public class ExecutionPlanBuilderView {

  public void printDependencyGraph(DependencyGraph dependencyGraph) {
    System.out.println("Dependency graph: \n" + dependencyGraph);
  }

  public void printExecutionPlan(List<Integer> executionPlan) {
    System.out.println("executionPlan = " + executionPlan);
  }
}
