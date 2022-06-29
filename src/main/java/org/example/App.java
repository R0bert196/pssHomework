package org.example;

import org.example.watchServices.FileAddWatcher;
import org.example.xmlHandlers.XmlParser;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        FileAddWatcher fileAddWatcher = new FileAddWatcher();
        fileAddWatcher.watchForChanges();

        XmlParser xmlParser = new XmlParser(DocumentBuilderFactory.newInstance());
        xmlParser.parseOrderXml();
    }
}
