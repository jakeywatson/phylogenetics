package com.company;
import java.io.*;
/**
 * @author Joe Parker, 2011
 * @version 0.1
 * @
 */
public class ToFasta {

    class CustomFileWriter {

        private final File outputFile;
        private final String data;

        /**
         * @param fullyPathQualifiedFileNameArg - a fully qualified (e.g. absolute) filename (String)
         * @param dataArg                       - the data (String) to write
         */
        public CustomFileWriter(File outputFileArg, String dataArg) {
            outputFile = outputFileArg;
            data = dataArg;
            //		assert(outputFile.canWrite());
            try {
                FileWriter writer = new FileWriter(outputFile);
                writer.write(data);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

