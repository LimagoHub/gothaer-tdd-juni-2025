package de.gothaer.service;

import de.gothaer.dependency.Dependency;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ServiceUsingDependency {

    private final Dependency dependency;

    public void foo() {
        dependency.consumer(5);
    }

    public int bar() {
        return dependency.supplier() * 10;

    }

    public int foobar() {
        return dependency.function("Hallo") + dependency.function("Hallo");
    }
}
