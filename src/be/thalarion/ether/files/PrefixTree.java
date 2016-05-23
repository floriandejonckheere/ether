package be.thalarion.ether.files;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Filesystem tree
 * 
 * @author florian
 */
public class PrefixTree {
    
    private final Set<TreeNode> roots;
    
    public PrefixTree() { roots = new HashSet<>(); }
    
    public void add(File... files) {
        for (File file: files)
            roots.add(new TreeNode(file));
    }
    
    public void printTree() {
        for (TreeNode node: roots)
            node.print("");
    }
    
}
