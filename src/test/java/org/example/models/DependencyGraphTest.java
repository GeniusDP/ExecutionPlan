package org.example.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DependencyGraphTest {

  @Test
  @DisplayName("check immutability")
  public void testIfGetterGivesDeepCopy() {
    Map<Integer, Set<Integer>> dependencies = new HashMap<>();
    dependencies.put(1, Set.of(5, 2));
    dependencies.put(2, Set.of(4, 3));
    dependencies.put(3, Collections.emptySet());
    dependencies.put(4, Set.of(5));
    dependencies.put(5, Set.of(3));
    dependencies.put(6, Set.of(7));
    dependencies.put(7, Collections.emptySet());
    dependencies.put(8, Set.of(2));
    DependencyGraph dependencyGraph = new DependencyGraph(dependencies);

    Assertions.assertThat(dependencies).isEqualTo(dependencyGraph.getDependencies());
    Assertions.assertThat(dependencies).isNotSameAs(dependencyGraph.getDependencies());

    for(var scriptId: dependencies.keySet()) {
      Set<Integer> setOriginal = dependencies.get(scriptId);
      Set<Integer> setFromGetter = dependencyGraph.getDependencies().get(scriptId);
      Assertions.assertThat(setOriginal).isNotSameAs(setFromGetter);
    }
  }

}