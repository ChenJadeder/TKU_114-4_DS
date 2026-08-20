//課後作業四：報表輸出 Factory

//指定檔名：`ReportExporterFactory.java`

// 報表輸出 Factory：以 interface + factory + 多型實作不同輸出格式

interface ReportExporter {
    String export(String title, int[] values);
    String format();
}

class CsvExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        String t = (title == null || title.trim().isEmpty()) ? "Untitled" : title.trim();
        int[] data = (values == null) ? new int[0] : values;
        StringBuilder sb = new StringBuilder();
        sb.append(t);
        for (int i = 0; i < data.length; i++) {
             sb.append(",").append(data[i]);
        }
        return sb.toString();     }

    @Override
    public String format() {
        return "csv";
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        String t = (title == null || title.trim().isEmpty()) ? "Untitled" : title.trim();
        int[] data = (values == null) ? new int[0] : values;
        StringBuilder sb = new StringBuilder();
        sb.append("{\"title\":\"").append(escape(t)).append("\",\"values\":[");
        for (int i = 0; i < data.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(data[i]);
        }
        sb.append("]}");
        return sb.toString(); 
    }

    private String escape(String s) {
        // 轉義魷魚絲
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '"' || c == '\\') {
                out.append('\\');
            }
            out.append(c);
        }
        return out.toString();
    }

    @Override
    public String format() {
        return "json";
    }
}

class TextExporter implements ReportExporter {
    @Override
    public String export(String title, int[] values) {
        String t = (title == null || title.trim().isEmpty()) ? "Untitled" : title.trim();
        int[] data = (values == null) ? new int[0] : values;
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(t).append("\nValues: ");
        if (data.length == 0) {
            sb.append("(none)");
        } else {
            for (int i = 0; i < data.length; i++) {
                if (i > 0) sb.append(" ");
                sb.append(data[i]);
            }
        }
        return sb.toString();
    }

    @Override
    public String format() {
        return "text";
    }
}

public class ReportExporterFactory {
    static ReportExporter createExporter(String format) {
        String f = (format == null) ? "" : format.trim().toLowerCase();
        switch (f) {
            case "csv":  return new CsvExporter();
            case "json": return new JsonExporter();
            case "text": return new TextExporter();
            default:     return new TextExporter(); // 不支援格式改為 Text
        }
    }

    // 只依賴介面，不用 instanceof
    static String exportReport(ReportExporter exporter, String title, int[] values) {
        return exporter.export(title, values);
    }

    public static void main(String[] args) {
        ReportExporter csv = createExporter("csv");
        ReportExporter json = createExporter("json");
        ReportExporter fallback = createExporter("yaml"); // 不支援 轉為 Text

        System.out.println("--- CSV ---");
        System.out.println(exportReport(csv, "Sales", new int[]{10, 20, 30}));

        System.out.println("--- JSON ---");
        System.out.println(exportReport(json, "Inventory", new int[]{5, 0}));

        System.out.println("--- TEXT (nulls) ---");
        System.out.println(exportReport(fallback, null, null));
    }
}