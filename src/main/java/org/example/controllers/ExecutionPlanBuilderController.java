package org.example.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dao.DependencyDao;
import org.example.models.DependencyGraph;
import org.example.services.ExecutionPlanBuilderService;
import org.example.views.ExecutionPlanBuilderView;

@RequiredArgsConstructor
public class ExecutionPlanBuilderController implements Controller {

  private final ExecutionPlanBuilderView view;
  private final DependencyDao dao;
  private final ExecutionPlanBuilderService service;

  @Override
  public void start() {
    DependencyGraph dependencyGraph = dao.readDependencyGraph();
    view.printDependencyGraph(dependencyGraph);

    List<Integer> executionPlan = service.getExecutionPlan(dependencyGraph);
    view.printExecutionPlan(executionPlan);
  }

}
