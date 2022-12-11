package org.example;

import org.example.controllers.Controller;
import org.example.controllers.ExecutionPlanBuilderController;
import org.example.dao.DependencyDao;
import org.example.dao.DependencyFileDao;
import org.example.services.ExecutionPlanBuilderService;
import org.example.views.ExecutionPlanBuilderView;

public class Main {

  public static void main(String[] args) {
    ExecutionPlanBuilderView view = new ExecutionPlanBuilderView();
    DependencyDao reader = new DependencyFileDao();
    ExecutionPlanBuilderService service = new ExecutionPlanBuilderService();
    Controller controller = new ExecutionPlanBuilderController(view, reader, service);
    controller.start();
  }

}