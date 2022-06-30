package org.example;

import org.example.watchServices.FileAddWatcher;
import org.example.xmlHandlers.XmlCreator;
import org.example.xmlHandlers.XmlHandler;
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



        XmlParser xmlParser = new XmlParser(DocumentBuilderFactory.newInstance());
        XmlCreator xmlCreator = new XmlCreator();

        XmlHandler xmlHandler = new XmlHandler(xmlParser, xmlCreator);

        FileAddWatcher fileAddWatcher = new FileAddWatcher(xmlHandler);

        fileAddWatcher.watchForChanges();




    }
}
