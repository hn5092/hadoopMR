package com.x.math.binarytree;

public class BinaryTree {
  private Node root;

  /**
   * @param key 值
   * @return key
   */
  public Node find(int key) {
    Node currNode = root;
    while (currNode.getId() != key) {
      if (currNode.getId() > key) {
        currNode = currNode.getLeftNode();
      } else {
        currNode = currNode.getRightNode();
      }
    }
    return currNode;
  }

  public void insert(final int id, final int data) {
    if (root == null) {
      root = new Node(id, data);
      return;
    }
    Node currNode = root;
    while (true) {
      Node parent = currNode;
      if (currNode.getId() > id) {
        currNode = currNode.getLeftNode();
        if (currNode == null) {
          parent.setLeftNode(new Node(id, data));
          return;
        }
      } else {
        currNode = currNode.getRightNode();
        if (currNode == null) {
          parent.setRightNode(new Node(id, data));
          return;
        }
      }
    }
  }

  public void preOrder(Node node) {
    if (node != null) {
      System.out.println(node.getId());
      preOrder(node.getLeftNode());
      preOrder(node.getRightNode());
    }
  }

  public void inOrder(Node node) {
    if (node != null) {
      inOrder(node.getLeftNode());
      System.out.println(node.getId());
      inOrder(node.getRightNode());
    }
  }

  public void postOrder(Node node) {
    if (node != null) {
      postOrder(node.getLeftNode());
      postOrder(node.getRightNode());
      System.out.println(node.getId());
    }
  }

  public Node maxNode() {
    Node currNode = root;
    while (currNode.getRightNode() != null) {
      currNode = currNode.getRightNode();
    }
    return currNode;
  }

  public Node minNode() {
    Node currNode = root;
    while (currNode.getLeftNode() != null) {
      currNode = currNode.getLeftNode();
    }
    return currNode;
  }

  public boolean delete(int key) {
    // 1找到节点
    if (root.getId() == key) {
      root = null;
      return true;
    }
    Node currentNode = root;
    boolean isLeft = false;
    Node parentNode = null;

    while (currentNode.getId() != key) {
      parentNode = currentNode;
      if (currentNode.getId() > key) {
        isLeft = true;
        currentNode = currentNode.getLeftNode();

      } else {
        isLeft = false;
        currentNode = currentNode.getRightNode();
      }
    }
    System.out.println(currentNode);
    System.out.println(parentNode);
    // 2没有子节点
    if (currentNode.getLeftNode() == null && currentNode.getRightNode() == null) {
     
    } else if (currentNode.getLeftNode() == null) {

    }
    // 3只有做节点
    // 4只有右节点
    // 5 有两个节点

    return false;
  }
  
  public static void main(String[] args) {
    BinaryTree bTree = new BinaryTree();
    bTree.insert(6, 1);
    bTree.insert(5, 8);
    bTree.insert(8, 9);
    bTree.insert(3, 7);
    bTree.insert(4, 5);
    bTree.insert(7, 1);
    System.out.println(bTree.root);
    System.out.println(bTree.minNode());
    bTree.delete(8);
    bTree.postOrder(bTree.root);

  }
}
