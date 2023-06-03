package org.mccode.math;

public class TreeNode<T> {
    private TreeNode<T> left = null;
    private TreeNode<T> right = null;
    private TreeNode<T> parent = null;
    private byte flag = 0;
    private final T value;

    public TreeNode(T value, byte flag) {
        this.value = value;
        this.flag = flag;
    }

    public T getValue() {
        return value;
    }
    public byte getFlag() {
        return flag;
    }
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
