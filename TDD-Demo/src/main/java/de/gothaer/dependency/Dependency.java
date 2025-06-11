package de.gothaer.dependency;

public interface Dependency {
    void consumer(int a);

    int supplier();

    int function(String a);
}
