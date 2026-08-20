//課後作業三：媒體檔案處理
//指定檔名：`MediaProcessingSystem.java`
//建立 abstract `MediaFile`，subclass 至少包含 `ImageFile`、`AudioFile`、`VideoFile`。另建立 `Playable`、`Compressible` 等 interface，由適合的 subclass 實作。輸出每個物件支援的操作結果。

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
