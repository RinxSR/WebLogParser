package logic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public interface LogParser {
  void process(InputStream is, OutputStream os) throws IOException;
}
