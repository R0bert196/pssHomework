package org.example;

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
        xmlParser.parseOrderXml();
    }
}
