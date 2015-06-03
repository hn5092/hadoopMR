package com.x.math.binarytree;

public class Node {
  private int id;
  private int data;
  private Node leftNode;
  private Node rightNode;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getData() {
    return data;
  }

  public Node(int id, int data) {
    super();
    this.id = id;
    this.data = data;
  }

  public void setData(int data) {
    this.data = data;
  }

  public Node getLeftNode() {
    return leftNode;
  }

  public void setLeftNode(Node leftNode) {
    this.leftNode = leftNode;
  }

  public Node getRightNode() {
    return rightNode;
  }

  public void setRightNode(Node rightNode) {
    this.rightNode = rightNode;
  }

  @Override
  public String toString() {
    return "Node [id=" + id + ", data=" + data + ", leftNode=" + leftNode + ", rightNode="
        + rightNode + "]";
  }

}
