package com.github.utils;

public @interface HttpMethod {
  java.lang.String GET = "GET";
  java.lang.String POST = "POST";
  java.lang.String PUT = "PUT";
  java.lang.String DELETE = "DELETE";
  java.lang.String HEAD = "HEAD";
  java.lang.String OPTIONS = "OPTIONS";

  java.lang.String value();
}