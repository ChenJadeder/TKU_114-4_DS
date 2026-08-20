// 初學者風格：抽象類 + 介面能力(Playable/Compressible) + 多型
// 思路：共同資料放 MediaFile；各子類決定 open 條件與能力；主程式以多型巡覽
// DEBUG 可切換觀察派發流程（預設關閉）
interface Playable {
    String play();
}
interface Compressible {
    String compress();
}

abstract class MediaFile {
    private String name;
    private int sizeKb;

    MediaFile(String name, int sizeKb) {
        if (name == null) {
            this.name = "Unknown";
        } else {
            String t = name.trim();
            this.name = (t.length() == 0) ? "Unknown" : t;
        }
        if (sizeKb < 0) {
            this.sizeKb = 0;
        } else {
            this.sizeKb = sizeKb;
        }
    }

    String getName() { return name; }
    int getSizeKb() { return sizeKb; }

    String info() {
        return getClass().getSimpleName() + " " + name + " size=" + sizeKb + "KB";
    }

    abstract boolean open();
}

class ImageFile extends MediaFile implements Compressible {
    private int width;
    private int height;

    ImageFile(String name, int sizeKb, int width, int height) {
        super(name, sizeKb);
        this.width = width < 0 ? 0 : width;
        this.height = height < 0 ? 0 : height;
    }

    @Override
    boolean open() {
        return getSizeKb() > 0 && width > 0 && height > 0;
    }

    @Override
    public String compress() {
        // 壓縮為70%
        int compressed = getSizeKb() * 7 / 10;
        return "compress -> " + compressed + "KB";
    }

    @Override
    public String toString() {
        return info() + " " + width + "x" + height;
    }
}

class AudioFile extends MediaFile implements Playable {
    private int durationSec;
    private int bitrateKbps;

    AudioFile(String name, int sizeKb, int durationSec, int bitrateKbps) {
        super(name, sizeKb);
        this.durationSec = durationSec < 0 ? 0 : durationSec;
        this.bitrateKbps = bitrateKbps < 0 ? 0 : bitrateKbps;
    }

    @Override
    boolean open() {
        return getSizeKb() > 0 && durationSec > 0;
    }

    @Override
    public String play() {
        return "play audio: " + getName() + " dur=" + durationSec + "s";
    }

    @Override
    public String toString() {
        return info() + " dur=" + durationSec + "s bitrate=" + bitrateKbps + "kbps";
    }
}

class VideoFile extends MediaFile implements Playable, Compressible {
    private int durationSec;
    private String resolution;
    private int fps;

    VideoFile(String name, int sizeKb, int durationSec, String resolution, int fps) {
        super(name, sizeKb);
        this.durationSec = durationSec < 0 ? 0 : durationSec;
        if (resolution == null) {
            this.resolution = "Unknown";
        } else {
            String t = resolution.trim();
            this.resolution = (t.length() == 0) ? "Unknown" : t;
        }
        this.fps = fps < 0 ? 0 : fps;
    }

    @Override
    boolean open() {
        return getSizeKb() > 0 && durationSec > 0 && fps > 0;
    }

    @Override
    public String play() {
        return "play video: " + getName() + " " + resolution + " " + fps + "fps";
    }

    @Override
    public String compress() {
        // 壓縮為60%
        int compressed = getSizeKb() * 6 / 10;
        return "compress -> " + compressed + "KB";
    }

    @Override
    public String toString() {
        return info() + " dur=" + durationSec + "s res=" + resolution + " fps=" + fps;
    }
}

public class MediaProcessingSystem {
    private static final boolean filed = false;

    public static void main(String[] args) {
        MediaFile[] files = new MediaFile[] {
            new ImageFile("photo_tku.jpg", 1200, 1920, 1080),
            new AudioFile("rickroll.mp3", 5000, 210, 320),
            new VideoFile("emotional_damage.mp4", 200000, 180, "1080p", 30),
            new ImageFile("john_cena.png", -1, 0, 0)
        };

        for (int i = 0; i < files.length; i++) {
            MediaFile f = files[i];
            if (filed) {
                System.out.println("filed: ref=MediaFile, obj=" + f.getClass().getSimpleName());
            }
            System.out.println(f.toString());
            System.out.println("open=" + f.open());

            if (f instanceof Playable p) {
                System.out.println(p.play());
            }
            if (f instanceof Compressible c) {
                System.out.println(c.compress());
            }
            System.out.println("---");
        }
    }
}