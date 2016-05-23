package be.thalarion.ether.files;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.control.TreeItem;

/**
 * Filesystem tree node
 * 
 * @author florian
 */
public class TreeNode {
    
    private final File file;
    private final Set<TreeNode> children;
    
    public TreeNode(File node) {
        this.file = node;
        children = new HashSet<>();
        
        if (node.isDirectory()) {
            System.out.println(node.getName() + " is a directory");
            for (File file: node.listFiles()) {
                System.out.println("Recursing into " + file.getName());
                children.add(new TreeNode(file));
            }
        } else System.out.println(node.getName() + " is a file");
    }
    
    public File getNode() { return file; }
    
    public boolean isFile() { return file.isFile(); }
    public boolean isDirectory() { return file.isDirectory(); }
    
    public void print(String prefix) {
        System.out.println(prefix + file.getName());
        for (TreeNode node: children)
            node.print(prefix + " ├─ ");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        final TreeNode other = (TreeNode) obj;
        return file.equals(other);
    }
    
}
