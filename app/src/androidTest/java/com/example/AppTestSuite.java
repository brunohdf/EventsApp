package com.example;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SplashActivityTest.class,
        HomeActivityTest.class,
        DetailActivityTest.class
})
public class AppTestSuite {
}