package gitTestJava;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.StringWriter;

public class Main {

    public static void main(String[] args) {
    		System.out.println("Hello Git!");
        System.out.println("This is a new feature branch.");
        System.out.println("This is a new feature branch2");
        
        try {
            // 1. XML 파싱
            Document doc = loadXML("devices.xml");

            // 2. 장치 상태 변경
            updateStatusAndPower(doc);

            // 3. 새 장치 추가
            addDevice(doc, "1004", "Sensor_Y", "ON", 50);

            // 4. XML 저장
            saveXML(doc, "devices_updated.xml");

            // 5. 콘솔 출력
            printDevices(doc);
            System.out.println( DocumentToString( doc ) );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // XML 파일 로드
    private static Document loadXML(String filePath) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(filePath));
    }

    // 장치 상태 및 전원 변경
    private static void updateStatusAndPower(Document doc) {
        NodeList devices = doc.getElementsByTagName("Device");

        for (int i = 0; i < devices.getLength(); i++) {
            Element device = (Element) devices.item(i);
            
            // TODO: Status가 OFF이면 ON으로 변경
            Element statusElem = (Element) device.getElementsByTagName("Status").item(0); 
            String statusElemText = statusElem.getTextContent();
            
            
            if(statusElemText.equals("OFF")) {
            		statusElem.setTextContent("ON");
            }
            
            // TODO: Power 값을 10 증가시키기
            Element powerElem = (Element) device.getElementsByTagName("Power").item(0); 
            int powerElemNum = Integer.parseInt(powerElem.getTextContent());
            
            powerElem.setTextContent(String.valueOf(powerElemNum+10)); 
            
        }
        
    }

    // 새 장치 추가
    private static void addDevice(Document doc, String id, String name, String status, int power) {
        // TODO: Element 생성 후 <Devices>에 append
    		Element root = doc.getDocumentElement();
    		
    		Element device = doc.createElement("Device");
    		
    		Element idElem = doc.createElement("ID");
    		idElem.setTextContent(id);
    		device.appendChild(idElem);
    		
    		Element nameElem = doc.createElement("Name");
    		nameElem.setTextContent(name);
    		device.appendChild(nameElem);

    	    // <Status> 요소
    	    Element statusElem = doc.createElement("Status");
    	    statusElem.setTextContent(status);
    	    device.appendChild(statusElem);

    	    // <Power> 요소
    	    Element powerElem = doc.createElement("Power");
    	    powerElem.setTextContent(String.valueOf(power));
    	    device.appendChild(powerElem);
    		
    		root.appendChild(device);
    		
    }

    // 콘솔 출력
    private static void printDevices(Document doc) {
        NodeList devices = doc.getElementsByTagName("Device");

        for (int i = 0; i < devices.getLength(); i++) {
            Element device = (Element) devices.item(i);
            String id = device.getElementsByTagName("ID").item(0).getTextContent();
            String name = device.getElementsByTagName("Name").item(0).getTextContent();
            String status = device.getElementsByTagName("Status").item(0).getTextContent();
            String power = device.getElementsByTagName("Power").item(0).getTextContent();

            System.out.println("ID: " + id + ", Name: " + name + ", Status: " + status + ", Power: " + power);
        }
    }

    // XML 저장
    private static void saveXML(Document doc, String outputPath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(outputPath));

        transformer.transform(source, result);
    }
    
    public static String DocumentToString( Document doc )

    {

     try

     {

      StringWriter clsOutput = new StringWriter( );

      Transformer clsTrans = TransformerFactory.newInstance( ).newTransformer( );

   

      clsTrans.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, "no" );

      clsTrans.setOutputProperty( OutputKeys.METHOD, "xml" );

      clsTrans.setOutputProperty( OutputKeys.INDENT, "yes" );

      clsTrans.setOutputProperty( OutputKeys.ENCODING, "UTF-8" );

   

      clsTrans.transform( new DOMSource( doc ), new StreamResult( clsOutput ) );

      return clsOutput.toString( );

     }

     catch( Exception ex )

     {

      return "";

     }

   }
}