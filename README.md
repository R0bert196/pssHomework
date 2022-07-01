## Xml Parser

---

Xml Parser is an application made for SACOM, that processes the orders XML files and creates new XML files that will be sent to the suppliers.

---
The solution approach uses two libraries:

The first one, **DOM Parser**, a built-in library, responsible for creating objects from the orders xml, parsing the xml as an object graph (a tree like structure) in memory – so called “Document Object Model (DOM)“.

The second one, **JAXB**, responsible for marshalling (writing) the created objects into xml files. It supports a binding framework that maps XML elements and attributes to Java fields and properties using Java annotations.

### Used libraries

- Lombok
- Junit
- JAXB
- DOM Parser (buit-in)

### How to use

The app is quite simple to use:
1. Run the app
2. Drop an 'orders##.xml' file into the input folder (the input folder can be changed from the application.properites)
3. Wait for the app to do its magic.
