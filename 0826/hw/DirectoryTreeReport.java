//課堂作業一：檔案系統統計
//指定檔名：DirectoryTreeReport.java

import java.util.ArrayList;
import java.util.List;

class FileSystemNode {
    String name;
    boolean directory;
    long fileSize;
    List<FileSystemNode> children;

    FileSystemNode(String name, long fileSize) {
        this.name = name;
        this.directory = false;
        this.fileSize = Math.max(0, fileSize);
        this.children = new ArrayList<>();
    }

    FileSystemNode(String name) {
        this.name = name;
        this.directory = true;
        this.fileSize = 0;
        this.children = new ArrayList<>();
    }

    boolean addChild(FileSystemNode child) {
        if (!directory || child == null) {
            return false;
        }

        children.add(child);
        return true;
    }
}

public class DirectoryTreeReport {
    private static long totalSize(FileSystemNode node) {
        if (node == null) {
            return 0;
        }

        if (!node.directory) {
            return node.fileSize;
        }

        long total = 0;

        for (FileSystemNode child : node.children) {
            total += totalSize(child);
        }

        return total;
    }

    private static int totalNodeCount(FileSystemNode node) {
        if (node == null) {
            return 0;
        }

        int total = 1;

        for (FileSystemNode child : node.children) {
            total += totalNodeCount(child);
        }

        return total;
    }

    private static int fileCount(FileSystemNode node) {
        if (node == null) {
            return 0;
        }

        if (!node.directory) {
            return 1;
        }

        int total = 0;

        for (FileSystemNode child : node.children) {
            total += fileCount(child);
        }

        return total;
    }

    private static int directoryCount(FileSystemNode node) {
        if (node == null) {
            return 0;
        }

        int total = node.directory ? 1 : 0;

        for (FileSystemNode child : node.children) {
            total += directoryCount(child);
        }

        return total;
    }

    private static int height(FileSystemNode node) {
        if (node == null) {
            return -1;
        }

        int maxChildHeight = -1;

        for (FileSystemNode child : node.children) {
            maxChildHeight = Math.max(maxChildHeight, height(child));
        }

        return 1 + maxChildHeight;
    }

    private static FileSystemNode largestFile(FileSystemNode node) {
        if (node == null) {
            return null;
        }

        if (!node.directory) {
            return node;
        }

        FileSystemNode largest = null;

        for (FileSystemNode child : node.children) {
            FileSystemNode candidate = largestFile(child);

            if (candidate != null
                    && (largest == null
                    || candidate.fileSize > largest.fileSize)) {
                largest = candidate;
            }
        }

        return largest;
    }

    private static void printDirectorySizes(FileSystemNode node,
                                            String path) {
        if (node == null || !node.directory) {
            return;
        }

        String currentPath = path + node.name + "/";

        for (FileSystemNode child : node.children) {
            printDirectorySizes(child, currentPath);
        }

        System.out.println(currentPath + " total=" + totalSize(node));
    }

    private static FileSystemNode buildFileSystem() {
        FileSystemNode root = new FileSystemNode("root");

        FileSystemNode project = new FileSystemNode("project");
        FileSystemNode assets = new FileSystemNode("assets");
        FileSystemNode documents = new FileSystemNode("documents");

        root.addChild(new FileSystemNode("report.pdf", 120));
        root.addChild(new FileSystemNode("photo.jpg", 250));
        root.addChild(project);
        root.addChild(documents);

        project.addChild(new FileSystemNode("Main.java", 30));
        project.addChild(new FileSystemNode("data.csv", 80));
        project.addChild(assets);

        assets.addChild(new FileSystemNode("logo.png", 40));

        documents.addChild(new FileSystemNode("notes.txt", 15));
        documents.addChild(new FileSystemNode("slides.pptx", 300));

        return root;
    }

    public static void main(String[] args) {
        FileSystemNode root = buildFileSystem();

        System.out.println("directory totals");
        printDirectorySizes(root, "");

        FileSystemNode largest = largestFile(root);

        System.out.println();
        System.out.println("totalNodes=" + totalNodeCount(root));
        System.out.println("fileCount=" + fileCount(root));
        System.out.println("directoryCount=" + directoryCount(root));
        System.out.println("height=" + height(root));
        System.out.println("totalSize=" + totalSize(root));

        if (largest != null) {
            System.out.println("largestFile="
                    + largest.name + ", size=" + largest.fileSize);
        } else {
            System.out.println("largestFile=null");
        }
    }
}
