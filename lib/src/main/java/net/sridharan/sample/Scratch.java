// Copyright 2024 Canva Inc. All Rights Reserved.

package net.sridharan.sample;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Scratch {
  public record Foo(@Nullable String bar, String baz){}
  record Qux(String a, String b){}
  record Transformer(){
    public static Qux transform(String bar) {
      var split = bar.split("-");
      return new Qux(split[0], split[1]);
    }
  }

  // Not fine here, but fine in my environment
  void streams() {
    List<Foo> foos = List.of(new Foo("a-b", "c"));
    foos.stream()
            .filter(f -> f.bar != null)
            // NullAway complains about nullability of bar even though bar is null checked
            .collect(Collectors.toMap(f -> Transformer.transform(f.bar), f -> f.baz));
  }

  // Fine in this environment but not in mine
  // In my env, Nullaway complains about the nullability of bar in collect as above
  void streams2() {
    List<Foo> foos = List.of(new Foo("a-b", "c"));
    foos.stream()
            .filter(f -> f.bar != null)
            .collect(Collectors.toMap(f -> f.bar, f -> f.baz));
  }
}
