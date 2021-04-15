package com.turtle.amatda;

import org.junit.Test;

public class ComponentTest {

    @Test
    public void testSingletonComponent() {
        Component_A component = DaggerComponent_A.factory()
                .create(19,"sung dae kyoung");

        System.out.println("person.name: " + component.callPerson().name + " / person.age: " + component.callPerson().age);


    }

}
