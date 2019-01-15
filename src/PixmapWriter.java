import java.io.*;

class PixmapWriter extends FileOutputStream {

  public PixmapWriter(String fileName) throws IOException {
    super(fileName);
  }

  public void put(String data) throws IOException {
    write(data.getBytes());
  }

}