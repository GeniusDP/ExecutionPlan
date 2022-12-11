package org.example.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DependencyGraph {

  private final Map<Integer, Set<Integer>> dependencies;

  public Map<Integer, Set<Integer>> getDependencies() {
    Map<Integer, Set<Integer>> deepCopy = new HashMap<>();
    dependencies.forEach((key, value) -> deepCopy.put(key, new HashSet<>(value)));
    return deepCopy;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    dependencies.forEach((key, value) -> sb.append(key).append(" --> ").append(value).append("\n"));
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj == null || obj.getClass() != this.getClass()) {
      return false;
    }
    var that = (DependencyGraph) obj;
    return Objects.equals(this.dependencies, that.dependencies);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dependencies);
  }


}
