package com.yc;

import org.ycframework.annotation.YcComponentScan;
import org.ycframework.annotation.YcConfiguration;
import org.ycframework.annotation.YcPropertySource;

@YcConfiguration
@YcComponentScan(basePackages = {"com.yc"})
@YcPropertySource(value = "db.properties")
public class MyConfig {
}
