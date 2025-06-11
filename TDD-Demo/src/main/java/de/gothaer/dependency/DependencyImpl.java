package de.gothaer.dependency;

public class DependencyImpl implements Dependency {

    @Override
    public void consumer(int a) {
        System.out.println(a);
    }

    @Override
    public int supplier() {
        return 42;
    }

    @Override
    public int function(String a) {
        return a.length();
    }
}
