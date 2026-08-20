//課堂實作題三：匯出與壓縮能力
//指定檔名：`DocumentCapabilityDemo.java`

//完成標準：能說明兩個 reference 指向同一物件，但可見 method 不同。

interface Exportable {
    String export();
}

interface Compressible {
    byte[] compress();
}

class BackupDocument implements Exportable, Compressible {
    private String name;
    private String content;

    BackupDocument(String name, String content) {
        if (name == null) {
            this.name = "Untitled";
        } else {
            String t = name.trim();
            this.name = t.length() == 0 ? "Untitled" : t;
        }
        this.content = (content == null) ? "" : content;
    }

    @Override
    public String export() {
        // 名稱:內容
        return name + ":" + content;
    }

    @Override
    public byte[] compress() {
        // 把內容手動轉成小寫，再轉成 bytes
        char[] arr = content.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (c >= 'A' && c <= 'Z') {
                arr[i] = (char) (c - 'A' + 'a');
            }
        }
        String lowered = new String(arr);
        return lowered.getBytes();
    }
}

public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        BackupDocument doc = new BackupDocument("Report", "Hello OOP");
        Exportable e = doc;
        Compressible c = doc;

        System.out.println("export=" + e.export());
        System.out.println("compress bytes=" + c.compress().length);
    }
}
