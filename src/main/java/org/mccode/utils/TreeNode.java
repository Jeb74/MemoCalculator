package org.mccode.utils;

public class TreeNode<T> {
    private TreeNode<T> left = null;
    private TreeNode<T> right = null;
    private final T value;
    private final int precedence;

    public TreeNode(T value, int precedence) {
        this.value = value;
        this.precedence = precedence;
    }

    public T getValue() {
        return value;
    }
    public int getPrecedence() { return precedence; }
    public TreeNode<T> getLeft() {
        return left;
    }
    public TreeNode<T> getRight() {
        return right;
    }
    public void setLeft(TreeNode<T> node) {
        left = node;
    }
    public void setRight(TreeNode<T> node) {
        right = node;
    }
}
