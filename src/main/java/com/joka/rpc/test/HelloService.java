package com.joka.rpc.test;

public interface HelloService {
    String hello(String name);

    String hello(Person person);
}
