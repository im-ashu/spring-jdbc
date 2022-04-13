package com.learning.model;

public class Circle {

  private int id;
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Circle() {
  }

  public Circle(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "Circle{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
