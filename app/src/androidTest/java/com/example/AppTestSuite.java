package com.example;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HomeActivityTest.class,
        SplashActivityTest.class,
        DetailActivityTest.class
})
public class AppTestSuite {
}